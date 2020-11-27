package com.blithe.cms.common.tools.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.*;

/**
 * Description 构建redis连接池，以及一些队列的相关方法
 * Created by crazy
 * Created on 2018/11/8.
 */
public class JedisUtil {
    private static String JEDIS_IP;
    private static int JEDIS_PORT;
    private static String JEDIS_PASSWORD;
    private static int JEDIS_TIMEOUT;
    private static int JEDIS_DATABASE;
    //private static String JEDIS_SLAVE;

    private static JedisPool jedisPool;

    static {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("amtp");
        JEDIS_IP = bundle.getString("redis.host");
        JEDIS_PORT = Integer.parseInt(bundle.getString("redis.port"));
        JEDIS_PASSWORD = bundle.getString("redis.password");
        JEDIS_TIMEOUT = Integer.parseInt(bundle.getString("jedis.timeout"));
        JEDIS_DATABASE = Integer.parseInt(bundle.getString("redis.database"));
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(bundle.getString("jedis.maxTotal")));
        config.setMaxIdle(Integer.parseInt(bundle.getString("jedis.maxIdle")));
        config.setMaxWaitMillis(Long.parseLong(bundle.getString("jedis.maxWaitMillis")));
        config.setTestOnBorrow(Boolean.parseBoolean(bundle.getString("jedis.testOnBorrow")));
        config.setTestOnReturn(Boolean.parseBoolean(bundle.getString("jedis.testOnReturn")));
        config.setTestWhileIdle(Boolean.parseBoolean(bundle.getString("jedis.testWhileIdle")));
        config.setMinEvictableIdleTimeMillis(Long.parseLong(bundle.getString("jedis.minEvictableIdleTimeMillis")));
        config.setTimeBetweenEvictionRunsMillis(Long.parseLong(bundle.getString("jedis.timeBetweenEvictionRunsMillis")));
        config.setNumTestsPerEvictionRun(Integer.parseInt(bundle.getString("jedis.numTestsPerEvictionRun")));
        //jedisPool = new JedisPool(config, JEDIS_IP, JEDIS_PORT, JEDIS_TIMEOUT);
        //redis需要设置密码后才可用下面配置进行操作
        jedisPool = new JedisPool(config, JEDIS_IP, JEDIS_PORT, JEDIS_TIMEOUT,JEDIS_PASSWORD,JEDIS_DATABASE);
    }

    //获取jedis实例
    public synchronized static Jedis getJedis(){
        if(jedisPool!=null){
            return jedisPool.getResource();
        }else{
            return null;
        }
    }

    //返还到连接池
    public static void close(Jedis jedis) {
        try{
            jedisPool.returnResource(jedis);
        }catch (Exception e){
            if(jedis.isConnected()){
                jedis.quit();
                jedis.disconnect();
            }
        }
    }
    public static Jedis select(int index) {
        Jedis jedis =null;
        try{
             jedis =getJedis();
            jedis.select(index);
        }catch (Exception e){
            if(jedis.isConnected()){
                jedis.quit();
                jedis.disconnect();
            }
        }
        return jedis;
    }

    /**
     * 存储REDIS队列 顺序存储
     * @param  key reids键名
     * @param  value 键值
     */
    public static Long lpush(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long lpush = jedis.lpush(key, value);
            return lpush;
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
            return null;
        } finally {
            //返还到连接池
            close(jedis);
        }
    }

    /**
     * 存储REDIS队列 反向存储
     * @param  key reids键名
     * @param  value 键值
     */
    public static Long rpush(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long rpush = jedis.rpush(key, value);
            return rpush;
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
            return null;
        } finally {
            //返还到连接池
            close(jedis);
        }
    }

    /**
     * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端
     * @param  key reids键名
     * @param  destination 键值
     */
    public static void rpoplpush(byte[] key, byte[] destination) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.rpoplpush(key, destination);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
    }

    /**
     * 获取队列List数据
     * @param  key 键名
     * @return
     */
    public static List lpopList(byte[] key) {
        List list = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            list = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);

        }
        return list;
    }

    /**
     * 获取队列List数据【指定开始结束范围】
     * @param  key 键名
     * @param  start 开始位置
     * @param  end 结束位置
     * @return
     */
    public static List lpopList(byte[] key,int start,int end) {
        List list = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            list = jedis.lrange(key, start, end);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);

        }
        return list;
    }

    /**
     * 获取队列List数据【指定长度】
     * @param  key 键名
     * @param  len 指定长度 0则返回全部数据
     * @return
     */
    public static List lpopListByLen(byte[] key,int len) {
        List list = null;
        Jedis jedis = null;
        try {
            len = len>1?len-1:-1;
            jedis = jedisPool.getResource();
            list = jedis.lrange(key, 0, len-1);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);

        }
        return list;
    }

    /**
     * 获取队列List长度
     * @param  key 键名
     * @return
     */
    public static int lpopListLen(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrange(key, 0, -1).size();
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
        return 0;
    }
    /**
     * 获取先进先出的队列数据
     * @param  key 键名
     * @return
     */
    public static byte[] rpop(byte[] key) {

        byte[] bytes = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            bytes = jedis.rpop(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
        return bytes;
    }

    /**
     * 获取后进先出的队列数据
     * @param  key 键名
     * @return
     */
    public static byte[] lpop(byte[] key) {

        byte[] bytes = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            bytes = jedis.lpop(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
        return bytes;
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @param srckey
     * @param dstkey
     * @param timout
     * @return
     */
    public static byte[] brpoplpush(byte[] srckey,byte[] dstkey,int timout){
        byte[] value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.brpoplpush(srckey,dstkey,timout);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 返回列表 key 的长度
     * 如果 key 不存在，则 key 被解释为一个空列表，返回 0
     * 如果 key 不是列表类型，返回一个错误。
     * @param key
     * @return
     */
    public static long llen(byte[] key) {
        long len = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.llen(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
        return len;
    }


    /**
     * 删除1个key
     * @param key
     */
    public static void del(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
    }

    /**
     * 返回key中所有域与其值-序列化
     * @param key
     * @return
     */
    public static Map hgetAll(byte[] key) {
        Map result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hgetAll(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();

        } finally {
            //返还到连接池
            close(jedis);
        }
        return result;
    }

    /**
     * 存储hash数据类型
     * @param key key
     * @param hash map结构
     */
    public static void hmset(Object key, Map hash) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hmset(key.toString(), hash);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();

        } finally {
            //返还到连接池
            close(jedis);

        }
    }

    /**
     * 存储hash数据类型
     * @param key key
     * @param hash map结构
     * @param time 超时时间
     */
    public static void hmset(Object key, Map hash, int time) {
        Jedis jedis = null;
        try {

            jedis = jedisPool.getResource();
            jedis.hmset(key.toString(), hash);
            jedis.expire(key.toString(), time);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();

        } finally {
            //返还到连接池
            close(jedis);

        }
    }

    /**
     * 存储hash数据类型-序列化存储
     * @param key  key
     * @param hash map接口
     */
    public static void hmset(byte[] key, Map<byte[],byte[]> hash) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hmset(key,hash);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();

        } finally {
            //返还到连接池
            close(jedis);
        }
    }

    /**
     * 存储hash数据类型-序列化存储
     * @param key  key
     * @param hash map接口
     * @param time 超时时间
     */
    public static void hmset(byte[] key, Map<byte[],byte[]>  hash, int time) {
        Jedis jedis = null;
        try {

            jedis = jedisPool.getResource();
            jedis.hmset(key, hash);
            jedis.expire(new String(key), time);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();

        } finally {
            //返还到连接池
            close(jedis);

        }
    }

    /**
     * 返回key中field1 field2 fieldN域的值
     * @param key  key
     * @param fields map的key
     * @return
     */
    public static List hmget(Object key, String... fields) {
        List result = null;
        Jedis jedis = null;
        try {

            jedis = jedisPool.getResource();
            result = jedis.hmget(key.toString(), fields);

        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();

        } finally {
            //返还到连接池
            close(jedis);

        }
        return result;
    }

    /**
     * 获取key中所有的map键
     * @param key key
     * @return
     */
    public static Set hkeys(String key) {
        Set result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hkeys(key);

        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();

        } finally {
            //返还到连接池
            close(jedis);

        }
        return result;
    }

    /**
     * LREM key count value
     * 删除列表指定的值
     * 第二个参数为删除的个数（有重复时）
     * 后add进去的值先被删，类似于出栈
     *
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
     * count 的值可以是以下几种：
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     * count = 0 : 移除表中所有与 value 相等的值。
     */
    public static Long lrem(byte[] key, byte[] value,Long count){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long lrem = jedis.lrem(key, count, value);
            return lrem;
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
            return null;
        } finally {
            //返还到连接池
            close(jedis);
        }
    }

    public static Long incr(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long incr = jedis.incr(key);
            return incr;
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
            return null;
        } finally {
            //返还到连接池
            close(jedis);
        }
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @param expireTime 过期时间
     * @return
     */
    public static boolean set(String key, String value, int expireTime) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            //为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
            jedis.expire(key,expireTime);
            result = true;
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }finally {
            //返还到连接池
            close(jedis);
        }
        return result;
    }

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * @param key
     * @param expireTime 过期时间
     * @return
     */
    public static boolean expire(String key, int expireTime) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key,expireTime);
            result = true;
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }finally {
            //返还到连接池
            close(jedis);
        }
        return result;
    }

    /**
     * Redis TTL命令以秒为单位返回key的剩余过期时间。
     * 当key不存在时，返回-2。
     * 当key存在但没有设置剩余生存时间时，返回-1。
     * 否则，以秒为单位，返回key的剩余生存时间。
     * @param key
     * @return
     */
    public static Long ttl(String key) {
        Jedis jedis = null;
        Long ttl = -2L;
        try {
            jedis = jedisPool.getResource();
            ttl = jedis.ttl(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }finally {
            //返还到连接池
            close(jedis);
        }
        return ttl;
    }

    /**
     * key是否存在
     * @param key
     * @return
     */
    public static boolean exists(String key) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = jedisPool.getResource();
            result = jedis.exists(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }finally {
            //返还到连接池
            close(jedis);
        }
        return result;
    }

    /**
     * 设置key value并指定这个键值的有效期
     *
     * @param key
     * @param seconds
     * @param value
     * @return
     */
    public static boolean setex(String key, int seconds, String value) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key, seconds, value);
            result = true;
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }finally {
            //返还到连接池
            close(jedis);
        }
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }finally {
            //返还到连接池
            close(jedis);
        }
        return result;
    }
    
    /**
     * 批量读取缓存
     * @param pattern
     * @return
     */
    public static Set partten(String pattern) {
        Jedis jedis = null;
        Set result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.keys(pattern);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }finally {
            //返还到连接池
            close(jedis);
        }
        return result;
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public static Object getList(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return unserialize(jedis.get(key.getBytes()));
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }finally {
            //返还到连接池
            close(jedis);
        }
        return "--";
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {

        }
        return null;
    }
}
