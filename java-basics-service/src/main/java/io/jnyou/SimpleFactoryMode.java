package io.jnyou;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SimpleFactoryMode
 * 核心：用工厂类来创建需要de对象
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class SimpleFactoryMode {

    public static void main(String[] args) {
//        Product product = new Appel();
//        product.doAction();
//
//        Product banner = new Banner();
//        banner.doAction();

        Product product = ProductFactory.getProduct("banner");
        product.doAction();
    }

}

// 工厂类
class ProductFactory{
    public static Product getProduct(String name){
        if("apple".equals(name)){
            return new Appel();
        } else if("banner".equals(name)){
            return new Banner();
        } else {
            return null;
        }
    }
}


interface Product {
    void doAction();
}


class Appel implements Product{

    @Override
    public void doAction() {
        System.out.println("开始生产苹果");
    }
}

class Banner implements Product{

    @Override
    public void doAction() {
        System.out.println("开始生产图像");
    }
}