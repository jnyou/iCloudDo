package org.jnyou.anoteinventoryservice.tools.tree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jnyou
 * @Description: 树工具方法
 * @Detail: 一次性构建多叉树结构
 */
public class TreeUtil {


    public static void main2(String[] args) {
        String str = "["
                + "{id: \"001\", pid: \"root\"},"
                + "{id: \"002\", pid: \"001\"},"
                + "{id: \"003\", pid: \"002\"},"
                + "{id: \"004\", pid: \"003\"},"
                + "{id: \"005\", pid: \"001\"},"
                + "{id: \"006\", pid: \"002\"},"
                + "{id: \"007\", pid: \"005\"}"
                + "]";
        JSONArray arr = JSON.parseArray(str);
        Map<String, TreeNode<JSONObject>> map = new HashMap<>();
        TreeNode _root = null;
        for (int i = 0; i < arr.size(); i++) {
            JSONObject jo = arr.getJSONObject(i);
            TreeNode<JSONObject> node = new TreeNode<>(jo);
            String id = jo.getString("id");
            String pid = jo.getString("pid");
            if (map.containsKey(pid)) {
                TreeNode<JSONObject> parentNode = map.get(pid);
                parentNode.appendChildNode(node);
            }
            map.put(id, node);
            if (_root == null) {
                if ("root".equals(pid)) {
                    _root = node;
                }
            }
        }
        map.clear();
        Tree<JSONObject> tree = new Tree(_root);
//            tree.traversal((TreeNode<JSONObject> node) -> {
//
//                System.out.println(node.getBindData().get("id"));
//                return false;
//
//            }, Tree.TraversalType.LOOP);
    }



    /**
     *  传入指定集合List<Map<String, Object>>
     *      要求一：[{"id":"","pid":""}]
     *      要求二：id有序
     *      返回一个根节点值为root的树
     * @param list
     * @return
     */
    public static final Tree<JSONObject> createTree(List<Map<String, Object>> list){
        // 把集合转成JSONArray
        String str = JSONArray.toJSONString(list);
        JSONArray arr = JSON.parseArray(str);
        // 临时放置树结点
        Map<String, TreeNode<JSONObject>> map = new HashMap<>();
        TreeNode _root = null;
        // 遍历JSONArray
        for (int i = 0; i < arr.size(); i++) {
            JSONObject jo = arr.getJSONObject(i);
            // 把JSON对象转为树结点
            TreeNode<JSONObject> node = new TreeNode<>(jo);;
            /*
             * 如果map中有该结点的父结点 把它放置到父节点中
             */
            String id = jo.getString("id");
            String pid = jo.getString("pid");
            if (map.containsKey(pid)) {
                TreeNode<JSONObject> parentNode = map.get(pid);
                parentNode.appendChildNode(node);
            }
            map.put(id, node);
            if (_root == null) {
                if ("root".equals(pid)) {
                    _root = node;
                }
            }
        }
        map.clear();
        Tree<JSONObject> tree = new Tree(_root);
        return tree;
    }

    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new HashMap();
        map.put("id", "1");
        map.put("pid", "root");
        Map<String, Object> map2 = new HashMap();
        map2.put("id", "2");
        map2.put("pid", "1");
        list.add(map2);
        Map<String, Object> map3 = new HashMap();
        map3.put("id", "3");
        map3.put("pid", "2");
        list.add(map3);
        Map<String, Object> map4 = new HashMap();
        map4.put("id", "4");
        map4.put("pid", "1");
        list.add(map4);
        Map<String, Object> map5 = new HashMap();
        map5.put("id", "5");
        map5.put("pid", "2");
        list.add(map5);
        Map<String, Object> map6 = new HashMap();
        map6.put("id", "6");
        map6.put("pid", "3");
        list.add(map6);

        list.add(map);
        createTree(list);
    }

}
