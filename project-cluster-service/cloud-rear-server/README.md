### 环境准备（必须）
- 启动nacos服务：（小编使用的是Windows的nacos-server-1.1.3版本）
- 启动sentinel的jar（version：1.7.1）服务：java -jar sentinel-dashboard-1.7.1.jar --server.port=8333
- 使用docker安装mysql，nginx，elasticsearch，kibana，redis，zipkin，rabbitMQ
- 修改Windows的host文件
```$xslt
192.168.56.10 gmall.com
192.168.56.10 search.gmall.com
192.168.56.10 item.gmall.com
192.168.56.10 auth.gmall.com
192.168.56.10 cart.gmall.com
192.168.56.10 order.gmall.com
192.168.56.10 seckill.gmall.com
```
### 环境准备（非必须）
- 启动seata服务（seata-server-1.0.0版本）

### 扩展：新增mall新模块，需要统一配置：
1、spring-session依赖
2、spring-session的配置
3、引入LoginInteceptor、WebMvcConfigure