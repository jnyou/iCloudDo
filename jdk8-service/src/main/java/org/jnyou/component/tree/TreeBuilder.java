package org.jnyou.component.tree;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * TreeBuilder
 *
 * @author: JnYou
 * @date 03月26日 9:58
 **/
public class TreeBuilder {

    public static void main(String[] args) {
        new TreeBuilder().buildTree();
    }

    //模拟从数据库查询出来
    List<Menu> menus = Arrays.asList(
            new Menu(1,"根节点",0),
            new Menu(2,"子节点1",1),
            new Menu(3,"子节点1.1",2),
            new Menu(4,"子节点1.2",2),
            new Menu(5,"根节点1.3",2),
            new Menu(6,"根节点2",1),
            new Menu(7,"根节点2.1",6),
            new Menu(8,"根节点2.2",6),
            new Menu(9,"根节点2.2.1",7),
            new Menu(10,"根节点2.2.2",7),
            new Menu(11,"根节点3",1),
            new Menu(12,"根节点3.1",11)
    );


    public void buildTree(){
        // 查询出所有数据信息
        //获取父节点
        List<Object> collect = menus.stream().filter(m -> m.getParentId() == 0).map(m -> {
            m.setChildList(recursionBuildTree(m, menus));
            return m;
        }).collect(Collectors.toList());

        System.out.println("-------转json输出结果-------");
        System.out.println(JSON.toJSON(collect));
    }

    /**
     * 递归查询子节点
     * @param root  根节点
     * @param all   所有节点
     * @return 根节点信息
     */
    private List<Menu> recursionBuildTree(Menu root, List<Menu> all) {
        return all.stream().filter(m -> Objects.equals(root.getId(),m.getParentId())).map(m ->{
            m.setChildList(recursionBuildTree(m,all));
            return m;
        }).collect(Collectors.toList());
    }

}