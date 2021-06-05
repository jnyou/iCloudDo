package io.jnyou;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * Strategy
 *
 接口应用-策略模式：定义了一系列的算法，将每一种算法封装起来并可以相互替换使用，
 策略模式让算法独立于使用它的客户应用独立变化。（把可变的行为抽象出来）
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class StrategyMode {

    public static void main(String[] args) {
        Assemble assemble = new BizCode(new DataToNet());
        assemble.excete("小明");
    }

}

interface Behavior{
    // 定义好接口抽象算法
    void save(String data);
}

// 定义两个变化的客户实现行为抽象接口
class DataToDataBase implements Behavior{

    @Override
    public void save(String data) {
        System.out.println("将数据保存在数据库中...");
    }
}

class DataToNet implements Behavior{

    @Override
    public void save(String data) {
        System.out.println("将数据保存在网络中...");
    }
}

// 组装好抽象接口
abstract class Assemble {

    private Behavior behavior;
    public Assemble (Behavior behavior) {
        this.behavior = behavior;
    }

    public void excete(String data) {
        behavior.save(data);
        System.out.println(data +"数据保存成功。。。");
    }
}

/**
 * 业务代码
 */
class BizCode extends Assemble{

    public BizCode(Behavior behavior) {
        super(behavior);
    }
}
