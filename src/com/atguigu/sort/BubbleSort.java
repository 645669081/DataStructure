package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 冒泡排序
 */
public class BubbleSort {
    public static void main(String[] args) {
//        int arr[] = {20, 10, 9, 3, -1};
//        int arr[] = {3, 9, -1, 10, 20};
//        int[] arr = {1, 2, 3, 4, 5, 6};

        //测试一下冒泡排序的速度O(n^2), 给80000个数据，测试
        //创建要给80000个的随机的数组
        int[] arr = new int[80000];
        for(int i =0; i < 80000;i++) {
            arr[i] = (int)(Math.random() * 8000000); //生成一个[0, 8000000) 数
        }

        //数据太多打印浪费时间
//		System.out.println("排序前");
//		System.out.println(Arrays.toString(arr));

        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

		bubbleSort(arr);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序后的时间是=" + date2Str);






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

    // 将前面额冒泡排序算法，封装成一个方法
    public static void bubbleSort(int[] arr) {
        //归纳后
        int temp;
        //跑的趟数=数组个数-1，最后一个数字不用跑就可以确定。
        for (int i = 0; i < arr.length -1; i++) {
            //每一趟比较时都要排除自己和已经排好序的数字，所以是比较次数是arr.length -1 -已经跑的趟数
            //设置一个标识，如果该趟没有调换位置则该数组已经有顺序，无需跑剩余的趟数来确定后边的数字
            boolean flag = false; //如果为true表示发生过交换，false表示没有发生交换
            for (int j = 0; j < arr.length -1 - i; j++) {
                if (arr[j] > arr[j+1]) {
                    //发生了数字的调换则改变标识
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            //数量量大时打印浪费时间
//            System.out.println("第"+ (i+1) +"趟排序结果：" + Arrays.toString(arr));
            //判断该趟是否发生数字顺序调换
            if (!flag) {
                break;
            }
        }
    }
}
