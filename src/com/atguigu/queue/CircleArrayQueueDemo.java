package com.atguigu.queue;

import java.util.Scanner;

/**
 * author:w_liangwei
 * date:2020/3/17
 * Description:数组模拟环形队列
 */
public class CircleArrayQueueDemo {
    public static void main(String[] args) {
//测试一把
        System.out.println("测试数组模拟环形队列的案例~~~");

        // 创建一个环形队列
        CircleArray queue = new CircleArray(4); //说明设置4, 其队列的有效数据最大是3
        char key = ' '; // 接收用户输入
        Scanner scanner = new Scanner(System.in);//
        boolean loop = true;
        // 输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);// 接收一个字符
            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g': // 取出数据
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h': // 查看队列头的数据
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e': // 退出
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出~~");
    }
}


class CircleArray {
    //数组容量，并不是队列容量
    private int maxSize;
    //指向队列最后一个元素的下一个位置，实际上数组pear+1的位置总是预留位置，不会来存储数据。初始值为0。保留会随着元素的增删而发生变化
    private int pear;
    //指定队列的第一个元素，初始值为0
    private int front;
    private int[] arr;

    public CircleArray(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
        //由于pear和front都要初始化为0而java默认类属性就是0，所以不需要手动初始化
    }

    public boolean isFull() {
        //具体可以画图看一下,数组中pear+1是不放元素的约定位置后的一个位置，需要保证始终有一个保留位置。
        //当保留位置的下一个位置恰好是front时，说明尾追上了头，保存元素的所有位置已满，此时只剩下一个保留位置
        return (pear + 1) % maxSize == front;
    }

    public boolean isEmpty() {
        return front == pear;
    }

    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("队列已满，无法继续添加");
            return;
        }
        //由于pear指向的是最后一个元素的下一个位置，所以直接可以添加到该位置
        arr[pear] = n;
        //指针后移，防止越界进行取模
        pear = (pear + 1) % maxSize;
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        //front指向队列的第一个元素所以直接取就可以
        int value = arr[front];
        //指针后移，防止越界进行取模
        front = (front + 1) % maxSize;
        return value;
    }

    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空");
            return;
        }
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    public int size() {
        return (pear + maxSize - front) % maxSize;
    }

    public int  headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        return arr[front];
    }
}