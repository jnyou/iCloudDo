package io.jnyou;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * BinaryTree
 * 二叉树的基本实现
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class BinaryTree {

    private Node root;

    public void add(int data){
        if(root == null){
            root = new Node(data);
        } else {
            root.addNode(data);
        }
    }

    public void print(){
        root.printNode();
    }

    private class Node{
        private int data;
        private Node left;
        private Node right;

        public Node(int data){
            this.data = data;
        }
        public void addNode(int data){
            if(this.data > data){
                // 放左边 当根节点的数据大于需要插入的数据，也就是需要插入的数据小于上一节点的数据，放在节点的左边
                if(this.left == null){
                    this.left = new Node(data);
                } else {
                    this.left.addNode(data);
                }
            } else {
                // 放右边
                if(this.right == null){
                    this.right = new Node(data);
                } else {
                    this.right.addNode(data);
                }
            }
        }
        public void printNode(){
            // 中序排序输出  左中右
            if(this.left != null){
                this.left.printNode();
            }
            System.out.print(this.data + "->");
            if(this.right != null){
                this.right.printNode();
            }
        }
    }
}

class Test{
    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();
        bt.add(2);
        bt.add(20);
        bt.add(15);
        bt.add(35);
        bt.add(10);
        bt.add(25);
        bt.add(99);
        bt.add(32);

        bt.print();
    }
}