package com.atguigu.stack;

import java.util.ArrayList;
import java.util.List;

/**
 * 逆波兰表达式(后缀表达式)
 * 中缀表达式就是我们常人阅读能看懂的形式，而有利于计算机运算的是后缀表达式，所以要进行转换
 * 本次使用后缀表达式实现计算器
 */
public class PolandNotation {
    public static void main(String[] args) {
        //测试将文本表达式转为中缀表达式的list
        String expression = "1+((2+3)*4)-5";
        List<String> infixExpressionList = toInfixExpressionList(expression);
        System.out.println("中缀表达式对应的List=" + infixExpressionList); // ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]

        //将上述的中缀表达式转为后缀表达式

    }

    /**
     * 中缀表达式转后缀
     * 为了实现对原来栈的表达式的逆序输出，直接使用list进行了正序输出
     */
    public static List<String> parseSuffixExpreesionList(List<String> ls) {
        return null;
    }

    /**
     * 将字符串表达式转为中序的表达式的list
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
}
