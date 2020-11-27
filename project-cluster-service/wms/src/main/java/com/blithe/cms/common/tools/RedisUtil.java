package com.blithe.cms.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
public class RedisUtil {

	public Logger logger = LoggerFactory.getLogger(getClass());

	private RedisTemplate<Serializable, Object> redisTemplate;

	public void setRedisTemplate(
			RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 批量删除对应的value
	 *
	 * @param keys
	 */
	public void remove(String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 *
	 * @param pattern
	 */
	public void removePattern(String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0) {
			redisTemplate.delete(keys);
		}
	}

	/**
	 * 删除对应的value
	 * @param key
	 */
	public void remove(String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}
	/**
	 * 判断缓存中是否有对应的value
	 * @param key
	 * @return
	 */
	public boolean notExists(String key) {
		return !redisTemplate.hasKey(key);
	}
	/**
	 * 读取缓存
	 *
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate
				.opsForValue();
		result = operations.get(key);
		return result;
	}


	/**
	 * 模糊查询key
	 *
	 * @param pattern
	 */
	public Set<Serializable> keys(String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		return keys;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 * @param key
	 * @param value
	 * @param expireTime 过期时间
	 * @return
	 */
	public boolean set(String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 设置redis键值缓存,此方法当redis中存在key时返回false,不存在则插入key并返回true
	 * @param key 键
	 * @param value 值
	 * @param seconds 过期时间 单位是秒
     * @return boolean
     */
	public boolean setEx(final String key, final String value, final Long seconds) {
		boolean result = (boolean)redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) {
				try {
					//写缓存
					boolean result = connection.setNX(key.getBytes() , value.getBytes());
					if (result) {
						//写成功则设置过期时间
						connection.expire(key.getBytes(), seconds);
					}
					return result;
				} catch (Exception e) {
					logger.error("redis exception, message:"+e.getMessage());
					return false;
				}
			}
		});
		return result;
	}
	/**
	 * 设置redis键值缓存,此方法当redis中存在key时返回false,不存在则插入key并返回true,默认过期时间一分钟
	 * @param key 键
	 * @param value 值
     * @return boolean
     */
    public boolean setEx(final String key, final String value) {
        return setEx(key, value, 60L);
    }

    /**
     * 存储REDIS队列 顺序存储
     * @param key
     * @param value
     * @return
     */
    public Long lpush(String key, Object value) {
        try {
            return redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量获取key所对应的名称
     * @param keys
     * @return
     */
    public  List<Object> multiGet(Set keys){
    	try {
    		return redisTemplate.opsForValue().multiGet(keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 存储REDIS队列 反向存储
     * @param key
     * @param value
     * @return
     */
    public Long rpush(String key, Object value) {
        try {
            return redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取队列List数据
     * @param  key 键名
     * @return
     */
    public  List lpopList(String key) {
        try {
            return redisTemplate.opsForList().range(key,0,-1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取队列List数据【指定开始结束范围】
     * @param  key 键名
     * @param  start 开始位置
     * @param  end 结束位置
     * @return
     */
    public  List lpopList(String key,int start,int end) {
        try {
            return redisTemplate.opsForList().range(key, start,end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取先进先出的队列数据
     * @param  key 键名
     * @return
     */
    public Object rpop(String key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取后进先出的队列数据
     * @param  key 键名
     * @return
     */
    public Object lpop(String key) {
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回。
     * @param sourceKey
     * @param destinationKey
     * @return
     */
    public Object brpoplpush(String sourceKey,String destinationKey){
        try {
            return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,destinationKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回，如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @param sourceKey
     * @param destinationKey
     * @param timeout
     * @param unit
     * @return
     */
    public Object brpoplpush(String sourceKey,String destinationKey, long timeout, TimeUnit unit){
        try {
            return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,destinationKey,timeout,unit);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
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
    public  Long lrem(String key,String value,Long count){
        try {
            return redisTemplate.opsForList().remove(key,count,value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回列表 key 的长度
     * 如果 key 不存在，则 key 被解释为一个空列表，返回 0
     * 如果 key 不是列表类型，返回一个错误。
     * @param key
     * @return
     */
    public long llen(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return 0;
    }
}
