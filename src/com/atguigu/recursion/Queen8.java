package com.atguigu.recursion;

/**
 * author:w_liangwei
 * date:2020/3/23
 * Description: 八皇后问题的递归解法，该算法会涉及大量的回溯，效率不高
 */
public class Queen8 {

    private int max = 8;
    private int[] arr = new int[max];

    public static void main(String[] args) {
        //使用一个数组来保存八皇后的最终坐标，数组下标代表行号，数组中该下标取出的值对应的是列号
        Queen8 queen = new Queen8();
        queen.check(0);
        //下一层只要的摆放只要发生了冲突，就会退出当前栈回到上一层的栈中，由于上一层是一个for循环，所以会对上一行的列重新进行摆放，符合要求后再去摆放下一个。
        // 直到递归到最后一个皇后依然成功，即最后一行摆放完成后退出时会回溯倒着走判断每一行是否和其它行冲突
    }

    /**
     * 放置第n个皇后
     * @param n
     */
    private void check(int n) {
        //当放置的数目超过了最大值即说明都已经放好了
        if (n > max) {
            //将摆放完成的结果输出
            print();
            return;
        }
        //尝试放置该皇后到棋盘同一行的所有位置，即变换列检查是否符合要求，直到放置位置符合
        for (int i = 0; i < arr.length; i++) {
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
        for (int i = 0; i < arr.length -1; i++) {
            if (arr[n] == arr[i] || Math.abs(n - i) == Math.abs(arr[n] - arr[i])) {
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
