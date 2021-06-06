package io.jnyou;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * InnerClazz
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class InnerClazz {

    public static void main(String[] args) {
        Outer outer = new Outer();
//        Outer.Inner inner = outer.new Inner();
//        inner.print();
        outer.printInner();// 初始化内部类
    }

}

class Outer{
    private String name;
    // 建议使用方式
    public void printInner(){
        Inner inner = new Inner();
        inner.print();
    }
    class Inner{
        public void print(){
            System.out.println("inner");
        }
    }
}
