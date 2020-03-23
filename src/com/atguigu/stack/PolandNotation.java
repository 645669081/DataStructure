package com.atguigu.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 逆波兰表达式(后缀表达式)
 * 中缀表达式就是我们常人阅读能看懂的形式，而有利于计算机运算的是后缀表达式，所以要进行转换
 * 本次使用后缀表达式实现计算器
 * 不管是前缀，中缀，后缀只要生成了表达式后计算时都是一样的步骤，遇到操作符弹出2个数进行计算结果入栈
 */
public class PolandNotation {
    public static void main(String[] args) {
        //测试将文本表达式转为中缀表达式的list
        String expression = "1+((2+3)*4)-5";
        List<String> infixExpressionList = toInfixExpressionList(expression);
        System.out.println("中缀表达式对应的List=" + infixExpressionList); // ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]

        //将上述的中缀表达式转为后缀表达式
        List<String> suffixExpreesionList = parseSuffixExpreesionList(infixExpressionList);
        System.out.println("后缀表达式对应的List" + suffixExpreesionList); //[1,2,3,+,4,*,+,5,–]

        //计算逆波兰表达式结果
        System.out.printf("expression = %d", calculate(suffixExpreesionList));
    }

    /**
     * 中缀表达式转后缀
     * 为了实现对原来栈的表达式的逆序输出，直接使用list进行了正序输出
     * @param expressionList 由toInfixExpressionList获得到的中序表达式的list
     */
    public static List<String> parseSuffixExpreesionList(List<String> expressionList) {
        //需要两个栈，一个操作符栈，一个中间结果栈。由于中间结果栈输出的后缀表达式还需要逆序一下，所以对中间结果栈使用list代替，省去了一次逆序操作
        Stack<String> s1 = new Stack<>(); //操作符栈
        List<String> s2 = new ArrayList<>(); //中间结果集合来代替中间结果栈
        for (String item : expressionList) {
            //如果是数字
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if ("(".equals(item)){
                //对左括号处理,直接入操作符栈
                s1.push(item);
            } else if (")".equals(item)) {
                //对右括号处理，从操作符栈中找到左括号，将找到的左括号和这个右括号之间所有的数字和操作符都移动到中间结果集合
                while (!s1.peek().equals("(")) {
                    //找到的不是左括号就将该值移动到中间结果集合
                    s2.add(s1.pop());
                }
                //找到这对括号好直接丢弃，对右括号不处理即可，对左括号进行出栈
                s1.pop();
            } else {
                //除了数字，括号后剩余的都是运算符。括号不算做运算符，不能参与运算符优先级比较
                while (!s1.isEmpty() && Operation.getValue(item) <= Operation.getValue(s1.peek())) {
                    //只要当前操作符的优先级不大于栈顶操作符的优先级，那么将栈顶操作符移动到s2中间结果集合。直到当前操作符的优先级大于了栈顶操作符的优先级停止移动
                    s2.add(s1.pop());
                }
                //操作符栈为空或当前操作符优先级大于栈顶操作符优先级则直接入栈
                s1.push(item);
            }
        }
        //将s1中的剩余全部加入s2
        while (!s1.isEmpty()) {
            s2.add(s1.pop());
        }
        //注意因为是存放到List, 因此按顺序输出就是后缀表达式
        return s2;
    }

    /**
     * 将字符串表达式转为中序的表达式的list，不涉及运算符优先级的比较
     * 将表达式1+((2+3)×4)-5转为ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]，即每一个数字和操作符按顺序放入到list中
     * @param s
     * @return
     */
    public static List<String> toInfixExpressionList(String s) {
        char[] chars = s.toCharArray();
        List<String> expressionList = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            //数字0-9范围的ASCII码在48和57之间，包含这两个数
            if (chars[i] < 48 || chars[i] > 57) {
                expressionList.add(String.valueOf(chars[i]));
            } else {
                //是数字,但是需要处理多位数
                StringBuffer sb = new StringBuffer();
                while (i < chars.length && chars[i] >=48 && chars[i] <= 57) {
                    sb.append(chars[i]);
                    i++;
                }
                //往后退1，不然下次循环时加1后会造成遍历错过符号位
                i--;
                expressionList.add(sb.toString());
            }
        }
        return expressionList;
    }

    /**
     * 对逆波兰表达式进行运算
     * 得到的逆波兰表达式中只有加减乘除，已经没有括号，遍历表达式入栈数字，遇到符号弹栈2个数字运算即可
     * @param expressionList 是一个逆波兰表达式，由中缀表达式转换得到
     */
    public static int calculate(List<String> expressionList) {
        Stack<String> stack = new Stack<>();
        for (String item : expressionList) {
            //判断是数字还是操作符
            if (item.matches("\\d+")) {
                stack.push(item);
                continue;
            } else {
                int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                switch (item) {
                    case "+": stack.push(String.valueOf(num1 + num2));
                    break;
                    case "-": stack.push(String.valueOf(num2 - num1));
                    break;
                    case "*": stack.push(String.valueOf(num1 * num2));
                    break;
                    case "/": stack.push(String.valueOf(num2 - num1));
                    break;
                    default:
                        System.out.println("运算符不存在");
                }
            }
        }
        return Integer.parseInt(stack.pop());
    }
}


//编写一个类 Operation 可以返回一个运算符 对应的优先级
class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    //写一个方法，返回对应的优先级数字
    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                //此处可能传入左括号，即没有右括号抵消时，左括号判断优先级在表达式中是最小的优先级
                System.out.println("不存在该运算符" + operation);
                break;
        }
        return result;
    }

}
