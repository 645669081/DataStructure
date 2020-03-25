package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * author:w_liangwei
 * date:2020/3/25
 * Description: 基数排序
 * 1.将数组中的每一位都通过前端补0的方式与数组中的最大数保持位数一致
 * 2.从个位开始对每一位进行桶排序
 * 3.每一次将桶排序结果写回原数组
 * 4.直到最高位完成桶排则排序完成
 * 由于创建桶时不知道桶中会放入多少元素，所以只能按照数组长度来创建桶。基数排序会消耗大量的内存用来创建桶，属于空间换时间
 *
 */
public class RadixSort {
    public static void main(String[] args) {
//        int arr[] = { 53, 3, 542, 748, 14, 214};
//        System.out.println("排序前：" + Arrays.toString(arr));

        //测试排序时间
        int[] arr = new int[8000000];
		for (int i = 0; i < 8000000; i++) {
			arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
		}

        System.out.println("排序前");
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        radixSort(arr);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序后的时间是=" + date2Str);
    }

    //基数排序方法
    public static void radixSort(int[] arr) {
        //归纳后
        //创建10个桶,每个的大小由于不知道会被放入多少元素所以无法确定，只能创建最大的和待排序数组一样的长度
        int[][] bucket = new int[10][arr.length];
        //每个桶都需要一个指针记录放入了多少元素,这里采用数组来保存桶内元素指针。同一个桶在二维数组中的下标=在一维数组中的下标。这样可以使用下标来获取元素个数指针
        int[] bucketCount = new int[10];

        //获取最大的数字确定要桶排的次数
        int max = -1;
        for (int item : arr) {
            if (max < item) {
                max = item;
            }
        }
        //获取最大值的位数
        int maxLength = (max + "").length();
        //通过循环控制桶排次数，初始化n用来获取当前被排序的位数
        for (int k = 0, n = 1; k < maxLength; k++, n *= 10) {
            //获取待排序数组每一个元素的个位
            for (int i = 0; i < arr.length; i++) {
                int digitOfElement = arr[i] / n % 10;
                //数字是几就放第几个桶,使用桶下标获取桶内已放入元素指针位置
                bucket[digitOfElement][bucketCount[digitOfElement]] = arr[i];
                //桶内元素指针后移
                bucketCount[digitOfElement]++;
            }
            //使用桶内元素顺序替换原数组顺序
            int index = 0;
            for (int i = 0; i < bucket.length; i++) {
                //判断该桶内是否有元素,不为空则取出元素放入原数组
                if (bucketCount[i] != 0) {
                    //由于桶中可能有上一次元素的残留未被覆盖，所以不能使用桶内元素长度来遍历
                    for (int j = 0; j < bucketCount[i]; j++) {
                        arr[index] = bucket[i][j];
                        index++;
                    }
                }
                //经过上一个循环，桶内元素被全部取出。此时需要将桶内元素指针bucketCount[i]置为0
                bucketCount[i] = 0;
            }
//            System.out.println("第"+ (k + 1) +"轮基数排序后："+ Arrays.toString(arr));
        }


        /*
        //第一轮对个位进行排序
        //创建10个桶,每个的大小由于不知道会被放入多少元素所以无法确定，只能创建最大的和待排序数组一样的长度
        int[][] bucket = new int[10][arr.length];
        //每个桶都需要一个指针记录放入了多少元素,这里采用数组来保存桶内元素指针。同一个桶在二维数组中的下标=在一维数组中的下标。这样可以使用下标来获取元素个数指针
        int[] bucketCount = new int[10];

        //获取待排序数组每一个元素的个位
        for (int i = 0; i < arr.length; i++) {
            int digitOfElement = arr[i] % 10;
            //数字是几就放第几个桶,使用桶下标获取桶内已放入元素指针位置
            bucket[digitOfElement][bucketCount[digitOfElement]] = arr[i];
            //桶内元素指针后移
            bucketCount[digitOfElement]++;
        }
        //使用桶内元素顺序替换原数组顺序
        int index = 0;
        for (int i = 0; i < bucket.length; i++) {
            //判断该桶内是否有元素,不为空则取出元素放入原数组
            if (bucketCount[i] != 0) {
                //由于桶中可能有上一次元素的残留未被覆盖，所以不能使用桶内元素长度来遍历
                for (int j = 0; j < bucketCount[i]; j++) {
                    arr[index] = bucket[i][j];
                    index++;
                }
            }
            //经过上一个循环，桶内元素被全部取出。此时需要将桶内元素指针bucketCount[i]置为0
            bucketCount[i] = 0;
        }
        System.out.println("第一轮基数排序后："+ Arrays.toString(arr));


        //第二轮对十位进行排序，桶和桶指针使用第一轮初始化好的
        //获取待排序数组每一个元素的十位
        for (int i = 0; i < arr.length; i++) {
            int digitOfElement = arr[i] / 10 % 10;
            //数字是几就放第几个桶,使用桶下标获取桶内已放入元素指针位置
            bucket[digitOfElement][bucketCount[digitOfElement]] = arr[i];
            //桶内元素指针后移
            bucketCount[digitOfElement]++;
        }
        //使用桶内元素顺序替换原数组顺序
        index = 0;
        for (int i = 0; i < bucket.length; i++) {
            //判断该桶内是否有元素,不为空则取出元素放入原数组
            if (bucketCount[i] != 0) {
                //由于桶中可能有上一次元素的残留未被覆盖，所以不能使用桶内元素长度来遍历
                for (int j = 0; j < bucketCount[i]; j++) {
                    arr[index] = bucket[i][j];
                    index++;
                }
            }
            //经过上一个循环，桶内元素被全部取出。此时需要将桶内元素指针bucketCount[i]置为0
            bucketCount[i] = 0;
        }
        System.out.println("第二轮基数排序后："+ Arrays.toString(arr));


        //第三轮对十位进行排序，桶和桶指针使用第一轮初始化好的
        //获取待排序数组每一个元素的百位
        for (int i = 0; i < arr.length; i++) {
            int digitOfElement = arr[i] / 10 / 10 % 10;
            //数字是几就放第几个桶,使用桶下标获取桶内已放入元素指针位置
            bucket[digitOfElement][bucketCount[digitOfElement]] = arr[i];
            //桶内元素指针后移
            bucketCount[digitOfElement]++;
        }
        //使用桶内元素顺序替换原数组顺序
        index = 0;
        for (int i = 0; i < bucket.length; i++) {
            //判断该桶内是否有元素,不为空则取出元素放入原数组
            if (bucketCount[i] != 0) {
                //由于桶中可能有上一次元素的残留未被覆盖，所以不能使用桶内元素长度来遍历
                for (int j = 0; j < bucketCount[i]; j++) {
                    arr[index] = bucket[i][j];
                    index++;
                }
            }
            //经过上一个循环，桶内元素被全部取出。此时需要将桶内元素指针bucketCount[i]置为0
            bucketCount[i] = 0;
        }
        System.out.println("第三轮基数排序后："+ Arrays.toString(arr));*/
    }
}
