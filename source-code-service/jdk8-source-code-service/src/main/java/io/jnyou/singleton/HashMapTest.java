package io.jnyou.singleton;

import java.util.HashMap;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * HashMapTets
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class HashMapTest {

    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap<>(100);
        map.put("1","2");
        String result = map.put("1", "3");
        System.out.println(result);
    }
}