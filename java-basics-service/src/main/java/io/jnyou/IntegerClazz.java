package io.jnyou;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * IntegerClazz
 *
 * @author: youjiannan
 * @date 04月19日 14:38
 **/
public class IntegerClazz {

    public static void main(String[] args) {
        demo4();
    }

    /**
     * 两个 new Integer() 变量比较 ，永远是 false
     */
    public static void demo1() {
        Integer i = new Integer(100);
        Integer j = new Integer(100);
        //false，因为new生成的是两个对象，其内存地址不同
        System.out.print("两个 new Integer() 变量比较 ，永远是 false" + (i == j));
    }

    /**
     * Integer变量 和 new Integer() 变量比较 ，永远为 false
     */
    public static void demo2() {
        Integer i1 = new Integer(100);
        Integer j1 = 100;
        //false 因为 Integer变量 指向的是 java 常量池 中的对象，而 new Integer() 的变量指向 堆中 新建的对象，两者在内存中的地址不同。
        System.out.print("Integer变量 和 new Integer() 变量比较 ，永远为 false。" + (i1 == j1));
    }

    /**
     * 两个Integer 变量比较，如果两个变量的值在区间-128到127 之间，则比较结果为true，
     * 如果两个变量的值不在此区间，则比较结果为 false 。
     *
     * 分析：Integer i = 100 在编译时，会翻译成为 Integer i = Integer.valueOf(100)，而 java 对 Integer类型的 valueOf 的定义如下：
     * public static Integer valueOf(int i){
     *     assert IntegerCache.high >= 127;
     *     if (i >= IntegerCache.low && i <= IntegerCache.high){
     *         return IntegerCache.cache[i + (-IntegerCache.low)];
     *     }
     *     return new Integer(i);
     * }
     * java对于-128到127之间的数，会进行缓存。
     *
     * 所以 Integer i = 127 时，会将127进行缓存，下次再写Integer j = 127时，就会直接从缓存中取，就不会new了。
     */
    public static void demo3() {
        Integer i2 = 100;
        Integer j2 = 100;
        System.out.print(i2 == j2); //true

        Integer i3 = 128;
        Integer j3 = 128;
        System.out.print(i3 == j3); //false
    }

    /**
     * 自动拆箱
     *
     * 因为包装类Integer 和 基本数据类型int 比较时，java会自动拆包装为int ，然后进行比较，实际上就变为两个int变量的比较。
     */
    public static void demo4() {
        Integer i = new Integer(100); //自动拆箱为 int i=100; 此时，相当于两个int的比较
        int j = 100;
        System.out.print(i == j); //true
    }

}

class IntegerDemo {
    public static void main(String[] args) {
        int i = 128;
        Integer i2 = 128;
        Integer i3 = new Integer(128);
        System.out.println("i == i2 = " + (i == i2)); // Integer会自动拆箱为int，所以为true
        System.out.println("i == i3 = " + (i == i3)); // true，理由同上
        System.out.println(i2 == i3);

        // -128~127内不会new一个新的对象
        Integer i4 = 127;// 编译时被翻译成：Integer i4 = Integer.valueOf(127);
        Integer i5 = 127;
        System.out.println("i4 == i5 = " + (i4 == i5));// true

        // 不在-128~127内将会new一个新的对象
        Integer i6 = 128;
        Integer i7 = 128;
        System.out.println("i6 == i7 = " + (i6 == i7));// false

        Integer i8 = new Integer(127);
        System.out.println("i5 == i8 = " + (i5 == i8)); // false

        Integer i9 = new Integer(128);
        Integer i10 = new Integer(128);
        System.out.println("i9 == i10 = " + (i9 == i10)); // false
    }

}

class Campare {
    public static void main(String[] args) {
        Integer a = new Integer(127), b = new Integer(128);

        int c = 127, d = 128, dd = 128;
        Integer e = 127, ee = 127, f = 128, ff = 128;

        System.out.println(a == b); // false 因为a,b都是new出来的对象，地址不同所以为false
        System.out.println(a == c); // true  a会自动拆箱为int类型
        System.out.println(a == e); // false 指向的地址不同a是new出来的
        System.out.println(e == c); // true  e会自动拆箱为int类型
        System.out.println(e == ee);// true  Integer对 处于-128到127范围之间，指向了同一片地址区域
        System.out.println(b == f); // false 指向的地址不同b是new出来的
        System.out.println(f == d); // true  f自动拆箱为int类型
        System.out.println(f == ff);// false IntegerCache()方法
        System.out.println(d == dd);// true
    }
}

class Campare1{
    public static void main(String[] args) {
        Integer i01 = 59;
        int i02 = 59;
        Integer i03 =Integer.valueOf(59);
        Integer i04 = new Integer(59);


        System.out.println(i01== i02); // true
        System.out.println(i01== i03); // true
        System.out.println(i03== i04); // false
        System.out.println(i02== i04); // true
    }
}