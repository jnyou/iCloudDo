package org.jnyou.gmall.orderservice.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.exception.NoStockException;
import org.jnyou.common.to.mq.OrderTo;
import org.jnyou.common.to.mq.SeckillOrderTo;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;
import org.jnyou.common.utils.R;
import org.jnyou.common.vo.MemberResponseVo;
import org.jnyou.gmall.orderservice.constant.OrderConstant;
import org.jnyou.gmall.orderservice.constant.PayConstant;
import org.jnyou.gmall.orderservice.dao.OrderDao;
import org.jnyou.gmall.orderservice.entity.OrderEntity;
import org.jnyou.gmall.orderservice.entity.OrderItemEntity;
import org.jnyou.gmall.orderservice.entity.PaymentInfoEntity;
import org.jnyou.gmall.orderservice.enume.OrderStatusEnum;
import org.jnyou.gmall.orderservice.feign.CartFeignClient;
import org.jnyou.gmall.orderservice.feign.MemberFeignClient;
import org.jnyou.gmall.orderservice.feign.ProductFeignClient;
import org.jnyou.gmall.orderservice.feign.WareFeignClient;
import org.jnyou.gmall.orderservice.interceptor.LoginUserInterceptor;
import org.jnyou.gmall.orderservice.service.OrderItemService;
import org.jnyou.gmall.orderservice.service.OrderService;
import org.jnyou.gmall.orderservice.service.PaymentInfoService;
import org.jnyou.gmall.orderservice.to.OrderCreateTo;
import org.jnyou.gmall.orderservice.to.SpuInfoTo;
import org.jnyou.gmall.orderservice.vo.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    private final ThreadLocal<OrderSubmitVo> submitThreadLocal = new ThreadLocal<>();

    @Autowired
    MemberFeignClient memberFeignClient;
    @Autowired
    CartFeignClient cartFeignClient;
    @Autowired
    ThreadPoolExecutor executor;
    @Autowired
    WareFeignClient wareFeignClient;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ProductFeignClient productFeignClient;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    PaymentInfoService paymentInfoService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        MemberResponseVo memberResponseVo = LoginUserInterceptor.loginUser.get();
        System.out.println("主线程===" + Thread.currentThread().getId());
        // 获取浏览器老请求过来的头信息
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        CompletableFuture<Void> getAddressTaskFuture = CompletableFuture.runAsync(() -> {
            // 解决feign异步丢失头信息，每一个线程都来共享原来的请求信息
            RequestContextHolder.setRequestAttributes(requestAttributes);
            // 1、远程查询用户收货地址信息
            System.out.println("AddressTask主线程===" + Thread.currentThread().getId());
            List<MemberAddressVo> address = memberFeignClient.getAddress(memberResponseVo.getId());
            confirmVo.setMemberAddressVos(address);
        }, executor);

        CompletableFuture<Void> getCartItemsTaskFuture = CompletableFuture.runAsync(() -> {
            // 解决feign异步丢失头信息，每一个线程都来共享原来的请求信息
            RequestContextHolder.setRequestAttributes(requestAttributes);
            // 2、远程查询购物车选中的购物项信息
            System.out.println("CartItemsTask主线程===" + Thread.currentThread().getId());
            List<OrderItemVo> currentCartItems = cartFeignClient.getCurrentCartItems();
            confirmVo.setItems(currentCartItems);
        }, executor).thenRunAsync(() -> {
            List<OrderItemVo> items = confirmVo.getItems();
            // 远程查询库存信息
            List<Long> collect = items.stream().map(item -> item.getSkuId()).collect(Collectors.toList());
            R r = wareFeignClient.getSkuHasStock(collect);
            if (r.getCode() == 0) {
                List<SkuStockVo> data = r.getData(new TypeReference<List<SkuStockVo>>() {
                });
                if (!CollectionUtils.isEmpty(data)) {
                    Map<Long, Boolean> map = data.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                    confirmVo.setStocks(map);
                }
            }
        }, executor);

        // 3、用户积分
        Integer integration = memberResponseVo.getIntegration();
        confirmVo.setIntegration(integration);
        // 其他数据自动计算
        // TODO 防重令牌
        String token = UUID.randomUUID().toString();
        confirmVo.setOrderToken(token);
        stringRedisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberResponseVo.getId(), token, 30, TimeUnit.MINUTES);
        CompletableFuture.allOf(getAddressTaskFuture, getCartItemsTaskFuture).get();
        return confirmVo;
    }

    @Transactional(timeout = 30)
    public void c() {
        /** 失效 **/
        a();
        b();
        /** 解决方案 **/
        OrderServiceImpl orderService = (OrderServiceImpl) AopContext.currentProxy();
        orderService.b();
        orderService.c();
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = 2)
    public void a() {
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 30)
    public void b() {
    }

    @Override
    @Transactional // 本地事务，只能控制住自己的回滚，控制不了其他服务的回滚
