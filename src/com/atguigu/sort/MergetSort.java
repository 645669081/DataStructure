package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * author:w_liangwei
 * date:2020/3/25
 * Description: 归并排序
 * 1.将数组的所有数据递归进行分割，最终数组内只有一个元素
 * 2.然后合并排序到一个新的数组
 * 3.将新的数组的元素拷贝到原数组
 * 4.多次归并后完成原数组排序，进行的归并次数是length-1次的线性阶
 */
public class MergetSort {

    private static int count = 1;

    public static void main(String[] args) {
//        int arr[] = { 8, 4, 5, 7, 1, 3, 6, 2 };
//        int[] arr = {8, 4, 5, 7};

        // 创建要给80000个的随机的数组
        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }
        System.out.println("排序前");
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        mergeSort(arr, 0, arr.length - 1, new int[arr.length]);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序后的时间是=" + date2Str);
    }
    /**
     * 递归分解数组,分解完成后再合并
     * 分+合方法
     */
    public static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        //递归左半边
        mergeSort(arr, left, mid, temp);
        //递归有半边
        mergeSort(arr, mid + 1, right, temp);
        //合并排序
        merge(arr, left, mid, right, temp);
    }


    /**
     * 合并的方法
     * @param arr 排序的原始数组
     * @param left 左边有序序列的初始索引
     * @param mid 中间索引
     * @param right 右边索引
     * @param temp 做中转的数组
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        //为两个数组初始化遍历比较时使用的指针，相当于将排序区间的数组分成两半
        int i = left; //左半部分数组指针起始位置
        int j = mid + 1;  //右半部分数组指针起始位置
        int index = 0; //数组

//        System.out.println("归并前：" + Arrays.toString(arr) + "，归并的索引区间是："+ left + "-" + right);

        //左半部分数组left到mid，右半部分数组mid+1到right
        //将数组元素分为左右两部分归并排序到新的数组，直到其中一个数组已经排序完成即指针移动到了自己所处的那半部分数组的结尾
        while (i <= mid && j <= right) {
            //比较指针位置的两个数，确定哪个放到新数组。当取出比较的两个数相等的时候，把谁放入新数组都可以
            if (arr[i] >= arr[j]) {
                temp[index] = arr[j];
                j++;
            } else {
                temp[index] = arr[i];
                i++;
            }
            //新数组指向下一个待插入元素位置
            index++;
        }

        //将另外一个有剩余元素的数组全部移动到新数组
        while (i <= mid) {
            temp[index] = arr[i];
            index++;
            i++;
        }
        while (j <= right) {
            temp[index] = arr[j];
            index++;
            j++;
        }

        //将新数组中排好序的元素拷贝到原数组来替换left到right之间要排序的部分元素。不能遍历数组，因为新数组中有上几次归并残留数据，只能拷贝自己本次放进去的那些
        index = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[index];
            tempLeft++;
            index++;
        }
//        System.out.println("第"+ count++ +"次归并后：" + Arrays.toString(arr));
    }
}
