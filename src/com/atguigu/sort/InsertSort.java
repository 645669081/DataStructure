package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * author:w_liangwei
 * date:2020/3/24
 * Description: 插入排序
 * 将数组分为有序和无序两部分，将无序的部分倒序遍历有序找到插入位置
 *
 * 排序速度：插入 > 选择 > 冒泡
 */
public class InsertSort {
    public static void main(String[] args) {
//        int[] arr = {101, 34, 119, 1, -1, 89};
//        int[] arr =  {101, 34, 119, 1};

//        System.out.println("排序前：" + Arrays.toString(arr));

        // 创建要给80000个的随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        insertSort(arr);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);
    }

    //插入排序
    public static void insertSort(int[] arr) {
        //归纳后
        //只要比较确定数组长度-1个数的位置，最后一个数的位置也就确定了。假设第一个数是最初的有序数组中的元素
        int insertVal = 0;
        int insertIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            insertVal = arr[i]; //记录待插入的值，防止比较后数字后移造成待插入值丢失。待插入值为有序数组最后一个元素的后一个元素
            insertIndex = i - 1; //第一个要比较的有序列表数字，将待插入值和倒序和有序列表中的数进行比较。也就是待插入值的前一个数字
            /*
             * 1.防止倒序遍历时索引越界
             * 2.如果待插入值比有序列表中的数小，则将这些数后移。直到找到插入位置
             */
            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                //后移数字
                arr[insertIndex + 1] = arr[insertIndex];
                //遍历指针后移
                insertIndex--;
            }
            //完成遍历找到插入位置,由于最后一次循环是条件不满足的。分为索引越界和数值比较不满足两种，越界说明此时是-1说明已经比较完了有序序列中所有的数字，此时待插入值就应该放在第一个位置。
            //当时值比较不满足时说明待插入值已经比insertIndex位置的值大了，所以insertIndex的后一个位置就是插入位置。即下一次不满足，上一次刚刚好
            //判断插入点的位置是不是最初假设的位置，如果不是才交换。最初假设的值插入点值就是i-1
            if (insertIndex != (i - 1)) {
                arr[insertIndex + 1] = insertVal;
            }
//            System.out.println("第"+ i +"次排序后：" + Arrays.toString(arr));
        }












        /*
        //第一轮
        //将数组分为有序列表和无序列表两部分，假设第一个数是有序列表，将剩余的数当做无序列表。将无序列表中的数一个一个插入到有序列表中
        int insertVal = arr[1]; //记录待插入的值，防止比较后数字后移造成待插入值丢失
        int insertIndex = 1 - 1; //第一个要比较的有序列表数字，将待插入值和倒序和有序列表中的数进行比较。也就是待插入值的前一个数字
        //1.防止倒序遍历时索引越界
        //2.如果待插入值比有序列表中的数小，则将这些数后移。直到找到插入位置
        while (insertIndex >=0 && insertVal < arr[insertIndex]) {
            //后移数字
            arr[insertIndex + 1] = arr[insertIndex];
            //遍历指针后移
            insertIndex--;
        }
        //完成遍历找到插入位置,由于最后一次循环是条件不满足的。分为索引越界和数值比较不满足两种，越界说明此时是-1说明已经比较完了有序序列中所有的数字，此时待插入值就应该放在第一个位置。
        //当时值比较不满足时说明待插入值已经比insertIndex位置的值大了，所以insertIndex的后一个位置就是插入位置。即下一次不满足，上一次刚刚好
        arr[insertIndex + 1] = insertVal;
        System.out.println("第一次排序后：" + Arrays.toString(arr));


        //第二轮
        //将数组分为有序列表和无序列表两部分，假设第一个数是有序列表，将剩余的数当做无序列表。将无序列表中的数一个一个插入到有序列表中
        insertVal = arr[2]; //记录待插入的值，防止比较后数字后移造成待插入值丢失
        insertIndex = 2 - 1; //第一个要比较的有序列表数字，将待插入值和倒序和有序列表中的数进行比较。也就是待插入值的前一个数字

         //1.防止倒序遍历时索引越界
         //2.如果待插入值比有序列表中的数小，则将这些数后移。直到找到插入位置
        while (insertIndex >=0 && insertVal < arr[insertIndex]) {
            //后移数字
            arr[insertIndex + 1] = arr[insertIndex];
            //遍历指针后移
            insertIndex--;
        }
        //完成遍历找到插入位置,由于最后一次循环是条件不满足的。分为索引越界和数值比较不满足两种，越界说明此时是-1说明已经比较完了有序序列中所有的数字，此时待插入值就应该放在第一个位置。
        //当时值比较不满足时说明待插入值已经比insertIndex位置的值大了，所以insertIndex的后一个位置就是插入位置。即下一次不满足，上一次刚刚好
        arr[insertIndex + 1] = insertVal;
        System.out.println("第二次排序后：" + Arrays.toString(arr));

        //第三轮
        //将数组分为有序列表和无序列表两部分，假设第一个数是有序列表，将剩余的数当做无序列表。将无序列表中的数一个一个插入到有序列表中
        insertVal = arr[3]; //记录待插入的值，防止比较后数字后移造成待插入值丢失
        insertIndex = 3 - 1; //第一个要比较的有序列表数字，将待插入值和倒序和有序列表中的数进行比较。也就是待插入值的前一个数字
        //1.防止倒序遍历时索引越界
        //2.如果待插入值比有序列表中的数小，则将这些数后移。直到找到插入位置
        while (insertIndex >=0 && insertVal < arr[insertIndex]) {
            //后移数字
            arr[insertIndex + 1] = arr[insertIndex];
            //遍历指针后移
            insertIndex--;
        }
        //完成遍历找到插入位置,由于最后一次循环是条件不满足的。分为索引越界和数值比较不满足两种，越界说明此时是-1说明已经比较完了有序序列中所有的数字，此时待插入值就应该放在第一个位置。
        //当时值比较不满足时说明待插入值已经比insertIndex位置的值大了，所以insertIndex的后一个位置就是插入位置。即下一次不满足，上一次刚刚好
        arr[insertIndex + 1] = insertVal;
        System.out.println("第三次排序后：" + Arrays.toString(arr));*/
    }
}
