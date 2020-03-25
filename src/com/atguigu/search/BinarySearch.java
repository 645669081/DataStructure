package com.atguigu.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 二分查找
 * 要求数组有序
 */
public class BinarySearch {
    public static void main(String[] args) {
        int arr[] = { 1, 8, 10, 89, 1000, 1000, 1234};

        List<Integer> indexList = binarySearch(arr, 0, arr.length - 1, 1000);
        System.out.println(indexList);
    }

    /**
     *二分查找算法，支持多个相同数值
     * @param arr 数组
     * @param left 左边的索引
     * @param right 右边的索引
     * @param findVal 要查找的值
     * @return 如果找到就返回下标集合，如果没有找到，就返回空集合
     */
    public static List<Integer> binarySearch(int[] arr, int left, int right, int findVal) {
        //当left=right都未找到的时候，说明数组查找完毕也没有。下一次查找必然发生指针穿过的情况
        if (left > right) {
            return new ArrayList<>();
        }
        int mid = (left + right) / 2;
        //去和arr[mid]进行比较来决定向哪边递归查找
        if (findVal > arr[mid]) {
            //向右递归查找
            return binarySearch(arr, mid + 1, right, findVal);
        } else if (findVal < arr[mid]) {
            //向左递归查找
            return binarySearch(arr, left, mid-1, findVal);
        } else {
            //相等的情况下需要找出该索引附近与查找值相同的值
            List<Integer> indexList = new ArrayList<>();
            int findEqualValIndex = mid - 1;
            //向左查找相同值
            while (findEqualValIndex >= 0 && arr[findEqualValIndex] == findVal) {
                indexList.add(findEqualValIndex);
                findEqualValIndex--;
            }
            //将第一次查找到的值索引加入到集合
            indexList.add(mid);
            //向右查找相同值
            findEqualValIndex = mid + 1;
            while (findEqualValIndex <= right && arr[findEqualValIndex] == findVal) {
                indexList.add(findEqualValIndex);
                findEqualValIndex++;
            }
            return indexList;
        }
    }
}

