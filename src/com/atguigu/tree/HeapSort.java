package com.atguigu.tree;

import java.util.Arrays;

/**
 * @Date 2020/3/30 6:12
 * @Auther 梁伟
 * @Description
 * 堆是父节点大于左右子节点，而左右节点间的大小不要求
 * 对一个不满足堆要求的树进行堆化即可形成一个堆
 */
public class HeapSort {
    public static void main(String[] args) {
//        int[] tree = {4, 10, 3, 5, 1, 2};
//        int[] tree = {2, 5, 3, 1, 10, 4};
        int[] tree = {4, 6, 8, 5, 9, -1, 90, 89, 56, -99};
//        heapify(tree, tree.length, 0);
//        heapify(tree, tree.length, 0);
//        heapify(tree, tree.length, 2);

//        buildHeap(tree, tree.length);
        heapSort(tree, tree.length);
        System.out.println(Arrays.toString(tree));
    }

    public static void swap(int[] tree, int i, int max) {
        int temp = tree[i];
        tree[i] = tree[max];
        tree[max] = temp;
    }

    /**
     * @auther 梁伟
     * @Description 对当前传入的节点i进行堆化，找到其在堆中的正确位置。要想完成整个树的堆化需多次执行
     * @Date 2020/3/31 6:24
     * @param tree 当前树存储的数组
     * @param length 数组要堆化的范围，从0到length
     * @param i 当前要堆化的子树的父节点
     * @return void
     **/
    public static void heapify(int[] tree, int length, int i) {
        //递归出口，当前节点最大值索引就是数组索引length-1
        if (i >= length) {
            return;
        }
        //初始化节点i的左右节点索引
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        //假设当前节点i就是最大值
        int max = i;
        //分别用父节点i和左右节点进行比较得到最大值的索引
        if (left < length && tree[left] > tree[max]) {
            max = left;
        }
        if (right < length && tree[right] > tree[max]) {
            max = right;
        }
        //比较过后最大值不是父节点i才进行交换
        if (max != i) {
            swap(tree, i, max);
            //由于父节点和左右节点交换过后会造成下边的子树结构被破坏，需要对下一层节点进行堆化。递归直至完成整个树的堆化
            //此时的tree[max]节点是交换过后的值，以max为父节点继续进行堆化
            //只有交换了的才会对下边的节点产生影响,所以放在if内执行该语句
            heapify(tree, length, max);
        }
    }

    /**
     * @auther 梁伟
     * @Description 对任意树调整成堆的形式
     * @Date 2020/3/31 6:52
     * @Param [tree, length] 树所存储的数组，数组长度
     * @return void
     **/
    public static void buildHeap(int[] tree, int length) {
        //获取最后一个非叶子节点,tree的最后一个元素的父节点就是该节点
        int lastNode = length - 1;
        int parent = (lastNode - 1) / 2;
        //做堆化时从下到上，从右往左。正好符合数组索引递减的趋势
        //从最后一个非叶子节点parent开始一路递减
        for (int i = parent; i >= 0; i--) {
            heapify(tree, length, i);
        }
    }

    /**
     * @auther 梁伟
     * @Description
     * @Date 2020/3/31 7:11
     * @Param [tree, length] 树所存储的数组, 要排序的数组长度
     * @return void
     **/
    public static void heapSort(int[] tree, int length) {
        //生成堆结构
        buildHeap(tree, length);
        //将树中最后一个节点和根节点交换，此时最大值到达了最后一个位置，将最后一个位置砍断得到最大值。
        // 然后对交换后的根节点进行堆化得到该节点的正确位置。此时又是一个堆了，重复以上操作直到全部元素完成
        for (int i = length -1; i >= 0; i--) {
            //树的最后一个位置与根节点交换,每次都是第i个的递减，相当于每次砍掉一个当前剩余子树的最大值
            //此时最大值就在数组的末尾，每次扔出一个最大值到数组后端
            swap(tree, i, 0);
            //要堆化的是交换过后的根节点，此时未排序节点数量只剩下i
            heapify(tree, i, 0);
        }
    }
}
