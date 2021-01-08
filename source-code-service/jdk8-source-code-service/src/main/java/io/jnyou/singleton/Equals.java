package io.jnyou.singleton;

/**
 * <p>
 *
 * @className Equals
 * @author: JnYou xiaojian19970910@gmail.com
 **/
public class Equals {

    private final char value[];

    public Equals() {
        this.value = new char[0];
    }

    public static void main(String[] args) {
        //String as1 = new String("ava");
        //String as2 = new String("ada");
//        as1.equals(as2)   as1==ava==this    as2==ada==anObject
        String a = "";
    }

    public boolean equals(Object anObject) {	//将String类型转换成 object
        if (this == anObject) {
            return true;	//两个对象是同一个对象  返回true
        }
        //判断比较类型是否为String类型
        if (anObject instanceof String) {
            //将对象转换成String
            String anotherString = (String)anObject;
            //获得字符数组长度
            int n = value.length;
            //比较字符串的长度
//            if (n == anotherString.value.length) {
//                // 获得原数组
//                char v1[] = value;
//                //获得比较数组
//                char v2[] = anotherString.value;
//                //数组初始化的位置
//                int i = 0;
//                //判断数组循环次数
//                while (n-- != 0) {
//                    //比较数组中的值
//                    if (v1[i] != v2[i])
//                        //如果值不相等--直接返回false
//                        return false;
//                    i++;
//                }
//                return true;
//            }
        }
        return false;
    }
}