package com.atguigu.tree;

/**
 * @Date 2020/4/2 6:32
 * @Auther 梁伟
 * @Description 二叉排序树，满足小于父节点的值放左节点，大于父节点的值放右节点。中序遍历该树得到的是从小到大的排序结果，能出现这样的结果正是利用了
 * 二叉排序树插入节点时的特性，即左小右大
 *
 * 在对二叉排序树删除节点时又三种情况
 * 1.是叶子节点直接删除
 * 2.被删除节点只有一个子节点，将自己的孩子过继给父节点
 * 3，被删除节点左右节点都有时，由排序二叉树中序遍历的特性可知该节点中序遍历下的前后节点可替代它，由此可知需要找到被删除节点左子树下的最大节点或右子树下的最小节点来替换被删除位置，
 * 用于替换的节点的删除继续递归按照这3个条件删除即可
 * 如二叉排序树中序遍历结果：2,7,9,15,23,45，要删除15，那么必然使用9或者23来替代它
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2, 11};
        BinarySortTree binarySortTree = new BinarySortTree();
        //循环的添加结点到二叉排序树
        for(int i = 0; i< arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }

        //中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树~");
        binarySortTree.infixOrder(); // 1, 3, 5, 7, 9, 10, 12

        //测试删除节点
        binarySortTree.delNode(10);

        binarySortTree.delNode(5);
        binarySortTree.delNode(10);
        binarySortTree.delNode(2);
        binarySortTree.delNode(3);

        binarySortTree.delNode(9);
        binarySortTree.delNode(1);
        binarySortTree.delNode(7);

        System.out.println("删除结点后");
        binarySortTree.infixOrder();
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
     *  获取到以node为根结点的二叉排序树的最小值节点，然后将该节点移动到node，从而完成删除节点操作。
     *  对于二叉排序树调用该方法时注意需要传入的是被删除节点的右节点，而不是被删除节点自己
     *  属于删除节点的第三种情况即被删除节点有左右节点
     * @param node 传入的结点(当做二叉排序树的根结点)
     * @return 返回的 以node 为根结点的二叉排序树的最小结点的值
     */
    public Integer delRightTreeMin(Node node) {
        if (node == null) {
            return null;
        }
        Node targetNode = node;
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
    public Integer delLeftTreeMax(Node node) {
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
        Node delNode = search(value);
        //要删除的节点不存在
        if (delNode == null) {
            return;
        }
        //只有找到父节点才能实现删除，即自己不能删除自己
        Node parentNode = searchParent(value);
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
            Node inheritedNode;
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