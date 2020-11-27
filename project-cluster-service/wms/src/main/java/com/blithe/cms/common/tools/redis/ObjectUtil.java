package com.blithe.cms.common.tools.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * java序列化的工具类,主要是将对象转化为byte数组,和根据byte数组反序列化成java对象
 * 注意:每个需要序列化的对象都要实现Serializable接口
 * @author jnyou
 */
public class ObjectUtil {
    /**对象转byte[]
     * @param obj
     * @return
     * @throws //IOException
     */
    public static byte[] objectToBytes(Object obj) throws Exception{
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        byte[] bytes = bo.toByteArray();
        bo.close();
        oo.close();
        return bytes;
    }
    /**byte[]转对象
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object bytesToObject(byte[] bytes) throws Exception{
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = new ObjectInputStream(in);
        return sIn.readObject();
    }

    /**
     * 序列化 hashMap<Object, Object>
     * @param hash
     * @return Map<byte[], byte[]>
     */
    public static Map<byte[], byte[]> serializehmoo(Map<Object, Object> hash) {
        Map<byte[], byte[]> result = new HashMap<>();
        try {
            Set<Object> keys = hash.keySet();
            if (keys != null && keys.size() > 0) {
                for (Object key : keys) {
                    result.put(objectToBytes(key), objectToBytes(hash.get(key)));
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 反序列化 hashMap<byte[], byte[]>
     * @param hash
     * @return Map<Object, Object>
     */
    public static Map<Object, Object> unserializehmoo(final Map<byte[], byte[]> hash) {
        Map<Object, Object> result = new HashMap<>();
        try {
            Set<byte[]> keys = hash.keySet();
            if (keys != null && keys.size() > 0) {
                for (byte[] key : keys) {
                    result.put(bytesToObject(key), bytesToObject(hash.get(key)));
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     *  序列化 hashMap<String, Object>
     * @param hash
     * @return Map<byte[], byte[]>
     */
    public static Map<byte[], byte[]> serializehmbb(final Map<String, Object> hash) {
        Map<byte[], byte[]> result = new HashMap<>();
        try {
            Set<String> keys = hash.keySet();
            if (keys != null && keys.size() > 0) {
                for (String key : keys) {
                    result.put(objectToBytes(key), objectToBytes(hash.get(key)));
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 反序列化 hashMap<byte[], byte[]>
     * @param hash
     * @return Map<String, Object>
     */
    public static Map<String, Object> unserializehmbb(final Map<byte[], byte[]> hash) {
        Map<String, Object> result = new HashMap<>();
        try {
            Set<byte[]> keys = hash.keySet();
            if (keys != null && keys.size() > 0) {
                for (byte[] key : keys) {
                    result.put(bytesToObject(key).toString(), bytesToObject(hash.get(key)));
                }
            }
        } catch (Exception e) {
        }
        return result;
    }
}
