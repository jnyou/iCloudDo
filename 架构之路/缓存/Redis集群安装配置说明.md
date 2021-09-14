## 下载

```
wget http://download.redis.io/releases/redis-6.0.5.tar.gz
```

## 安装基础环境

```
# 查看gcc版本是否在5.3以上，centos7.6默认安装4.8.5
gcc -v
# 升级gcc到5.3及以上,如下：
升级到gcc 9.3：
yum -y install centos-release-scl
yum -y install devtoolset-9-gcc devtoolset-9-gcc-c++ devtoolset-9-binutils
scl enable devtoolset-9 bash
需要注意的是scl命令启用只是临时的，退出shell或重启就会恢复原系统gcc版本。
如果要长期使用gcc 9.3的话：

echo "source /opt/rh/devtoolset-9/enable" >>/etc/profile
这样退出shell重新打开就是新版的gcc了
以下其他版本同理，修改devtoolset版本号即可。

```

## 编译安装


```
make && make install
```

## 其他命令


```
# 编译出错时，清出编译生成的文件
make distclean
# 编译安装到指定目录下
make PREFIX=/usr/local/redis install 
# 卸载
make uninstall
```
## 启动服务


```
mkdir redis-cluster 
```

创建集群文件夹，然后在该文件夹下创建6个文件同redis.conf配置一致， 7001.conf、7002.conf、7003.conf、7004.conf、7005.conf、7006.conf

然后修改各个配置：


```
port  700x                             // 端口700X
bind 本机ip                             // 需要改为其他节点机器可访问的ip 否则创建集群时无法访问对应的端口，无法创建集群
daemonize    yes                       // redis后台运行
pidfile  /var/run/redis_7000.pid       // pidfile文件对应7000,7001,7002
cluster-enabled  yes                   // 开启集群  把注释#去掉
cluster-config-file  nodes_7000.conf   // 集群的配置  配置文件首次启动自动生成 7000,7001,7002
cluster-node-timeout  15000            // 请求超时  默认15秒，可自行设置
appendonly  yes
```


## 启动服务

```
redis-server ../redis-cluster/7001.conf
redis-server ../redis-cluster/7002.conf
redis-server ../redis-cluster/7003.conf
redis-server ../redis-cluster/7004.conf
redis-server ../redis-cluster/7005.conf
redis-server ../redis-cluster/7006.conf

```

## 创建集群


```
--replicas参数指定集群中每个主节点配备几个从节点，这里设置为1。

./redis-cli --cluster create 127.0.0.1:7001 127.0.0.1:7002 127.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 127.0.0.1:7006 --cluster-replicas 1 
```

测试连接：


```
redis-cli -c -h 127.0.0.1 -p 7001
```

关闭服务：


```
redis-cli -c -h 127.0.0.1 -p 7001 shutdown
```


## 查看集群


