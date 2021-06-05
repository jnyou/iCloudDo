package io.jnyou;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * TempleteMode
 * 抽象类应用-模板方法模式：定义操作中的算法的骨架，而将一些可变部分的实现延迟到子类中。模板方法模式
 * 使得子类可以不改变一个算法的结构即可重新定义该算法的某些特定的步骤。
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class TempleteMode {

    public static void main(String[] args) {
        Manager m = new UserManager();
        m.action("admin","admin","add");
    }

}

/**
 * 模板方法的骨架
 */
abstract class Manager{

    public void action(String name,String password,String opertor){
        if("admin".equals(name) && "admin".equals(password)){
            excute(opertor);
        } else {
            System.out.println("你没有访问权限，请联系管理员。");
        }
    }

    public abstract void excute(String opertor);

}

class UserManager extends Manager{

    @Override
    public void excute(String opertor) {
        if("add".equals(opertor)){
            System.out.println("执行了添加方法");
        }else if("del".equals(opertor)){
            System.out.println("执行了删除方法");
        }
    }
}

class RoleManager extends Manager{
    @Override
    public void excute(String opertor) {
        System.out.println("...");
    }
}