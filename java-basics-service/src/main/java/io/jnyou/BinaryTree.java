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

    // 根节点
    private Node root;

    // 添加
    public void add(int data){
         // 根节点为空，直接插入到根节点中，否则递归插入
        if(root == null){
            root = new Node(data);
        } else {
            root.addNode(data);
        }
    }

    // 打印输出
    public void print(){
        root.printNode();
    }

    // 查找
    public boolean find(int data){
        if(root != null){
            if(root.data == data){
                return true;
            } else {
                return root.findNode(data);
            }
        }
        return false;
    }

    private class Node{
        // 插入的数据
        private int data;
        // 左子节点
        private Node left;
        // 右子节点
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
            // 中序遍历输出  左中右
            if(this.left != null){
                this.left.printNode();
            }
            System.out.print(this.data + "->");
            if(this.right != null){
                this.right.printNode();
            }
        }

        public boolean findNode(int data){
            if(this.data > data){
                // 左边找
                if(this.left != null){
                    if(this.left.data == data){
                        return true;
                    }else {
                        return this.left.findNode(data);
                    }
                }
            } else {
                if(this.right != null){
                    if(this.right.data == data){
                        return true;
                    } else {
                        return this.right.findNode(data);
                    }
                }
            }
            return false;
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

        System.out.println(bt.find(15));
    }
}