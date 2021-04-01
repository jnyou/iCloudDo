package org.jnyou.clustest;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * ModeTest
 *
 * @author: youjiannan
 * @date 04月01日 9:37
 **/
public class ModeTest {

    // 单例模式
    // 饿汉模式
    private ModeTest(){}

    private static ModeTest instance = new ModeTest();

    public static ModeTest getInstance(){
        return instance;
    }

}

// 懒汉模式
class ModeLazy{

    private ModeLazy(){}

    private static volatile ModeLazy instance = null;

    public static ModeLazy getInstance(){

        if(instance == null){
            synchronized (ModeLazy.class) {
                if(instance == null){
                    instance = new ModeLazy();
                }
            }
        }
        return instance;
    }
}

// 嵌套类
class Nest{
    private Nest(){}

    private static class InnerClazz{
        private static Nest instance = new Nest();
    }
    public static Nest getInstance(){
        return InnerClazz.instance;
    }
}