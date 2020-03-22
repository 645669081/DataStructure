package com.atguigu.stack;

/**
 * author:w_liangwei
 * date:2020/3/20
 * Description:
 */
public class Calculator {
    public static void main(String[] args) {
        //根据前面老师思路，完成表达式的运算
        String expression = "123+773*34-1238/2"; // 15//如何处理多位数的问题？
        //创建两个栈，数栈，一个符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        char[] chars = expression.toCharArray();
        //遍历所有的字符，确定是数字还是操作符
        for (int i = 0; i < chars.length; i++) {
            if (ArrayStack2.isOper(chars[i])) {
                //如果此时要入栈的操作符小于等于栈中的操作符的优先级。需要先从数栈取两个数，操作符栈取一个操作符进行计算后将结果入数栈，然后该操作符才能入栈
                //入操作符栈的时候直接入char值，取出来时需要进行转换。从操作符栈取出的操作符
                if (!operStack.isEmpty() && ArrayStack2.priority(chars[i]) <= ArrayStack2.priority(operStack.peek())) {
                    Integer num1 = numStack.pop();
                    Integer num2 = numStack.pop();
                    Integer result = ArrayStack2.cal(num1, num2, operStack.pop());
                    numStack.push(result);
                }
                operStack.push(chars[i]);
            } else {
                //处理多位数,发现是数字时不能立即入栈，而是遍历随后的数字看看是否也是数字，直到不是数字时才能入栈
                StringBuffer sb = new StringBuffer();
                while (i < chars.length && !ArrayStack2.isOper(chars[i])) {
                    sb.append(chars[i]);
                    i++;
                }
                numStack.push(Integer.parseInt(sb.toString()));
                //由于此时chars[temp]所指的位置是操作符，而下一次循环执行时，还会对i+1造成错过了操作符的位置而直接处理下一个数字，导致后续计算错误
                i--;
            }
        }
        //如果表达式的所有数字遍历完毕后，最终将所有栈中剩余的数字和操作符进行计算获得结果
        while (!operStack.isEmpty()) {
            Integer result = ArrayStack2.cal(numStack.pop(), numStack.pop(), operStack.pop());
            numStack.push(result);
        }
        //打印最终结果
        System.out.printf("表达式 %s = %d", expression, numStack.pop());
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
            throw new RuntimeException("栈已满");
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
    public static int priority(int oper) {
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
    public static Integer cal(int num1, int num2, int oper) {
        switch (oper) {
            //对于减法和除法会出现两个数字在操作符前后位置不同计算结果不同，所以需要将第一个弹出的作为操作符后边的数来计算
            case '+': return num1 + num2;
            case '-': return num2 - num1;
            case '*': return num1 * num2;
            case '/': return num2 / num1;
            default:
                System.out.println("操作符不正确");
                return null;
        }
    }
}
