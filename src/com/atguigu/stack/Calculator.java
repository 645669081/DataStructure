package com.atguigu.stack;

/**
 * author:w_liangwei
 * date:2020/3/20
 * Description:
 */
public class Calculator {
    public static void main(String[] args) {
        //根据前面老师思路，完成表达式的运算
        String expression = "7*2*2-5+1-5+3-4"; // 15//如何处理多位数的问题？
        //创建两个栈，数栈，一个符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        char[] chars = expression.toCharArray();
        for (char c : chars) {
            //数字直接入数栈
            if (!ArrayStack2.isOper(c)) {
                numStack.push(c);
            } else {
                //是符号对优先级进行比较,如果加入符号的优先级比前一个低说明需要先进行计算
                if (ArrayStack2.priority(c) <= ArrayStack2.priority('c')) {

                }
            }
        }
    }
}

/**
 * 定义一个 ArrayStack 表示栈
 */
class ArrayStack2 {
    // 栈的大小
    public int maxSize;
    // 数组，数组模拟栈，数据就放在该数组
    public int top = -1;
    //表示栈顶，初始化为-1
    public int[] stack;

    public ArrayStack2(int size) {
        this.maxSize = size;
        this.stack = new int[size];
    }

    public boolean isFull() {
        return top == maxSize-1;
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
        stack[top] = n;
    }

    /**
     * 弹出栈顶元素
     * @return
     */
    public Integer pop() {
        if (isEmpty()) {
            System.out.println("栈为空");
            return null;
        }
        int value = stack[top];
        top--;
        return value;
    }

    /**
     * 查看栈顶元素
     * @return
     */
    public Integer peek() {
        if (isEmpty()) {
            System.out.println("栈为空");
            return null;
        }
        return stack[top];
    }

    /**
     * 遍历栈
     */
    public void list() {
        if (isEmpty()) {
            System.out.println("栈为空");
            return;
        }
        //遍历栈的元素时需要先加入的后显示，即先进后出
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }

    /**
     * 返回运算符的优先级，返回的数字越大优先级越高
     */
    public static int priority(char oper) {
        if (oper == '+' || oper == '-') {
            return 1;
        }
        if (oper == '*' || oper == '/') {
            return 2;
        }
        return -1;
    }

    /**
     * 判断是否为操作符
     */
    public static boolean isOper(char oper) {
        return oper == '+' || oper == '-' || oper == '*' || oper == '/';
    }

    /**
     * 传递两个数和一个操作符来返回计算结果
     */
    public Integer cal(int num1, int num2, char oper) {
        switch (oper) {
            case '+': return num1 + num2;
            case '-': return num1 - num2;
            case '*': return num1 * num2;
            case '/': return num1 / num2;
            default:
                System.out.println("操作符不正确");
                return null;
        }
    }
}
