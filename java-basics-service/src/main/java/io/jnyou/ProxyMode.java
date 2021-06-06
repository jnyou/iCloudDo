package io.jnyou;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * ProxyMode
 * 代理模式核心在于将目标方法交于代理对象进行控制，增强被代理对象的功能
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class ProxyMode {

    public static void main(String[] args) {
        Action action = new ActionImpl();
        ProxyObject ps = new ProxyObject(action);
        ps.proxyHander();
    }

}

class ProxyObject {
    private Action target;
    public ProxyObject(Action target){
        this.target = target;
    }

    public void proxyHander(){
        long start = System.currentTimeMillis();
         // 执行目标方法
        target.doAction();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start));
    }
}

interface Action{
    void doAction();
}

class ActionImpl implements Action {

    @Override
    public void doAction() {
        System.out.println("start excute 。。。");
    }
}