//    @GlobalTransactional
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo vo) {
        submitThreadLocal.set(vo);
        SubmitOrderResponseVo responseVo = new SubmitOrderResponseVo();
        responseVo.setCode(0);
        MemberResponseVo memberResponseVo = LoginUserInterceptor.loginUser.get();

        // 1、验证防重令牌[令牌的对比和删除应该是原子操作，0-令牌失败 - 1、删除成功] 保证接口幂等性
        // 译：如果从redis中获取一个key和传递过来的数进行对比，删除这个key，成功后返回1，失败返回0
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        String redisToken = stringRedisTemplate.opsForValue().get(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberResponseVo.getId());
        Long result = stringRedisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList(redisToken), vo.getOrderToken());
        if (result == 0L) {
            // 验证失败
            responseVo.setCode(1);
            return responseVo;
        } else {
            // 验证成功
            responseVo.setCode(0);
            // 创建订单、验价格、锁库存
            // 1、创建订单
            OrderCreateTo ordered = createOrdered();

            // 2、验价
            BigDecimal payAmount = ordered.getOrder().getPayAmount();
            BigDecimal payPrice = vo.getPayPrice();
            if (Math.abs(payAmount.subtract(payPrice).doubleValue()) < 0.01) {
                // 金额对比成功...
                // 3、保存订单和订单项
                saveOrder(ordered);
                // 4、远程锁定库存
                WareSkuLockVo lockVo = new WareSkuLockVo();
                lockVo.setOrderSn(ordered.getOrder().getOrderSn());
                List<OrderItemVo> locks = ordered.getOrderItems().stream().map(item -> {
                    OrderItemVo itemVo = new OrderItemVo();
                    itemVo.setSkuId(item.getSkuId());
                    itemVo.setCount(item.getSkuQuantity());
                    itemVo.setTitle(item.getSkuName());
                    return itemVo;
                }).collect(Collectors.toList());
                lockVo.setLocks(locks);
                R r = wareFeignClient.orderLockStock(lockVo);
                if (r.getCode() == 0) {
                    // 锁定成功
                    responseVo.setOrder(ordered.getOrder());
//                    int i = 10/0;
                    // 订单创建成功，发送消息给MQ,让MQ执行关单逻辑到新的队列中
                    rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", ordered.getOrder());
                    return responseVo;
                } else {
                    // 锁定失败
                    String msg = (String) r.get("msg");
                    throw new NoStockException(msg);
                }

            } else {
                responseVo.setCode(2);
            }
        }
        return responseVo;
    }

    @Override
    public OrderEntity getOrderByOrderSn(String orderSn) {
        return this.getOne(Wrappers.<OrderEntity>lambdaQuery().eq(OrderEntity::getOrderSn, orderSn));
    }

    @Override
    public void closeOrder(OrderEntity orderEntity) {
        // 查询当前订单的最新状态
        OrderEntity entity = this.getById(orderEntity.getId());
        if (OrderStatusEnum.CREATE_NEW.getCode().equals(entity.getStatus())) {
            // 关单
            OrderEntity order = new OrderEntity().setId(entity.getId()).setStatus(OrderStatusEnum.CANCLED.getCode());
            this.updateById(order);
        }
        OrderTo orderTo = new OrderTo();
        BeanUtils.copyProperties(entity, orderTo);
        // 关闭订单后，发送消息给MQ
        try {
            // 保证消息一定发送出去，每一个消息都可以做好日志记录（给数据库保存每一个消息的详细信息）
            // 定期扫描数据库，将失败的消息再发送一遍
            rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other", orderTo);
        } catch (Exception e) {
            // 想方法将消息进行重试发送
        }

    }

    @Override
    public PayVo getPayData(String orderSn) {
        OrderEntity order = this.getOrderByOrderSn(orderSn);
        List<OrderItemEntity> list = orderItemService.list(Wrappers.<OrderItemEntity>lambdaQuery().eq(OrderItemEntity::getOrderSn, orderSn));
        BigDecimal payAmount = order.getPayAmount().setScale(2, BigDecimal.ROUND_UP);
        PayVo payVo = new PayVo().setTotal_amount(payAmount.toString()).setOut_trade_no(order.getOrderSn()).setSubject(list.get(0).getSkuName()).setBody(list.get(0).getSkuAttrsVals());
        return payVo;
    }

    @Override
    public PageUtils queryPageWithItem(Map<String, Object> params) {
        MemberResponseVo memberResponseVo = LoginUserInterceptor.loginUser.get();
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>().eq("member_id", memberResponseVo.getId()).orderByDesc("id")
        );

        List<OrderEntity> records = page.getRecords();
        List<OrderEntity> collect = records.stream().map(item -> {
            List<OrderItemEntity> list = orderItemService.list(Wrappers.<OrderItemEntity>lambdaQuery().eq(OrderItemEntity::getOrderSn, item.getOrderSn()));
            item.setOrderItemEntities(list);
            return item;
        }).collect(Collectors.toList());

        page.setRecords(collect);
        return new PageUtils(page);
    }

    @Override
    public String handlePayResult(PayAsyncVo payAsyncVo) {
        // 保存交易流水
        PaymentInfoEntity infoEntity = new PaymentInfoEntity();
        String orderSn = payAsyncVo.getOut_trade_no();
        infoEntity.setOrderSn(orderSn);
        infoEntity.setAlipayTradeNo(payAsyncVo.getTrade_no());
        infoEntity.setSubject(payAsyncVo.getSubject());
        String trade_status = payAsyncVo.getTrade_status();
        infoEntity.setPaymentStatus(trade_status);
        infoEntity.setCreateTime(new Date());
        infoEntity.setCallbackTime(payAsyncVo.getNotify_time());
        paymentInfoService.save(infoEntity);
        //判断交易状态是否成功
        if (trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED")) {
            // 支付成功，修改订单的状态
            this.baseMapper.updateOrderStatus(orderSn, OrderStatusEnum.PAYED.getCode(), PayConstant.ALIPAY);
        }
        return "success";
    }

    @Override
    public void createSeckillOrder(SeckillOrderTo order) {
        // TODO 保存订单
        BigDecimal price = order.getSeckillPrice().multiply(new BigDecimal(order.getNum()));
        OrderEntity orderEntity = new OrderEntity()
                .setOrderSn(order.getOrderSn())
                .setMemberId(order.getMemberId())
                .setStatus(OrderStatusEnum.CREATE_NEW.getCode())
                .setPayAmount(price);
        this.save(orderEntity);


        // 保存订单项信息，其他信息都能通过远程product服务获取
        OrderItemEntity entity = new OrderItemEntity().setOrderSn(order.getOrderSn()).setRealAmount(price).setSkuQuantity(order.getNum());
        orderItemService.save(entity);
    }

    /**
     * 保存订单数据 (包括订单和订单项)
     *
     * @param ordered
     * @Author JnYou
     */
    private void saveOrder(OrderCreateTo ordered) {
        OrderEntity orderEntity = new OrderEntity();
        OrderEntity order = ordered.getOrder();
        order.setModifyTime(new Date());
        order.setCreateTime(new Date());
        // 保存订单
        this.save(order);
        // 批量保存订单项
        orderItemService.saveBatch(ordered.getOrderItems());

    }

    /**
     * create order and order item
     */
    private OrderCreateTo createOrdered() {
        OrderCreateTo orderCreateTo = new OrderCreateTo();
        // 生成订单号
        String orderSn = IdWorker.getTimeId();
        // 构建订单
        OrderEntity orderEntity = buildOrder(orderSn);
        orderCreateTo.setOrder(orderEntity);
        // 获取所有订单项
        List<OrderItemEntity> entities = buildOrderItems(orderSn);
        orderCreateTo.setOrderItems(entities);

        // 计算价格
        compiterPrice(orderEntity, entities);

        return orderCreateTo;
    }

    private void compiterPrice(OrderEntity orderEntity, List<OrderItemEntity> entities) {
        //总价
        BigDecimal total = BigDecimal.ZERO;
        //优惠价格
        BigDecimal promotion = new BigDecimal("0.0");
        BigDecimal integration = new BigDecimal("0.0");
        BigDecimal coupon = new BigDecimal("0.0");
        //积分
        Integer integrationTotal = 0;
        Integer growthTotal = 0;

        // 计算价格
        for (OrderItemEntity entity : entities) {
            total = total.add(entity.getRealAmount());
            promotion = promotion.add(entity.getPromotionAmount());
            integration = integration.add(entity.getIntegrationAmount());
            coupon = coupon.add(entity.getCouponAmount());
            integrationTotal += entity.getGiftIntegration();
            growthTotal += entity.getGiftGrowth();
        }
        orderEntity.setTotalAmount(total);
        orderEntity.setPromotionAmount(promotion);
        orderEntity.setIntegrationAmount(integration);
        orderEntity.setCouponAmount(coupon);
        orderEntity.setIntegration(integrationTotal);
        orderEntity.setGrowth(growthTotal);
        // 应付金额：总金额 + 运费金额
        orderEntity.setPayAmount(total.add(orderEntity.getFreightAmount()));

        //设置删除状态(0-未删除，1-已删除)
        orderEntity.setDeleteStatus(0);

    }

    /**
     * 构建订单
     *
     * @param orderSn
     * @Author JnYou
     */
    private OrderEntity buildOrder(String orderSn) {
        MemberResponseVo memberResponseVo = LoginUserInterceptor.loginUser.get();
        OrderSubmitVo orderSubmitVo = submitThreadLocal.get();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderSn(orderSn);
        orderEntity.setMemberId(memberResponseVo.getId());
        // 远程获取收货地址信息
        R r = wareFeignClient.getFare(orderSubmitVo.getAddrId());
        FareVo data = r.getData(new TypeReference<FareVo>() {
        });
        // 设置运费
        orderEntity.setFreightAmount(data.getFare());
        // 设置收货人的信息
        orderEntity.setReceiverCity(data.getAddress().getCity());
        orderEntity.setReceiverName(data.getAddress().getName());
        orderEntity.setReceiverPhone(data.getAddress().getPhone());
        orderEntity.setReceiverDetailAddress(data.getAddress().getDetailAddress());
        orderEntity.setReceiverPostCode(data.getAddress().getPostCode());
        orderEntity.setReceiverProvince(data.getAddress().getProvince());
        orderEntity.setReceiverRegion(data.getAddress().getRegion());

        //3) 设置订单相关的状态信息
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        orderEntity.setConfirmStatus(0);
        orderEntity.setAutoConfirmDay(7);

        return orderEntity;
    }

    /**
     * 构建所有订单项
     *
     * @param cartItem
     * @Author JnYou
     */
    private List<OrderItemEntity> buildOrderItems(String orderSn) {
        List<OrderItemVo> currentCartItems = cartFeignClient.getCurrentCartItems();
        if (!CollectionUtils.isEmpty(currentCartItems)) {
            List<OrderItemEntity> collect = currentCartItems.stream().map(cartItem -> {
                OrderItemEntity itemEntity = buildOrderItem(cartItem);
                itemEntity.setOrderSn(orderSn);
                return itemEntity;
            }).collect(Collectors.toList());
            return collect;
        }

        return null;
    }

    /**
     * 构建某一个订单项
     *
     * @param cartItem
     * @Author JnYou
     */
    private OrderItemEntity buildOrderItem(OrderItemVo item) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        Long skuId = item.getSkuId();
        // 订单信息(商品的sku信息)
        orderItemEntity.setSkuId(skuId);
        orderItemEntity.setSkuName(item.getTitle());
        orderItemEntity.setSkuAttrsVals(StringUtils.collectionToDelimitedString(item.getSkuAttrValues(), ";"));
        orderItemEntity.setSkuPic(item.getImage());
        orderItemEntity.setSkuPrice(item.getPrice());
        orderItemEntity.setSkuQuantity(item.getCount());

        // 商品的spu信息 (通过skuId查询spu相关属性并设置)
        R r = productFeignClient.getSpuInfoBySkuId(skuId);
        if (r.getCode() == 0) {
            SpuInfoTo spuInfo = r.getData(new TypeReference<SpuInfoTo>() {
            });
            orderItemEntity.setSpuId(spuInfo.getId());
            orderItemEntity.setSpuName(spuInfo.getSpuName());
            orderItemEntity.setSpuBrand(spuInfo.getBrandName());
            orderItemEntity.setCategoryId(spuInfo.getCatalogId());
        }

        // TODO 优惠信息

        // 积分信息（商品的积分成长，为价格x数量）
        orderItemEntity.setGiftGrowth(item.getPrice().multiply(new BigDecimal(item.getCount())).intValue());
        orderItemEntity.setGiftIntegration(item.getPrice().multiply(new BigDecimal(item.getCount())).intValue());

        //5) 订单项订单价格信息
        orderItemEntity.setPromotionAmount(BigDecimal.ZERO);
        orderItemEntity.setCouponAmount(BigDecimal.ZERO);
        orderItemEntity.setIntegrationAmount(BigDecimal.ZERO);

        //6) 实际价格
        BigDecimal origin = orderItemEntity.getSkuPrice().multiply(new BigDecimal(orderItemEntity.getSkuQuantity()));
        // 减去优惠的所有价格
        BigDecimal realPrice = origin.subtract(orderItemEntity.getPromotionAmount())
                .subtract(orderItemEntity.getCouponAmount())
                .subtract(orderItemEntity.getIntegrationAmount());
        orderItemEntity.setRealAmount(realPrice);

        return orderItemEntity;
    }

}