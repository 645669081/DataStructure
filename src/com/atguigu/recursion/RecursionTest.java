package com.atguigu.recursion;

/**
 * author:w_liangwei
 * date:2020/3/23
 * Description: 通过打印问题和阶乘问题了解递归
 */
public class RecursionTest {
    public static void main(String[] args) {
        //由于递归是只有在底层退出后才会继续执行上层剩余的操作，所以打印的顺序是2、3、4
//        test(4);

        int res = factorial(3);
        System.out.println("res=" + res);
    }

    //打印问题.
    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        } else {
            //当加了else时每次栈中只能有一个分支执行，大于2时一直调用到2不满足条件打印2，但是由于底层栈对出后上层继续执行的分支依然是那个test方法而不是else，所以就不会打印直接退出该栈
            System.out.println("n = " + n);
        }
//        System.out.println("n = " + n);
    }

    //阶乘问题
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n; // 1 * 2 * 3
        }
    }
}
