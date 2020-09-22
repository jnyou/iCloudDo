## JDK8的map和optional源码剖析



[
](https://mp.weixin.qq.com/s?__biz=MzI3ODcxMzQzMw==&mid=2247486559&idx=2&sn=0eebf45617fb7be5727712e22aac7fb6&scene=21#wechat_redirect)

## 业务背景

首先，业务需求是这样的，从第三方电商平台拉取所有订单，然后保存到公司自己的数据库，需要判断是否有物流信息，如果有物流信息，还需要再进行上传。

而第三方接口返回的数据是 `JSON` 格式的，其中物流信息却藏的十分深，如下面所示，JSON 节点是这样的：

> xxxOrder > xxxShippingInfo > xxxShipmentDetails > xxxTrackingInfo > trackingNumber, trackingLink

## 基本实现

因为第三方接口返回的数据是 `JSON` 格式的，所以需要把 `JSON` 字符串转换成 Java 对象来进行处理。

```
@JsonIgnoreProperties(ignoreUnknown = true)
public class XxxOrder {

    /**
     * 物流信息
     */
    @JsonProperty("shippingInfo")
    private XxxShippingInfo xxxShippingInfo;

}
```

上面只是第一层示例，要拿到物流信息，要依次封装四层对象，到真正获取物流信息时要避免空指针，就需要判断四层才能拿到，如示例所示：

```
if(xxxOrder != null){
 if(xxxOrder.getXxxShippingInfo() != null){
  if(xxxOrder.getXxxShippingInfo().getXxxShipmentDetails() != null){
   if(xxxOrder.getXxxShippingInfo().getXxxShipmentDetails().getXxxTrackingInfo() != null){
    ...
   }
  }
 }
}
```

获取一个物流信息这么麻烦，我也是醉了，这样写也太不优雅了。

## Java 8 实现

因为我知道 Java 8 可以处理这类的需求，所以我从来没想过用最原始的方式去实现，直接把就用 Java 8 来实现了：

```
/**
* 公众号：Java技术栈
/
private String[] getFulfillments(XxxOrder xxxOrder) {
    return Optional.ofNullable(xxxOrder)
            .map((o) -> o.getXxxShippingInfo())
            .map((si) -> si.getXxxShipmentDetails())
            .map((sd) -> sd.getXxxTrackingInfo())
            .map((t) -> new String[]{t.getTrackingNumber(), t.getTrackingLink()})
            .orElse(null);
}
```

写完之后，同事居然都直呼看不懂，还特地跑过来问我。。

## 实现原理

其实这并没有用什么高超的技术，就是利用 Java 8 Optional 来实现的，细节就不介绍了 ，主要是为了避免空指针而生的，不懂的可以点击[这里](https://mp.weixin.qq.com/s?__biz=MzI3ODcxMzQzMw==&mid=2247488192&idx=1&sn=258dff0d6b10266ba3e1a2adec4c0fdb&scene=21#wechat_redirect)查看这篇文章。

今天就来介绍下 Optional#map 方法实现这段逻辑的原理，来看下 map 的实现源码：

```
public<U> Optional<U> map(Function<? super T, ? extends U> mapper) {
    // 函数式接口不能为null
    Objects.requireNonNull(mapper);
    
    // 如果当前没有值，返回一个空的Optional
    if (!isPresent())
        return empty();
    else {
        // 如果当前有值，返回一个函数式处理该值的结果Optional
        return Optional.ofNullable(mapper.apply(value));
    }
}

// 判断 Optional Value 有没有值
public boolean isPresent() {
    return value != null;
}

// 创建一个 Optional，可以为空
public static <T> Optional<T> ofNullable(T value) {
    return value == null ? empty() : of(value);
}
```

所以回到这段程序：

```
// 根对象为空就创建一个空Optional，否则就创建一个根对象的Optional
Optional.ofNullable(xxxOrder)
    // 根对象为空就直接返回空Optional，否则返回这个值的 Optional
    .map((o) -> o.getXxxShippingInfo())
    // 下面依次类推……
    .map((si) -> si.getXxxShipmentDetails())
    .map((sd) -> sd.getXxxTrackingInfo())
    .map((t) -> new String[]{t.getTrackingNumber(), t.getTrackingLink()})
    // 取不到值就返回 null
    .orElse(null);
}
```

也许你看完感觉还是看不懂，我承认，确实比较绕，不太好理解，这个只可意会不可言传了，多看多练就理解了。

这个的关键核心在于，调用 map 时，如果 Optional 没有值就直接返回空的 Optional，而不会调用函数式接口，所以就不会出现空指针。所以只要有一个为空，后面就取不到物流信息。

程序使用了 .xx.xx.xx 这样的链式调用，调用 map 方法就必须是 Optional，而 map 的返回结果就是 Optional。

有一个问题是，如果都为空，那不是所有的 map 都会走一遍？在这种情况下会不会影响性能？编译器是否会作优化？这个暂不可知。

另外还有一个 `flatMap` 方法，和 `map` 有什么区别呢？

![img](https://mmbiz.qpic.cn/mmbiz_png/TNUwKhV0JpRvXhAERVVpW1tR0aweRgEFuibRPJnibgkI4mwHPIo4nswWxGJlRMY0InicxZb3ocNYsn5xES3BSQjDg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

`flatMap` 返回结果需要在函数式接口中封装 Optional 返回，在这里应用不太合适。

## 总结

很多人一直都在说有在学习 Java 8 新特性，但在我看来，大部分人并没有什么实践，用的都还是最原始的实现方式。

其实我个人是一直在努力学习这方面的知识的，最新的我已经学到 Java 14 了，之前也陆续分享了一系列新特性文章，感兴趣的可以关注公众号Java技术栈回复java获取。

所以我现在虽然是个老前浪了，但在新知识学习和掌握上面，我感觉已经走到了很多后浪前面。

做 Java 程序猿要学的技术很多，虽然有点知识点短时间你是理解了，但肯定不深刻，时间久了就忘了，所以给大家的建议是一定要实战 + 阅读源码，这样才真正属于你的。

- 