#### 获取配置文件：

```
wget -P  /usr/local/redis http://download.redis.io/redis-stable/redis.conf
```
#### 修改配置文件：

```
vi /usr/local/redis/redis.conf
```

#### 启动容器：

```
docker run -d --name redis -p 6379:6379 -v /usr/local/redis/redis.conf:/etc/redis/redis.conf redis redis-server /etc/redis/redis.conf --appendonly yes --requirepass '123456'
```

| 参数  | 说明  |
|---|---|
| -d	  |  后台运行容器 |
| --name redis  | 设置容器名为 redis  |
| -p 6379:6379 | 将宿主机 6379 端口映射到容器的 6379 端口  |
| -v /usr/local/redis/redis.conf:/etc/redis/redis.conf |  将宿主机redis.conf映射到容器内 |
| -v /usr/local/redis/data:/data  | 将宿主机 /usr/local/redis/data 映射到容器 /data , 方便备份持久数据 |
| redis-server /etc/redis/redis.conf  | redis 服务以容器内的 redis.conf 配置启动 |
| --appendonly yes  | redis 数据持久化 |
| --requirepass '123456'  | 连接密码 |


#### 进入容器：

```
docker exec -it app_learn /bin/bash
```

#### 测试连接：


```
./redis-cli -h 127.0.0.1 -p 6379
```

输入 auth  123456#你刚才设置的密码 
	
	


