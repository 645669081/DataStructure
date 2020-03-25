package com.atguigu.sort;

import java.util.Arrays;

/**
 * author:w_liangwei
 * date:2020/3/25
 * Description: 基数排序
 */
public class RadixSort {
    public static void main(String[] args) {
        int arr[] = { 53, 3, 542, 748, 14, 214};

        radixSort(arr);
    }

    //基数排序方法
    public static void radixSort(int[] arr) {

        //第一轮对个位进行排序
        //创建10个桶,每个的大小由于不知道会被放入多少元素所以无法确定，只能创建最大的和待排序数组一样的长度
        int[][] bucket = new int[10][arr.length];
        //每个桶都需要一个指针记录放入了多少元素,这里采用数组来保存桶内元素指针。同一个桶在二维数组中的下标=在一维数组中的下标。这样可以使用下标来获取元素个数指针
        int[] bucketCount = new int[10];

        //获取待排序数组每一个元素的个位
        for (int i = 0; i < arr.length; i++) {
            int digitOfElement = arr[i] % 10;
            //数字是几就放第几个桶,使用桶下标获取桶内已放入元素指针位置
            bucket[digitOfElement][bucketCount[digitOfElement]] = digitOfElement;
            //桶内元素指针后移
            bucketCount[digitOfElement]++;
        }
        //使用桶内元素顺序替换原数组顺序
        System.out.println("第一轮基数排序后："+ Arrays.toString(arr));
    }
}