```
redis-cli --cluster check 127.0.0.1:7001
127.0.0.1:7001 (2d589d00...) -> 0 keys | 5461 slots | 1 slaves.
127.0.0.1:7003 (85df8dc9...) -> 0 keys | 5462 slots | 1 slaves.
127.0.0.1:7002 (406a1d6e...) -> 0 keys | 5461 slots | 1 slaves.
[OK] 0 keys in 3 masters.
0.00 keys per slot on average.
>>> Performing Cluster Check (using node 127.0.0.1:7001)
M: 2d589d0077771ca9a7b2909b351e350aa69bea45 127.0.0.1:7001
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
M: 85df8dc9b649e02740fa0f932aa1df135d44953e 127.0.0.1:7003
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
M: 406a1d6e9bcc760501c6b8f71f9151e020b247bd 127.0.0.1:7002
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: 60f49255085936fbcf682bafc81319f9393150c9 127.0.0.1:7006
   slots: (0 slots) slave
   replicates 406a1d6e9bcc760501c6b8f71f9151e020b247bd
S: e52eda05e4df779e15437f7f42522459a3e26b27 127.0.0.1:7005
   slots: (0 slots) slave
   replicates 85df8dc9b649e02740fa0f932aa1df135d44953e
S: 54f13720da944d3dcc75351c4042fcaf3b2300e0 127.0.0.1:7004
   slots: (0 slots) slave
   replicates 2d589d0077771ca9a7b2909b351e350aa69bea45
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

## 高可用

redis集群至少需要一个备份节点，才能更好的保证集群的高可用。一个集群里面有`M1-S1、M2-S2、M3-S3`六个主从节点，其中节点 M1包含 0 到 5500号哈希槽，节点M2包含5501 到 11000 号哈希槽，节点M3包含11001 到 16384号哈希槽。如果是M1宕掉，集群便会选举S1为新节点继续服务，整个集群还会正常运行。当M1、S1都宕掉了，这时候集群就不可用了。

## 集群命令


```
redis-cli --cluster help
Cluster Manager Commands:
  create         host1:port1 ... hostN:portN   #创建集群
                 --cluster-replicas <arg>      #从节点个数
  check          host:port                     #检查集群
                 --cluster-search-multiple-owners #检查是否有槽同时被分配给了多个节点
  info           host:port                     #查看集群状态
  fix            host:port                     #修复集群
                 --cluster-search-multiple-owners #修复槽的重复分配问题
  reshard        host:port                     #指定集群的任意一节点进行迁移slot，重新分slots
                 --cluster-from <arg>          #需要从哪些源节点上迁移slot，可从多个源节点完成迁移，以逗号隔开，传递的是节点的node id，还可以直接传递--from all，这样源节点就是集群的所有节点，不传递该参数的话，则会在迁移过程中提示用户输入
                 --cluster-to <arg>            #slot需要迁移的目的节点的node id，目的节点只能填写一个，不传递该参数的话，则会在迁移过程中提示用户输入
                 --cluster-slots <arg>         #需要迁移的slot数量，不传递该参数的话，则会在迁移过程中提示用户输入。
                 --cluster-yes                 #指定迁移时的确认输入
                 --cluster-timeout <arg>       #设置migrate命令的超时时间
                 --cluster-pipeline <arg>      #定义cluster getkeysinslot命令一次取出的key数量，不传的话使用默认值为10
                 --cluster-replace             #是否直接replace到目标节点
  rebalance      host:port                                      #指定集群的任意一节点进行平衡集群节点slot数量 
                 --cluster-weight <node1=w1...nodeN=wN>         #指定集群节点的权重
                 --cluster-use-empty-masters                    #设置可以让没有分配slot的主节点参与，默认不允许
                 --cluster-timeout <arg>                        #设置migrate命令的超时时间
                 --cluster-simulate                             #模拟rebalance操作，不会真正执行迁移操作
                 --cluster-pipeline <arg>                       #定义cluster getkeysinslot命令一次取出的key数量，默认值为10
                 --cluster-threshold <arg>                      #迁移的slot阈值超过threshold，执行rebalance操作
                 --cluster-replace                              #是否直接replace到目标节点
  add-node       new_host:new_port existing_host:existing_port  #添加节点，把新节点加入到指定的集群，默认添加主节点
                 --cluster-slave                                #新节点作为从节点，默认随机一个主节点
                 --cluster-master-id <arg>                      #给新节点指定主节点
  del-node       host:port node_id                              #删除给定的一个节点，成功后关闭该节点服务
  call           host:port command arg arg .. arg               #在集群的所有节点执行相关命令
  set-timeout    host:port milliseconds                         #设置cluster-node-timeout
  import         host:port                                      #将外部redis数据导入集群
                 --cluster-from <arg>                           #将指定实例的数据导入到集群
                 --cluster-copy                                 #migrate时指定copy
                 --cluster-replace                              #migrate时指定replace
  help           

For check, fix, reshard, del-node, set-timeout you can specify the host and port of any working node in the cluster.
```

## 整合

```
spring:
  redis:
    timeout: 30000
    password: 123456
    cluster:
      nodes:
        - 172.17.1.218:7001
        - 172.17.1.218:7002
        - 172.17.1.218:7003
        - 172.17.1.218:7004
        - 172.17.1.218:7005
        - 172.17.1.218:7006
      max-redirects: 3
    lettuce:
      max-active: 1000
      max-idle: 10
      max-wait: -1
      min-idle: 5
```

新版本建议使用 `Lettuce`：

1、`Jedis` 是直连模式，在多个线程间共享一个 `Jedis` 实例时是线程不安全的，每个线程都去拿自己的 `Jedis` 实例，当连接数量增多时，物理连接成本就较高了。

2、`Lettuce`的连接是基于`Netty`的，连接实例可以在多个线程间共享，如果你不知道`Netty`也没事，大致意思就是一个多线程的应用可以使用同一个连接实例，而不用担心并发线程的数量。通过异步的方式可以让我们更好地利用系统资源。