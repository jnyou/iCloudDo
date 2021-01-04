package org.jnyou.anoteinventoryservice.tools.tree;

/**
 * 树型数据存储结构
 *
 * @author jnyou
 * @param <T>
 */
public class Tree<T> {

    private final TreeNode<T> root;

    /**
     * 构造方法
     *
     * @param root 根节点
     */
    public Tree(TreeNode<T> root) {
        this.root = root;
    }

    /**
     * 获取根节点
     *
     * @return 根节点
     */
    public TreeNode<T> getRoot() {
        return root;
    }

    /**
     * 获取树的总深度
     *
     * @return 总深度
     */
//    public int getDepth() {
//        int[] n = new int[1];
//        traversal((TreeNode<T> node) -> {
//            int nodeDepth = node.getDepth();
//            if (nodeDepth > n[0]) {
//                n[0] = nodeDepth;
//            }
//            return false;
//        }, TraversalType.LOOP);
//        return n[0];
//    }

    /**
     * 添加一个孩子节点
     *
     * @param node 待添加的孩子节点
     */
    public void appendChildNode(TreeNode<T> node) {
        root.appendChildNode(node);
    }

    /**
     * 移除此节点中指定的孩子节点（从孩子节点集合中移除节点数据）
     *
     * @param index 孩子节点列表索引
     */
    public void removeChildNode(int index) {
        root.removeChildNode(index);
    }

    /**
     * 遍历节点或者查找节点
     *
     * @param visiter 节点访问方法
     * @param type 遍历方式
     */
//    public void traversal(TreeNode.TreeNodeVisiter<T> visiter, TraversalType type) {
//        switch (type) {
//            case LOOP:
//                root.loopTraversal(visiter);
//                break;
//            default:
//                root.recursiveTraversal(visiter, null);
//                break;
//        }
//    }

    /**
     * 遍历节点或者查找节点，默认遍历方式为@see(TraversalType.LOOP)
     *
     * @param visiter 节点访问方法
     */
//    public void traversal(TreeNode.TreeNodeVisiter<T> visiter) {
//        traversal(visiter, TraversalType.LOOP);
//    }

    /**
     * 清除所有节点
     */
//    public void clear() {
//        List<TreeNode<T>> list = new ArrayList<>();
//        traversal((TreeNode<T> node) -> {
//            if (node.childrenCount() > 0) {
//                list.add(node);
//            }
//            return false;
//        }, TraversalType.LOOP);
//        list.forEach((TreeNode<T> node) -> {
//            node.clearChildren();
//        });
//        list.clear();
//    }

    /**
     * 节点遍历类型枚举
     */
     public static enum TraversalType {
        /**
         * 迭代
         */
        LOOP,
        /**
         * 递归
         */
        RECURSIVE;
    }

}
