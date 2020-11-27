package com.blithe.cms.common.tools.redis;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

/**
 * Description 测试Redis队列
 * @author jnyou
 */
public class TestRedisQueue {
    public static byte[] redisKey = "key".getBytes();
    public static byte[] dstkey = "dstkey".getBytes();
    static {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //存储一些数据
    private static void init() throws IOException {
        Jedis jedis = JedisUtil.getJedis();
        for (int i = 1; i < 6; i++) {
            Message message = new Message(i, "这是第" + i + "个内容");
            try {
                Long lpush = JedisUtil.lpush(redisKey, ObjectUtil.objectToBytes(message));
                System.out.println("插入成功=========="+lpush);
                System.out.println(jedis.lrange(redisKey,0,-1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取队列数据
     */
    private static void rpop() throws Exception {
        byte[] bytes = JedisUtil.rpop(redisKey);
        Message msg = (Message) ObjectUtil.bytesToObject(bytes);
        if (msg != null) {
            System.out.println(msg.getId() + "----" + msg.getContent());
        }
    }

    private static void brpoplpush() throws Exception{
        Jedis jedis = JedisUtil.getJedis();
        while (true){
            try {
                byte[] bytes = JedisUtil.brpoplpush(redisKey, dstkey, 0);
                Message msg = (Message) ObjectUtil.bytesToObject(bytes);
                if(msg != null){
                    System.out.println(msg.getId() + "----" + msg.getContent());
                }
                System.out.println(jedis.lrange(redisKey,0,-1));
                System.out.println(jedis.lrange(dstkey,0,-1));
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            //rpop();
            //brpoplpush();

            //遍历list
            List list = JedisUtil.lpopList(redisKey);
            System.out.println(list.size());
            for (Object mess:list) {
                Message msg = (Message) ObjectUtil.bytesToObject((byte[]) mess);
                System.out.println(msg.getId() + "----" + msg.getContent());
            }

            //反向存储
            /*Message message = new Message(10, "这是第" + 10 + "个内容");
            JedisUtil.rpush(redisKey,ObjectUtil.objectToBytes(message));*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
