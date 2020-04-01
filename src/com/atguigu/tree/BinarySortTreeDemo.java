package com.atguigu.tree;

/**
 * @Date 2020/4/2 6:32
 * @Auther 梁伟
 * @Description 二叉排序树，满足小于父节点的值放左节点，大于父节点的值放右节点。中序遍历该树得到的是从小到大的排序结果，能出现这样的结果正是利用了
 * 二叉排序树插入节点时的特性，即左小右大
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {

    }
}



/**
 * @auther 梁伟
 * @Description 创建二叉排序树
 * @Date 2020/4/2 7:14
 * @Param
 * @return
 **/
class BinarySortTree {
    private Node root;


    public Node getRoot() {
        return root;
    }

    /**
     * 1. 返回的 以node 为根结点的二叉排序树的最小结点的值
     * 2. 删除node 为根结点的二叉排序树的最小结点
     * @param node 传入的结点(当做二叉排序树的根结点)
     * @return 返回的 以node 为根结点的二叉排序树的最小结点的值
     */
    public int delRightTreeMin(Node node) {
        return 0;
    }

    /**
     * @auther 梁伟
     * @Description 查找要删除节点
     * @Date 2020/4/2 7:16
     * @Param [value]
     * @return com.atguigu.tree.Node
     **/
    public Node search(int value) {
        if (root == null) {
            return null;
        }
        return root.search(value);
    }

    /**
     * @auther 梁伟
     * @Description 查找要删除节点的父节点
     * @Date 2020/4/2 7:17
     * @Param [value]
     * @return com.atguigu.tree.Node
     **/
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        }
        return root.searchParent(value);
    }

    //添加结点的方法
    public void add(Node node) {
        if(root == null) {
            root = node;//如果root为空则直接让root指向node
        } else {
            root.add(node);
        }
    }
    //中序遍历
    public void infixOrder() {
        if(root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空，不能遍历");
        }
    }
}

/**
 * @auther 梁伟
 * @Description 创建Node结点
 * @Date 2020/4/2 7:14
 * @Param
 * @return
 **/
class Node {
    public int value;
    public Node left;
    public Node right;

    public Node(int value) {
        this.value = value;
    }

    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 查找要删除结点的父结点
     * @param value 要找到的结点的值
     * @return 返回的是要删除的结点的父结点，如果没有就返回null
     */
    public Node searchParent(int value) {
        //判断当前节点的左右子节点是否是要删除节点，是则返回父节点
        if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
            return this;
        }
        //根据排序二叉树特性查找要删除节点
        if (value < this.value) {
            //左节点为null，未找到要删除的节点
            if (this.left == null) {
                return null;
            }
            return this.left.searchParent(value);
        } else {
            //右节点为null，未找到要删除的节点
            if (this.right == null) {
                return null;
            }
            return this.right.searchParent(value);
        }
    }

    /**
     * 查找要删除的节点
     * @auther 梁伟
     * @Description
     * @Date 2020/4/2 6:51
     * @Param [value] 要删除的节点值
     * @return void
     **/
    public Node search(int value) {
        if (this.value == value) {
            return this;
        }
        //要查找值与当前节点值比较来决定向左还是向右
        if (value < this.value) {
            //向左子树找
            if (this.left != null) {
                return this.left.search(value);
            }
        } else {
            //向右子树找
            if (this.right != null) {
                return this.right.search(value);
            }
        }
        return null;
    }

    /**
     * 递归的形式添加结点，注意需要满足二叉排序树的要求
     * @auther 梁伟
     * @Description 
     * @Date 2020/4/2 6:49
     * @Param [node] 被添加节点
     * @return void
     **/
    public void add(Node node) {
        if (node == null) {
            return;
        }
        if (node.value < this.value) {
            //左节点不存在那么直接添加节点
            if (this.left == null) {
                this.left = node;
            } else {
                //存在左节点那么继续和左节点比较
                this.left.add(node);
            }
        } else {
            //右节点不存在那么直接添加节点
            if (this.right == null) {
                this.right = node;
            } else {
                //右节点存在那么继续与右节点比较
                this.right.add(node);
            }
        }
    }

    @Override
    public String toString() {
        return "Node [value=" + value + "]";
    }
}