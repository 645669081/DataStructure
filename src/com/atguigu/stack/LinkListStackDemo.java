package com.atguigu.stack;

import java.util.Scanner;

/**
 * author:w_liangwei
 * date:2020/3/20
 * Description: 使用双向链表模拟栈
 */
public class LinkListStackDemo {
    public static void main(String[] args) {
        //链表模拟栈是否正确
        //先创建一个LinkListStack对象->表示栈
        LinkListStack stack = new LinkListStack(3);
        String key = "";
        boolean loop = true; //控制是否退出菜单
        Scanner scanner = new Scanner(System.in);

        while(loop) {
            System.out.println("show: 表示显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 表示添加数据到栈(入栈)");
            System.out.println("pop: 表示从栈取出数据(出栈)");
            System.out.println("请输入你的选择");
            key = scanner.next();
            switch (key) {
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        int res = stack.pop();
                        System.out.printf("出栈的数据是 %d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出~~~");
    }
}

//定义链表模拟栈,为了方便遍历，使用双向含头尾节点的链表来模拟
class LinkListStack {
    //栈大小
    public int size;
    //栈指针
    public int top = -1;
    //头结点
    public Node head;
    //尾节点
    public Node end;

    public LinkListStack(int size) {
        this.size = size;
        head = new Node();
        end = new Node();
        //初始化头尾节点的前后连接
        head.next = end;
        end.pre = head;
    }

    public boolean isFull() {
        return top == size -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(int n) {
        if (isFull()) {
            System.out.println("栈已满");
            return;
        }
        top++;
        Node node = new Node(n);
        Node current = head;
        //遍历找到链表的结尾挂上去
        while (true) {
            if (current.next == end) {
                break;
            }
            current = current.next;
        }
        //将要入栈的元素加入到最后一个节点和尾节点之间
        current.next = node;
        node.next = end;
        end.pre = node;
        node.pre = current;
    }

    public Integer pop() {
        if (isEmpty()) {
            System.out.println("栈为空");
            return null;
        }
        top--;
        //由于有尾指针end不需要再遍历整个链表找最后添加的一个
        int value = end.pre.data;
        //删除节点弹出栈
        end.pre.pre.next = end;
        end.pre = end.pre.pre;
        return value;
    }

    //遍历栈
    public void list() {
        if (isEmpty()) {
            System.out.println("栈为空");
            return;
        }
        //需要将链表中的节点倒序输出
        Node current = end.pre;
        for (int i = top; i >= 0; i--) {
            //说明已经完成遍历
            if (current == head) {
                break;
            }
            System.out.printf("stack[%d]=%d\n", i, current.data);
            //节点前移
            current = current.pre;
        }
    }
}


//定义链表中节点
class Node {
    public Integer data;
    public Node next;
    public Node pre;

    public Node(Integer data) {
        this.data = data;
    }

    public Node() {

    }
}
