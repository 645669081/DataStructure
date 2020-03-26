package com.atguigu.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 插值查找
 * 插值查找其实是对二分法的改进，能够找出一个更好的mid值来迅速的接近要查找数据。公式的推倒具体看数学中数值分析中的差值
 * 具体公式是：mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
 * 对于分布均匀的数组支持较好，对极端分布的不如二分查找。如arr={0,1,2,100,102,1000,10000}
 *
 *
 * 原来二分查找mid选取公式演变
 * 1. low/2 + high/2
 * 2. low + (high/2 - low/2) 	损失了2个low/2所以加上low
 * 3. low + 1/2 * (high - low)
 *
 * 插值公式，这里的key是查找值
 * 1. 公式：low + ((key - arr[low]) / (arr[high] - arr[low])) * (high - low)
 * 2. 个人理解
 *            1.原来是1/2的(high - low)，现在是((key - arr[low]) / (arr[high] - arr[low]))的(high - low)
 *            2.key在整个查找区间high-low上所处的距离。当距离low较远时，说明查找的是较大值，那么此时key-arr[low]与high-low的差距较小，
 *              相除后得到的是接近1的数，再乘以整个区间high-low就得到了靠后的索引位置，从而比二分查找能够更快的靠近查找值。
 *            3.相反如果key是一个小值，那么key-arr[low]是一个小值，再去除以(arr[high] - arr[low])还是一个小值，乘以(high - low)就是一个靠近low位置的值。
 *            4.与原先对半不同的是，使用((key - arr[low]) / (arr[high] - arr[low]))来代替原来的1/2，原来是整个查找长度的一半。
 *              现在是先去根据查找值与arr[low]的差占整个查找范围内最大值和最小值差的的比例来决定。
 *              可以把这里的差值理解成距离，差值的比例也就是索引的比例，乘以总的查找长度就可以获取到更加接近查找值的索引
 */
public class InsertValueSearch {

    private static int count = 1;

    public static void main(String[] args) {
        //二分插值与插值查找查找次数比较
//        int [] arr = new int[100];
//		for(int i = 0; i < 100; i++) {
//			arr[i] = i + 1;
//		}
        int arr[] = { 1, 8, 10, 89,1000,1000, 1234 };
        List<Integer> list = BinarySearch.binarySearch(arr, 0, arr.length - 1, 1000);
        System.out.println(list);
        List<Integer> indexList = insertValueSearch(arr, 0, arr.length - 1, 1000);
        System.out.println(indexList);
    }


    /**
     *插值查找算法，要求数组是有序的
     * @param arr 数组
     * @param left 左边索引
     * @param right 右边索引
     * @param findVal 查找值
     * @return 如果找到，就返回对应的下标，如果没有找到，返回-1
     */
    public static List<Integer> insertValueSearch(int[] arr, int left, int right, int findVal) {
        //保证指针相遇，查找值大小在范围内
        if (left > right || findVal < arr[left] || findVal > arr[right]) {
            return new ArrayList<>();
        }
        int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
        System.out.println("插值查找第" + count++ + "次");
        if (findVal > arr[mid]) {
            //向右递归
            return insertValueSearch(arr, mid + 1, right, findVal);
        } else if (findVal < arr[mid]) {
            //向左递归
            return insertValueSearch(arr, left, mid - 1, findVal);
        } else {
            //找左右两侧找相同的值,查找的区间是整个数组，不再试left和right之间
            List<Integer> indexList = new ArrayList<>();
            //向左查找
            int index = mid - 1;
            while (index >= 0 && arr[index] == findVal) {
                indexList.add(index);
                index--;
            }
            indexList.add(mid);
            //向右查找
            index = mid + 1;
            while (index <= arr.length -1 && arr[index] == findVal) {
                indexList.add(index);
                index++;
            }
            return indexList;
        }
    }
}
