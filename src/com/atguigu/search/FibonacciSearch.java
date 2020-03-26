package com.atguigu.search;

import java.util.Arrays;

/**
 * author:w_liangwei
 * date:2020/3/26
 * Description: 斐波那契查找
 *
 */
public class FibonacciSearch {

    private static int maxSize = 20;

    public static void main(String[] args) {
        int [] arr = {1,8, 10, 89, 1000, 1234};
        System.out.println("index=" + fibSearch(arr, 89));
    }

    /**
     * 因为后面我们mid=low+F(k-1)-1，需要使用到斐波那契数列，因此我们需要先获取到一个斐波那契数列
     * 非递归方法得到一个斐波那契数列
     */

    public static int[] fib() {
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    /**
     * 编写斐波那契查找算法,使用非递归的方式编写算法
     * @param arr  数组
     * @param key 我们需要查找的关键码(值)
     * @return 返回对应的下标，如果没有-1
     */
    public static int fibSearch(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;
        //生成斐波那契数组
        int[] fib = fib();
        //调整数组大小fib(k)-1的长度
        int k = 0; //排序数组为自己从斐波那契数列中选择的斐波那契数的下标，用于计算mid值和扩充数组容量到符合斐波那契查找要求
        int mid = 0;
        //将数组容量扩充到fib[k]-1来满足分割要求，这个值刚刚大于等于原数组长度就行
        while (fib[k]-1 < high) {
            k++;
        }
        //将数组a扩展到fid[k]-1的长度
        int[] temp = Arrays.copyOf(arr, fib[k] - 1);
        //填充right后空余空间
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }

        //开始进行查找
        while (low <= high) {
            mid = low + fib[k-1] -1;
            if (arr[mid] > key) {
                //向左移动
                high = mid - 1;
                k--;
            } else if (arr[mid] < key) {
                //向右移动
                low = mid + 1;
                k -= 2;
            } else {
                //看最终找到的是在数组原来的部分还是在数组填充了末尾元素的部分，虽然值可能相同但是索引不同。而对于用户来说是看不到被扩充部分的
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }
        return -1;
    }
}
