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

    public static void main(String[] args) {
        NodeManager node = new NodeManager();
        // 添加节点数据
        node.add(1);
        node.add(2);
        node.add(3);
        node.add(4);
        node.add(5);

        // 查询节点所有数据
        node.list();

        // 更新节点数据
        node.update(2,10);

        node.list();

        // 删除节点数据
        node.delete(1);
        node.list();

        System.out.println(node.find(2));
        System.out.println(node.find(10));

        // 插入
        node.insert(3,20);
        node.list();

    }

}

class NodeManager{
    private int currentNode;
    // 定义根节点
    private Node root;
    // 增删改查
    public void add(int data){
        if(root == null) {
            root = new Node(data);
        } else {
            root.addNode(data);
        }
    }
    // TODO
    public void update(int oldData,int newData){
        if(root == null) return;
        if(root.getData() == oldData){
            root.setData(newData);
        } else {
            root.updateNode(oldData,newData);
        }
    }
    public void delete(int data){
        if(root == null) return;
        if(root.getData() == data){
            root = root.next;
        } else {
            root.delNode(data);
        }
    }
    public void list(){
        if(root != null){
            System.out.print(root.getData() + "-->");
            root.listAllNode();
            System.out.println();
        }
    }
    public boolean find(int data){
        if(root == null) return false;
        if(root.getData() == data){
            return true;
        } else {
            return root.findNode(data);
        }
    }
    public void insert(int index,int data){
        if(index < 0) return;
        currentNode = 0;
        // 在根节点前面插入
        if(currentNode == index) {
            Node node = new Node(data);
            // 前插法  // 后插法：root.next = node;
            node.next = root;
            root = node;
        } else {
            root.insertNode(index,data);
        }
    }

    @Data
    private class Node{
        // 下一个指针，把当前类型作为自己的属性
        private Node next;
        // 存储的数据
        private int data;

        public Node(int data){
            this.data = data;
        }
        // 添加
        public void addNode(int data){
            if(this.next == null){
                this.next = new Node(data);
            } else {
                this.next.addNode(data);
            }
        }
        // 删除
        public void delNode(int data){
            if(this.next == null) return;
            if(this.next.data == data){
                this.next = this.next.next;
            } else {
                this.next.delNode(data);
            }
        }
        // 查询所有节点
        public void listAllNode(){
            if(this.next != null){
                System.out.print(this.next.getData() + "-->");
                this.next.listAllNode();
            }
        }
        // 查找某个节点
        public boolean findNode(int data){
            if(this.next != null){
                if(this.next.data == data){
                    return true;
                } else {
                    return this.next.findNode(data);
                }
            }
            return false;
        }
        // 更新节点
        public void updateNode(int oldData,int newData){
            if(this.next == null) return;
            if(this.next.data == oldData){
                this.next.data = newData;
            } else {
                this.next.updateNode(oldData,newData);
            }
        }
        // 插入节点
        public void insertNode(int index,int data){
            if(this.next == null) return;
            currentNode++;
            if(currentNode == index){
                Node node = new Node(data);
                node.next = this.next;
                this.next = node;
            } else {
                this.next.insertNode(index,data);
            }
        }


    }
}

