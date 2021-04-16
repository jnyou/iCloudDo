import request from '@/utils/request'
export default {
  //1、创建订单
  createOrder(cid) {
    return request({
      url: '/orderservice/order/createOrder/' + cid,
      method: 'post'
    })
  },
  //2、根据id获取订单
  getById(orderNo) {
    return request({
      url: '/orderservice/order/getOrderInfo/' + orderNo,
      method: 'get'
    })
  },
  //3、生成微信支付二维码
  createNative(orderNo) {
    return request({
      url: '/orderservice/log/createNative/' + orderNo,
      method: 'get'
    })
  },
  //4、根据id获取订单支付状态
  queryPayStatus(orderNo) {
    return request({
      url: '/orderservice/log/queryPayStatus/' + orderNo,
      method: 'get'
    })
  }
}