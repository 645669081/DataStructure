package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 快速排序
 * 1. 选择基准数，随便选，只要是start和end范围内的就行。此处由于数字交换时基准数作为了被覆盖的变量，所以只能选择low所指位置，并且还要先从右边开始找
 * 2. 初始化左指针low和右指针high
 * 3. low和high一个向后一个向前分别找比基准数大的数和基准数小的数
 * 4. low找到的数与high交换，然后high找到的与low交换。找到所有符合交换条件的数
 * 5. 最后在low和high重合的位置放置基准数
 * 6. 分别递归左边和右边直到整个数组排序完成
 */
public class QuickSort {

    private static int count = 1;

    public static void main(String[] args) {
//        int[] arr = {-9,78,0,23,78,-567,70,10, -1,900, 4561,10};
//        int[] arr = {-9, 78, 0 ,23, -567, 70};
//        System.out.println("排序前" + Arrays.toString(arr));

        //测试快排的执行速度
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

        quickSort(arr, 0 , arr.length -1);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);
    }

    /**
     * 对数组特定区间范围内进行排序
     * @param arr   要排序的数组
     * @param start 要排序数组的起始位置
     * @param end   要排序数组的结束位置
     */
    public static void quickSort(int[] arr,int start, int end) {
        //如果起始位置大于等于结束位置退出递归排序
        if (start >= end) {
            return;
        }
        //初始化左右部分的指针移动时使用
        int low = start;
        int high = end;
        /*
        1.假定基准数为数组要排序区间的第一个数。本来基准数是可以随便取，但是由于在交换位置时没有使用临时变量而是直接覆盖基准数的位置，如果low最初指向的位置不是基准数的位置时那么就会出现
        被覆盖的是其它数字并且没有被变量记录过，导致后续的排序发生错误
        2.在和基准数比较大小时只能先从右边开始，这样是为了右边找到了值之后直接覆盖已经记录的基准数就好。如果先从左边开始找会去覆盖右边没有记录的值，右边的变量丢失。后续排序发生错误
        3. 基准数的选择很重要，虽然选别的基准数也可以，但是选start为基准数并先从右边开始找就可以省去一个临时变量交换的麻烦而是直接将值覆盖到对方的指针即可
        */
        int baseNum = arr[start];

//        System.out.println("第" + count + "次快速排序前是：" + Arrays.toString(arr) + "，基准数是：" + baseNum);
        //每循环一次只是左右各自移动一个数，而左右都有多个数需要移动，所以需要循环多次。只要左右指针不相遇就一直查找并交换
        while (low < high) {
            //右边high指针一直向前移动直到找出比基准数小的数，一定不要忽略等号，不然会造成当有和基准数相等的值指针永远不会移动
            while (low < high && arr[high] >= baseNum) {
                high--;
            }
            //将右边找到的比基准数小的数复制到low指针所指位置，此处的交换就利用了覆盖已经记录的基准数的特性，省去临时变量
            arr[low] = arr[high];
            //左边low指针一直向后移动直到找出比基准数大的数
            while (low < high && arr[low] <= baseNum) {
                low++;
            }
            //将左边找到的比基准数大的数复制到high指针所指位置
            arr[high] = arr[low];
        }
        //所有数交换完毕后此时low等于high，还需要将基准数放到low所指的位置
        arr[low] = baseNum;
//        System.out.println("第" + count + "次快速排序后是：" + Arrays.toString(arr));
        count++;
        //递归排序左边的
        quickSort(arr, start, low);
        //递归排序右边的
        quickSort(arr, low + 1, end);
    }
}
