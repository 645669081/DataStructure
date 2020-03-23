package com.atguigu.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort {
    public static void main(String[] args) {
        int arr[] = {20, 10, 9, 3, -1};

		System.out.println("排序前");
		System.out.println(Arrays.toString(arr));

        //归纳后
        int temp;
        //跑的趟数=数组个数-1，最后一个数字不用跑就可以确定。
        for (int i = 0; i < arr.length -1; i++) {
            //第一趟排序
            //每一趟比较时都要排除自己和已经排好序的数字，所以是比较次数是arr.length -1 -已经跑的趟数
            for (int j = 0; j < arr.length -1 - i; j++) {
                if (arr[j] > arr[j+1]) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            System.out.println("第"+ (i+1) +"趟排序结果：" + Arrays.toString(arr));
        }







		//第一趟排序
//        int temp;
        //每一个数字都不用和自己比较，所以是比较次数是arr.length -1
//        for (int i = 0; i < arr.length -1 -0; i++) {
//            if (arr[i] > arr[i+1]) {
//                temp = arr[i];
//                arr[i] = arr[i+1];
//                arr[i+1] = temp;
//            }
//        }
//        System.out.println("第一趟排序结果：" + Arrays.toString(arr));

        //第二趟排序
        //每一个数字都不用和自己比较，所以是比较次数是arr.length -1
//        for (int i = 0; i < arr.length -1 -1; i++) {
//            if (arr[i] > arr[i+1]) {
//                temp = arr[i];
//                arr[i] = arr[i+1];
//                arr[i+1] = temp;
//            }
//        }
//        System.out.println("第二趟排序结果：" + Arrays.toString(arr));

        //第三趟排序
//        for (int i = 0; i < arr.length -1 -2; i++) {
//            if (arr[i] > arr[i+1]) {
//                temp = arr[i];
//                arr[i] = arr[i+1];
//                arr[i+1] = temp;
//            }
//        }
//        System.out.println("第三趟排序结果：" + Arrays.toString(arr));

        //第四趟排序
//        for (int i = 0; i < arr.length -1 -3; i++) {
//            if (arr[i] > arr[i+1]) {
//                temp = arr[i];
//                arr[i] = arr[i+1];
//                arr[i+1] = temp;
//            }
//        }
//        System.out.println("第四趟排序结果：" + Arrays.toString(arr));
    }
}
