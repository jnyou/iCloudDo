package io.jnyou;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * StrategyModeV2
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class StrategyModeV2 {

    public static void main(String[] args) {
        AssembleV2 assembleV2 = new BizCode2(new customV1());
        assembleV2.excete("xx",StrategyType.DATABASE);
    }

}

interface BehaviorV2{
    // 定义好接口抽象算法
    void save(String data,StrategyType type);
}

class customV1 implements BehaviorV2 {

    @Override
    public void save(String data, StrategyType type) {
        System.out.println(type + "数据");
    }
}

class customV2 implements BehaviorV2 {

    @Override
    public void save(String data, StrategyType type) {
        System.out.println(type + "数据");
    }
}

// 组装好抽象接口
abstract class AssembleV2 {

    private BehaviorV2 behaviorV2;
    public AssembleV2 (BehaviorV2 behaviorV2) {
        this.behaviorV2 = behaviorV2;
    }

    public void excete(String data,StrategyType type) {
        behaviorV2.save(data,type);
        System.out.println(data +"数据保存在"+type+"成功。。。");
    }
}

enum StrategyType{
    NET,DATABASE;
}

class BizCode2 extends AssembleV2{

    public BizCode2(BehaviorV2 behaviorV2) {
        super(behaviorV2);
    }
}

