package com.atguigu.recursion;

/**
 * author:w_liangwei
 * date:2020/3/23
 * Description: 八皇后问题的递归解法，该算法会涉及大量的回溯，效率不高
 * 对于check方法的解释
 *下一层只要的摆放只要发生了冲突，就会退出当前栈回到上一层的栈中，由于上一层是一个for循环，所以会对上一行的列重新进行摆放，符合要求后再去摆放下一个。
 *直到递归到最后一个皇后依然成功，即最后一行摆放完成后退出时会回溯倒着走判断每一行是否和其它行冲突
 */
public class Queen8 {

    private int max = 8;
    private int[] arr = new int[max];
    private static int count = 0;
    private static int judgeCount = 0;
    public static void main(String[] args) {
        //使用一个数组来保存八皇后的最终坐标，数组下标代表行号，数组中该下标取出的值对应的是列号
        Queen8 queen = new Queen8();
        queen.check(0);
        System.out.printf("一共有%d解法\n", count);
        System.out.printf("一共判断冲突的次数%d次", judgeCount); // 1.5w
    }

    /**
     * 放置第n个皇后
     * @param n
     */
    private void check(int n) {
        //当放置的数目超过了最大值即说明都已经放好了,n是下标所以只能走到7，当为8的时候表示8皇后全部摆放完成
        if (n == max) {
            //将摆放完成的结果输出
            count++;
            print();
            return;
        }
        //尝试放置该皇后到棋盘同一行的所有位置，即变换列检查是否符合要求，直到放置位置符合。回溯的发生就是建立在这个循环上
        for (int i = 0; i < max; i++) {
            //将第n个皇后放到第i列
            arr[n] = i;
            //检查是否符合要求
            if (judge(n)) {
                //如果符合要求那么就继续放下一行
                check(n +  1);
            }
            //不符合要求将该行的皇后放到下一列继续
        }
    }

    private boolean judge(int n) {
        //要满足不在同一行，同一类，同意斜线。同一行由n来控制不会相同，同一列取决于数组中的值是否相同，同一斜线取决于该皇后距离另一个皇后的垂直和水平距离是否相等，相等即是同一斜线
        //此处的i < n不能用i < arr.length代替，初始化后数组内数字都为0，获取到的length是8，而且第一次循环总是自己跟自己比相等得到false
        for (int i = 0; i < n; i++) {
            if (arr[n] == arr[i] || Math.abs(n - i) == Math.abs(arr[n] - arr[i])) {
                judgeCount++;
                return false;
            }
        }
        return true;
    }

    public void print() {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
