## 布隆过滤器
解决缓存穿透，或者使用缓存空对象（一个永远不存在的key）

压测计划：

使用压测工具喂120万条数据进入Redis Bloomfilter看实际效果


```
jmeter中BeanShell产生16位字符随机组成email的函数
useremail="${__RandomString(16,abcdefghijklmnop,myemail)}"+"@163.com";
vars.put("random_email",useremail);
```
![jemter](https://img-blog.csdnimg.cn/20200112232923882.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpZmV0cmFnZWR5,size_16,color_FFFFFF,t_70)

jmeter测试计划设置成了75个线程，连续运行30分钟（30分钟每次插大概40万条数据进去吧，可以运行3次差不多）
![](https://img-blog.csdnimg.cn/20200112233051433.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpZmV0cmFnZWR5,size_16,color_FFFFFF,t_70)

jmeter post请求
![](https://img-blog.csdnimg.cn/202001122332572.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpZmV0cmFnZWR5,size_16,color_FFFFFF,t_70)

保存整个测试计划 执行如下命令：
```
 jmeter -n -t add_randomemail_to_bloom.jmx -l add_email_to_bloom\report\03-result.csv -j add_email_to_bloom\logs\03-log.log -e -o add_email_to_bloom\html_report_3
```
它代表：

- -t 指定jmeter执行计划文件所在路径；
- -l 生成report的目录，这个目录如果不存在则创建 ，必须是一个空目录；
- -j 生成log的目录，这个目录如果不存在则创建 ，必须是一个空目录；
- -e 生成html报告，它配合着-o参数一起使用；
- -o 生成html报告所在的路径，这个目录如果不存在则创建 ，必须是一个空目录；

回车运行即可执行，可以看我们指定的路径中的报告文件，然后随便找一个邮箱进行测试即可。。。