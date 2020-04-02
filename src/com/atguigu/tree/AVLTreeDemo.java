package com.atguigu.tree;

import com.atguigu.util.TreeNode;
import com.atguigu.util.Utils;

/**
 * author:w_liangwei
 * date:2020/4/2
 * Description: 平衡二叉树，为了解决二叉排序树在插入或者删除时造成数的左右高度
 *
 * 平衡因子=左子树高度-右子树高度
 * 添加节点时不平衡导致的旋转有LL、LR、RL、RR四种类型。L代表left，R代表right
 *
 * 在旋转时引入节点碰撞的概念，即同一个节点上出现了挂2个左节点或右节点的情况，那么被碰撞的节点挂到发起碰撞节点的子节点上。
 * 左旋挂右节点，右旋挂左节点。具体不理解可以看算法动态图的演示
 *
 * 网址：https://www.cs.usfca.edu/~galles/visualization/Algorithms.html
 *
 * 1.找到失衡的点，确定最小的失衡子树。以失衡节点为根节点到新添加的节点看途中经过了哪些节点，包括失衡点和新添加节点。取最开始的三个节点就是要平衡的节点
 * 2.对不平衡的子树进行中序遍历，看失衡的三个节点在中序遍历中的前后关系，将在中间的失衡节点挑选为根节点，两边的为左右节点来构建新的子树
 * 3.然后处理碰撞节点或新插入的值
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
        int[] arr = {4,3,6,5,7,8};

        //创建一个 AVLTree对象
        AVLTree avlTree = new AVLTree();
        //添加结点
        for(int i=0; i < arr.length; i++) {
            avlTree.add(new Node1(arr[i]));
        }

        //遍历
        System.out.println("中序遍历");
        avlTree.infixOrder();

        System.out.println("平衡处理前~~");
        System.out.println("树的高度=" + avlTree.getRoot().height()); //3
        System.out.println("树的左子树高度=" + avlTree.getRoot().leftHeight()); // 2
        System.out.println("树的右子树高度=" + avlTree.getRoot().rightHeight()); // 2
        System.out.println("当前的根结点=" + avlTree.getRoot());//8

        Utils.pirnt(avlTree.getRoot());
        avlTree.getRoot().leftRotate();
        avlTree.infixOrder();
        Utils.pirnt(avlTree.getRoot());

        System.out.println("平衡处理后~~");
        System.out.println("树的高度=" + avlTree.getRoot().height()); //3
        System.out.println("树的左子树高度=" + avlTree.getRoot().leftHeight()); // 2
        System.out.println("树的右子树高度=" + avlTree.getRoot().rightHeight()); // 2
        System.out.println("当前的根结点=" + avlTree.getRoot());//8
    }
}

class AVLTree {
    private Node1 root;


    public Node1 getRoot() {
        return root;
    }

    /**
     *  获取到以node为根结点的二叉排序树的最小值节点，然后将该节点移动到node，从而完成删除节点操作。
     *  对于二叉排序树调用该方法时注意需要传入的是被删除节点的右节点，而不是被删除节点自己
     *  属于删除节点的第三种情况即被删除节点有左右节点
     * @param node 传入的结点(当做二叉排序树的根结点)
     * @return 返回的 以node 为根结点的二叉排序树的最小结点的值
     */
    public Integer delRightTreeMin(Node1 node) {
        if (node == null) {
            return null;
        }
        Node1 targetNode = node;
        //根据二叉排序树的特性一直走左子树就能获取到最小值
        while (targetNode.left != null) {
            targetNode = targetNode.left;
        }
        //删除找到的最小节点，因为后续要将该值移动到被删除节点
        delNode(targetNode.value);
        return targetNode.value;
    }

    /**
     * delRightTreeMin相反找的是左子树的最大值，返回该值并删除该节点
     * @param node
     * @return
     */
    public Integer delLeftTreeMax(Node1 node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        delNode(node.value);
        return node.value;
    }

    /**
     * 删除结点
     */
    public void delNode(int value) {
        Node1 delNode = search(value);
        //要删除的节点不存在
        if (delNode == null) {
            return;
        }
        //只有找到父节点才能实现删除，即自己不能删除自己
        Node1 parentNode = searchParent(value);
        //被删除节点存在且没有父节点，说明要删除的是根结点。对根节点的删除可以分为三种情况
        //根无子：直接置为null。根有一个子：过继节点。根有二个子，第三种方式删除。针对后两种情况其实我们后边已经做过了处理，唯一需要处理的就是根无子的情况
        if (parentNode == null && delNode.left == null && delNode.right == null) {
            //由于使用search方法返回的delNode对变量本身置为null是无效的，所以使用root变量来置为null
            root = null;
            return;
        }
        //第一种情况，删除叶子节点
        if (delNode.left == null && delNode.right == null) {
            //判断被删除节点是父节点的左节点还是右节点
            if (parentNode.left == delNode) {
                parentNode.left = null;
            } else {
                parentNode.right = null;
            }
        } else if (delNode.left != null && delNode.right != null) {
            //第三种情况，被删除节点下左右子节点都有
//            Integer rightTreeMin = delRightTreeMin(delNode.right);
            Integer delLeftTreeMax = delLeftTreeMax(delNode.left);
            //替换被删除节点的值
            delNode.value = delLeftTreeMax;
        } else {
            //第二种情况，被删除节点下只有一个子节点。那么将被删除节点的子节点过继给父节点
            Node1 inheritedNode;
            //确定被过继的是左右中的哪个节点
            if (delNode.left != null) {
                inheritedNode = delNode.left;
            } else {
                inheritedNode = delNode.right;
            }
            //只有在有父节点的情况下才能过继，判断被删除节点是父节点的左节点还是右节点
            if (parentNode != null) {
                if (parentNode.left == delNode) {
                    parentNode.left = inheritedNode;
                } else {
                    parentNode.right = inheritedNode;
                }
            } else {
                //被删除节点没有父节点，说明要删除的是根结点。直接将要过继的子节点升级为新的根结点
                root = inheritedNode;
            }
        }
    }

    /**
     * @auther 梁伟
     * @Description 查找要删除节点
     * @Date 2020/4/2 7:16
     * @Param [value]
     * @return com.atguigu.tree.Node
     **/
    public Node1 search(int value) {
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
    public Node1 searchParent(int value) {
        if (root == null) {
            return null;
        }
        return root.searchParent(value);
    }

    //添加结点的方法
    public void add(Node1 node) {
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
class Node1 implements TreeNode {
    public int value;
    public Node1 left;
    public Node1 right;

    public Node1(int value) {
        this.value = value;
    }

    /** 左旋转方法
     * @auther 梁伟
     * @Description
     * @Date 2020/4/3 6:27
     * @Param
     * @return
     **/
    public void leftRotate() {
        Node1 newNode = new Node1(value);
        //拼了一个左下角的小块,等待根节点连接
        //过继左节点
        newNode.left = left;
        //移动右节点的左节点到新节点的右节点，此时就构成了待连接的左子树
        newNode.right = right.left;
        //拼右半部分
        //修改根节点值为右节点值，相当于将右节点提升为根节点
        value = right.value;
        //为根节点连接右子树，因为右节点提升为根所以需要跳过自己，连接下一个右节点为右子树
        right = right.right;
        //连接左节点
        left = newNode;
    }

    // 返回左子树的高度
    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    // 返回右子树的高度
    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    /**  重新测试该方法，想想为什么可以测算高度
     * @auther 梁伟
     * @Description 返回以该结点为根结点的树的高度
     * @Date 2020/4/3 6:23
     * @Param
     * @return
     **/
    public int height() {
        return Math.max(this.left == null ? 0 : left.height(), this.right == null ? 0 : right.height()) + 1;
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
    public Node1 searchParent(int value) {
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
    public Node1 search(int value) {
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
    public void add(Node1 node) {
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

        //调整二叉树到平衡状态
//        if (rightHeight() - leftHeight() > 1) {
//            leftRotate();
//        }
    }

    @Override
    public String toString() {
        return "[" + value + "]";
    }

    @Override
    public String getPrintInfo() {
        return toString();
    }

    @Override
    public TreeNode getLeftChild() {
        return left;
    }

    @Override
    public TreeNode getRightChild() {
        return right;
    }
}


