package io.jnyou;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * AdapterMode
 * 适配器模式：将一个原本不匹配的接口通过适配器模式将其可以适配上去
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class AdapterMode {

    public static void main(String[] args) {
        PowerB b = new ElecB();
        Adapter adapter = new Adapter(b);
        work(adapter);

    }

    public static void work(PowerA a){
        a.insert();
    }
}

// 适配器组装
class Adapter implements PowerA {

    private PowerB powerB;
    public Adapter(PowerB powerB){
        this.powerB = powerB;
    }

    @Override
    public void insert() {
        powerB.connect();
    }
}

interface PowerA{
    void insert();
}

interface PowerB{
    void connect();
}

class ElecA implements PowerA {
    @Override
    public void insert() {
        System.out.println("A开始工作");
    }
}

class ElecB implements PowerB{

    @Override
    public void connect() {
        System.out.println("开始连接B");
    }
}



