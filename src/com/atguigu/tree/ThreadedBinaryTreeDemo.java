package com.atguigu.tree;

/**
 * 线索化二叉树
 *      由于传统的二叉树总共有2n个指针（left和right），n为节点个数。而n个节点要构成树需要n-1个指针，总共是2n个指针。
 *      那么剩下的空的指针就是2n-(n-1) = n+1个
 * 对二叉树中有的节点可能它的left和right没有关联节点，这时候为了将这部分空间使用起来就是线索化
 * 线索化分为前序线索化，中序线索化，后序线索化三种
 * left指向前驱节点，right指向后继节点。具体指向哪个节点由当前的遍历模式是前序，中序，后序来决定
 * 其本身就是为了方便节点的遍历
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        //测试一把中序线索二叉树的功能
        HeroNode1 root = new HeroNode1(1, "tom");
        HeroNode1 node2 = new HeroNode1(3, "jack");
        HeroNode1 node3 = new HeroNode1(6, "smith");
        HeroNode1 node4 = new HeroNode1(8, "mary");
        HeroNode1 node5 = new HeroNode1(10, "king");
        HeroNode1 node6 = new HeroNode1(14, "dim");

        //二叉树，后面我们要递归创建, 现在简单处理使用手动创建
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        //测试中序线索化
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree(root);
        threadedBinaryTree.threadedNodes();

        //测试: 以10号节点测试
        HeroNode1 leftNode = node5.getLeft();
        HeroNode1 rightNode = node5.getRight();
        System.out.println("10号结点的前驱结点是 ="  + leftNode); //3
        System.out.println("10号结点的后继结点是="  + rightNode); //1
        System.out.println("使用线索化的方式中序遍历 线索化二叉树");
        threadedBinaryTree.threadedList(); // 8, 3, 10, 1, 14, 6
    }


}

/**
 * 构建线索化二叉树
 */
class ThreadedBinaryTree {
    public HeroNode1 root;
    //当前被线索化节点的前一个节点
    public HeroNode1 preNode;

    public ThreadedBinaryTree(HeroNode1 root) {
        this.root = root;
    }

    /**
     *对二叉树进行中序线索化的方法
     * @param node 就是当前需要线索化的结点
     */
    public void threadedNodes(HeroNode1 node) {
        if (node == null) {
            return;
        }
        //线索化左子树
        threadedNodes(node.left);
        //线索化当前节点的前驱，通过当前节点node指向preNode来构建一个当前节点的前驱
        if (node.left == null) {
            node.leftType = 1;
            node.left = preNode;
        }
        //线索化当前节点的后继，要构建后继节点需要当前节点node1再遍历移动一次到node2。
        // 这样当前节点node2就是node1的后继节点，而node1也就是node2的前驱节点，也就是现在的preNode就是node1节点。
        //只有当前被遍历节点的前一个节点才能设置后继，所以当前节点的后继节点设置需要遍历到下一个节点时使用preNode来设置
        if (preNode != null && preNode.right == null) {
            preNode.rightType = 1;
            preNode.right = node;
        }
        //!!! 每处理一个结点后，让当前结点是下一个结点的前驱结点。只有前一个节点才能设置后继节点
        preNode = node;
        //线索化右子树
        threadedNodes(node.right);
    }

    public void threadedNodes() {
        this.threadedNodes(root);
    }

    //中序遍历线索化二叉树
    public void threadedList(HeroNode1 node) {
        while (node != null) {
            //node从root 开始，一直往左找，只要node.getLeftType() == 1，表示这个结点一定是最左边的结点，即第一个要遍历的结点
            while (node.leftType == 0) {
                node = node.left;
            }
            //找到了就打印出来
            System.out.println(node);
            //只要当前被遍历的结点有后驱结点，就一直把这个当前结点的后驱结点遍历打印出来
            while (node.rightType == 1) {
                node = node.right;
                System.out.println(node);
            }
            //对于中序遍历，下一个节点必定是right
            node = node.right;
        }
    }

    public void threadedList() {
        threadedList(root);
    }
}


class HeroNode1 {
    public int no;
    public String name;
    public HeroNode1 left;
    public HeroNode1 right;
    //1. 如果leftType == 0 表示指向的是左子树, 如果 1 则表示指向前驱结点
    //2. 如果rightType == 0 表示指向是右子树, 如果 1表示指向后继结点
    public int leftType;
    public int rightType;

    public HeroNode1(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public HeroNode1 getLeft() {
        return left;
    }
    public void setLeft(HeroNode1 left) {
        this.left = left;
    }
    public HeroNode1 getRight() {
        return right;
    }
    public void setRight(HeroNode1 right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + "]";
    }
}


