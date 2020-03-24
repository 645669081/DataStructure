package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * author:w_liangwei
 * date:2020/3/24
 * Description:希尔排序
 * 希尔排序是对插入排序的插入间隔由原来的1增大，它的间隔变化是由最初的一个大值逐渐下降到1最终完成排序。减少了大量的位置交换
 * 缩小增量排序，增量即组间元素间隔
 * 希尔排序在时对分组内插入时采用交换法（类似于冒泡排序）和移动法（类似于插入排序）。整体还是移动法明显调整数字位置的次数少，只有在位置最终确定了才会交换，不然只是后移较大的元素
 */
public class ShellSort {
    public static void main(String[] args) {
//        int[] arr = { 8, 9, 1, 7, 2, 3, 5, 4, 6, 0 };
//        System.out.println("排序前：" + Arrays.toString(arr));

        // 创建要给80000个的随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        System.out.println("排序前");
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        shellSort(arr);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);
    }

    /**
     * 使用逐步推导的方式来编写希尔排序
     * 希尔排序时， 对有序序列在插入时采用移位法
     * @param arr
     */
    public static void shellSort(int[] arr) {
        //归纳后
        //分组每次都是前一次的一半，直至无法分割
        int count = 0;
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            count++;
            //当分为gap组时，将i = gap开始从第gap个数来匹配进行分组，前边gap个数看成有gap个分组，每个分组中只有一个元素。从arr[gap]开始到数组结尾都是要加入分组数组的元素
            //这个循环遍历到的是组外的待插入元素
            for (int i = gap; i < arr.length; i++) {
                //保存待插入组内元素值，防止后移组内元素时被覆盖
                int insertVal = arr[i];
                int insertIndex = i;
                //进行组内排序,此时的arr[i]是组外的第一个被遍历到的值，对该值进行组内的插入排序。此时同一组就看成一个小数组插入排序即可
                // 将待插入组内的元素从arr[i-同一组元素间间隔（与组数相同）]组内的最后一个元素进行倒序比较
                for (int j = i-gap; j >= 0; j -= gap) {
                    //待插入元素比组内元素小则将组内元素后移
                    if (insertVal < arr[j]) {
                        //将组内元素后移
                        arr[j + gap] = arr[j];
                        insertIndex = j;
                    }
                }
                //待插入元素获取插入位置完成后进行插入
                if (insertIndex != i) {
                    arr[insertIndex] = insertVal;
                }
            }
//            System.out.println("希尔排序"+ count +"轮后=" + Arrays.toString(arr));
        }


        /*
        //第一轮
        //当第一次分为10/2=5组，将i = 5开始从第6个数来匹配进行分组，前边5个数看成有5个分组，每个分组中只有一个元素
        //这个循环遍历到的是组外的待插入元素
        for (int i = 5; i < arr.length; i++) {
            //保存待插入组内元素值，防止后移组内元素时被覆盖
            int insertVal = arr[i];
            int insertIndex = i;
            //进行组内排序,此时的arr[i]是组外的第一个被遍历到的值，对该值进行组内的插入排序。此时同一组就看成一个小数组插入排序即可
            // 将待插入组内的元素从arr[i-同一组元素间间隔（与组数相同）]组内的最后一个元素进行倒序比较
            for (int j = i-5; j >= 0; j -= 5) {
                //待插入元素比组内元素小则将组内元素后移
                if (insertVal < arr[j]) {
                    //将组内元素后移
                    arr[j+5] = arr[j];
                    insertIndex = j;
                }
            }
            //待插入元素获取插入位置完成后进行插入
            if (insertIndex != i) {
                arr[insertIndex] = insertVal;
            }
        }
        System.out.println("希尔排序1轮后=" + Arrays.toString(arr));


        //第二轮
        //当第一次分为5/2=2组，将i = 2开始从第6个数来匹配进行分组，前边2个数看成有2个分组，每个分组中只有一个元素
        //这个循环遍历到的是组外的待插入元素
        for (int i = 2; i < arr.length; i++) {
            //保存待插入组内元素值，防止后移组内元素时被覆盖
            int insertVal = arr[i];
            int insertIndex = i;
            //进行组内排序,此时的arr[i]是组外的第一个被遍历到的值，对该值进行组内的插入排序。此时同一组就看成一个小数组插入排序即可
            // 将待插入组内的元素从arr[i-同一组元素间间隔（与组数相同）]组内的最后一个元素进行倒序比较
            for (int j = i-2; j >= 0; j -= 2) {
                //待插入元素比组内元素小则将组内元素后移，为了房子元素覆盖此处不要用arr[i]，要用insertVal最初记录的插入值
                if (insertVal < arr[j]) {
                    //将组内元素后移。此处也可以不记录插入位置直接使用临时变量交换位置，只是交换的无用次数会增多
                    arr[j+2] = arr[j];
                    insertIndex = j;
                }
            }
            //待插入元素获取插入位置完成后进行插入
            if (insertIndex != i) {
                arr[insertIndex] = insertVal;
            }
        }
        System.out.println("希尔排序2轮后=" + Arrays.toString(arr));


        //第三轮
        //当第一次分为2/2=1组，将i = 1开始从第6个数来匹配进行分组，前边1个数看成有1个分组，每个分组中只有一个元素
        //这个循环遍历到的是组外的待插入元素
        for (int i = 1; i < arr.length; i++) {
            //保存待插入组内元素值，防止后移组内元素时被覆盖
            int insertVal = arr[i];
            int insertIndex = i;
            //进行组内排序,此时的arr[i]是组外的第一个被遍历到的值，对该值进行组内的插入排序。此时同一组就看成一个小数组插入排序即可
            // 将待插入组内的元素从arr[i-同一组元素间间隔（与组数相同）]组内的最后一个元素进行倒序比较
            for (int j = i-1; j >= 0; j -= 1) {
                //待插入元素比组内元素小则将组内元素后移，为了房子元素覆盖此处不要用arr[i]，要用insertVal最初记录的插入值
                if (insertVal < arr[j]) {
                    //将组内元素后移。此处也可以不记录插入位置直接使用临时变量交换位置，只是交换的无用次数会增多
                    arr[j+1] = arr[j];
                    insertIndex = j;
                }
            }
            //待插入元素获取插入位置完成后进行插入
            if (insertIndex != i) {
                arr[insertIndex] = insertVal;
            }
        }
        System.out.println("希尔排序3轮后=" + Arrays.toString(arr));*/
    }
}
