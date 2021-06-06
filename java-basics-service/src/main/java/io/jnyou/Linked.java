package io.jnyou;

import lombok.Data;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * NodeManager
 * 链表数据结构定义
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class Linked {


}

class NodeManager{
    // 定义根节点
    private Node root;

    @Data
    private class Node{
        // 下一个指针，把当前类型作为自己的属性
        private Node node;
        // 存储的数据
        private int data;

        public Node(int data){
            this.data = data;
        }
        // 添加
        public void addNode(int data){

        }
        public void delNode(int data){

        }
        public void listAllNode(){

        }
        public boolean findNode(int data){
            return false;
        }
        public void uodateNode(int oldData,int newData){

        }
        // 插入
        public void insertNode(int index,int data){

        }


    }
}

