package com.atguigu.tree;

/**
 * author:w_liangwei
 * date:2020/3/27
 * Description: 使用数组实现顺序存储二叉树
 * 第n个元素的左子节点为2n+1
 * 第n个元素的右子节点为2n+2
 * 第n个元素的父节点为(n-1)/2
 * n表示二叉树中的第几个元素。从上往下，从左往右，由大到小的顺序
 */
public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
//        arrBinaryTree.preOrder();// 1,2,4,5,3,6,7
//        arrBinaryTree.infixOrder();// 4,2,5,1,6,3,7
        arrBinaryTree.postOrder();// 4,5,2,6,7,3,1
    }
}

//编写一个ArrayBinaryTree, 实现顺序存储二叉树遍历
class ArrBinaryTree {
    private int[] arr;

    //传入一个数组初始化
    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    /**
     * 前序遍历
     * @param index 数组的下标
     */
    public void preOrder(int index) {
        if (arr == null || arr.length == 0) {
            return;
        }
        System.out.println(arr[index]);
        //左子节点存在
        if (2 * index + 1 < arr.length) {
            //递归时传递的是节点在数组中的索引，不是节点的值
            preOrder(2 * index + 1);
        }
        //右子节点存在
        if (2 * index + 2 < arr.length) {
            preOrder(2 * index + 2);
        }
    }

    public void preOrder() {
        this.preOrder(0);
    }

    /**
     * 中序遍历
     * @param index 数组下标
     */
    public void infixOrder(int index) {
        if (arr == null || arr.length == 0) {
            return;
        }
        if (2 * index + 1 < arr.length) {
            infixOrder(2 * index + 1);
        }
        System.out.println(arr[index]);
        if (2 * index + 2 < arr.length) {
            infixOrder(2 * index + 2);
        }
    }

    public void infixOrder() {
        this.infixOrder(0);
    }


    /**
     * 后序遍历
     * @param index 数组下标
     */
    public void postOrder(int index) {
        if (arr == null || arr.length == 0) {
            return;
        }
        if (2 * index + 1 < arr.length) {
            postOrder(2 * index + 1);
        }
        if (2 * index + 2 < arr.length) {
            postOrder(2 * index + 2);
        }
        System.out.println(arr[index]);
    }

    public void postOrder() {
        this.postOrder(0);
    }
}
