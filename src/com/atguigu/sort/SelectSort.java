package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * author:w_liangwei
 * date:2020/3/24
 * Description:选择排序
 * 优化点：在遍历完成后得到的最小值的位置就是最开始假设的位置时就无需交换
 * 同冒泡排序的比较：
 *                  同样是n^2但是根据测试比冒泡快
 *                  选择排序每一趟只进行一次交换，冒泡一趟要进行多次交换
 */
public class SelectSort {
    public static void main(String[] args) {
//        int [] arr = {101, 34, 119, 1, -1, 90, 123};
        //创建要给80000个的随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }
//        System.out.println("排序前：" + Arrays.toString(arr));

        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        selectSort(arr);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);
    }

    //选择排序
    public static void selectSort(int[] arr) {
        //归纳后
        //每一趟获取一个最小值，比较趟数等于数组长度-1
        for (int i = 0; i < arr.length -1; i++) {
            //假设最小值是在未排序数组的第一位，分别记录最小值和索引供交换位置时使用。随着每跑一趟会出来一个最小值，未排序数组长度也会变化
            int minIndex = i;
            int min = arr[i];
            //由于自己和自己不用比较，而每一趟会使未排序数组减少一个，所以要比较的下一个数字的索引就是i+1
            for (int j = i + 1; j < arr.length; j++) {
                //如果发现当前遍历的数字比当前最小值小，那么更新最小值和最小值索引
                if (arr[j] < min) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            //遍历比较完成后，如果此时最小值已经不是原来假定的值，那么交换最小值位置
            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            } else {
//                System.out.println("第"+ (i+1) +"次排序未发生数据交换");
            }
//            System.out.println("第"+ (i+1) +"次排序后：" + Arrays.toString(arr));
        }








        /*
        int[] arr1 = {101, 34, 119, 1};
        System.out.println("排序前：" + Arrays.toString(arr1));
        //第一趟排序
        //假设最小值是在未排序数组的第一位，分别记录最小值和索引供交换位置时使用
        int minIndex = 0;
        int min = arr1[0];
        //由于自己和自己不用比较，所以j+1索引后移到下一个数字
        for (int j = 0 + 1; j < arr1.length; j++) {
            //如果发现当前遍历的数字比当前最小值小，那么更新最小值和最小值索引
            if (arr1[j] < min) {
                min = arr1[j];
                minIndex = j;
            }
        }
        //遍历比较完成后，如果此时最小值已经不是原来假定的值，那么交换最小值位置
        if (minIndex != 0) {
            arr1[minIndex] = arr1[0];
            arr1[0] = min;
        }
        System.out.println("第一次排序后：" + Arrays.toString(arr1));


        //第二趟排序
        //假设最小值是在未排序数组的第一位，分别记录最小值和索引供交换位置时使用
        minIndex = 1;
        min = arr1[1];
        //由于自己和自己不用比较，所以j+1索引后移到下一个数字
        for (int j = 1 + 1; j < arr1.length; j++) {
            //如果发现当前遍历的数字比当前最小值小，那么更新最小值和最小值索引
            if (arr1[j] < min) {
                min = arr1[j];
                minIndex = j;
            }
        }
        //遍历比较完成后，如果此时最小值已经不是原来假定的值，那么交换最小值位置
        if (minIndex != 1) {
            arr1[minIndex] = arr1[1];
            arr1[1] = min;
        }
        System.out.println("第二次排序后：" + Arrays.toString(arr1));


        //第三趟排序
        //假设最小值是在未排序数组的第一位，分别记录最小值和索引供交换位置时使用
        minIndex = 2;
        min = arr1[2];
        //由于自己和自己不用比较，所以j+1索引后移到下一个数字
        for (int j = 2 + 1; j < arr1.length; j++) {
            //如果发现当前遍历的数字比当前最小值小，那么更新最小值和最小值索引
            if (arr1[j] < min) {
                min = arr1[j];
                minIndex = j;
            }
        }
        //遍历比较完成后，如果此时最小值已经不是原来假定的值，那么交换最小值位置
        if (minIndex != 2) {
            arr1[minIndex] = arr1[2];
            arr1[2] = min;
        }
        System.out.println("第三次排序后：" + Arrays.toString(arr1));*/
    }
}
