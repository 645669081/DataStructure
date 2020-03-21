package com.atguigu.queue;

import java.util.Scanner;

/**
 * 使用数组模拟队列
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
//测试一把
        //创建一个队列
        ArrayQueue queue = new ArrayQueue(3);
        char key = ' '; //接收用户输入
        Scanner scanner = new Scanner(System.in);//
        boolean loop = true;
        //输出一个菜单
        while(loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);//接收一个字符
            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g': //取出数据
                    try {
                        int res = queue.getHeadQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h': //查看队列头的数据
                    try {
                        int res = queue.showHeadQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e': //退出
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

class ArrayQueue {
    private int maxSize; //数组最大容量
    private int front; //队列头指针
    private int rear; //队列尾指针
    private int[] arr; //该数组用于存放队列数据

    /**
     * 队列是否已满
     * @return
     */
    public boolean isFull() {
        return rear == maxSize -1; //尾指针是否处于队列的最后一个位置
    }

    /**
     * 队列是否为空
     * @return
     */
    public boolean isEmpty() {
        return front == rear; //只有在初始化后是front=rear=-1，这时候队列为空
    }

    /**
     * 初始化指定大小的队列
     */
    public ArrayQueue(int maxSize) {
        front = -1; //初始化队列头指针，指向的是队列第一个元素的前一个位置
        rear = -1; //初始化队列尾指针，指向的是队列的最后一个元素
        this.maxSize = maxSize;
        arr = new int[maxSize];
    }

    /**
     * 向队列添加元素,相当于从队尾入队
     */
    public void addQueue(int n) {
        //判断队列是否已满
        if (isFull()) {
            System.out.println("队列已满无法添加元素");
            return;
        }
        rear++; //尾指针后移指向新添加的元素位置
        arr[rear] = n;
    }

    /**
     * 从队列中取出元素，相当于从队头出队
     */
    public int getHeadQueue() {
        //判断队列是否为空
        if (isEmpty()) {
            throw new RuntimeException("队列已经为空");
        }
        front++; //将队列的头指针指向下一个出队的元素
        return arr[front];
    }

    /**
     * 打印队列全部元素
     */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("当前队列为空");
            return;
        }
        //队列的头指针的后一个到尾指针都是队列中的元素
        for (int i = front + 1; i <= rear; i++) {
            System.out.printf("arr[%d]=%d\n", i , arr[i]);
        }
    }

    /**
     * 显示队列的头数据，不取出数据
     */
    public int showHeadQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        return arr[front + 1]; //因为front指的是头部元素的前一个位置，所以要获取元素需要+1
    }
}