package com.atguigu.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 一个工具类集合
 */
public class Utils {
    public void elapsedTime() {
        System.out.println("排序前");
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);
    }


    public static void pirnt(TreeNode root) {
        // 找到左边的最大偏移量
        int maxLeftOffset = findMaxOffset(root, 0, true);
        int maxRightOffset = findMaxOffset(root, 0, false);
        int offset = Math.max(maxLeftOffset, maxRightOffset);
        // 计算最大偏移量
        Map<Integer, PrintLine> lineMap = new HashMap();
        calculateLines(root, offset, lineMap, 0, true);
        Iterator<Integer> lineNumbers = lineMap.keySet().iterator();
        int maxLine = 0;
        while (lineNumbers.hasNext()) {
            int lineNumber = lineNumbers.next();
            if (lineNumber > maxLine) {
                maxLine = lineNumber;
            }
        }
        for (int i = 0; i <= maxLine; i++) {
            PrintLine line = lineMap.get(i);
            if (line != null) {
                System.out.println(line.getLineString());
            }
        }

    }

    private static void calculateLines(TreeNode parent, int offset, Map<Integer, PrintLine> lineMap, int level,
                                       boolean right) {
        if (parent == null) {
            return;
        }
        int nameoffset = parent.toString().length() / 2;
        PrintLine line = lineMap.get(level);
        if (line == null) {
            line = new PrintLine();
            lineMap.put(level, line);
        }
        line.putString(right ? offset : (offset - nameoffset), parent.toString());
        // 判断有没有下一级
        if (parent.getLeftChild() == null && parent.getRightChild() == null) {
            return;
        }
        // 如果有，添加分割线即/\
        PrintLine separateLine = lineMap.get(level + 1);
        if (separateLine == null) {
            separateLine = new PrintLine();
            lineMap.put(level + 1, separateLine);
        }
        if (parent.getLeftChild() != null) {
            separateLine.putString(offset - 1, "/");
            calculateLines(parent.getLeftChild(), offset - nameoffset - 1, lineMap, level + 2, false);
        }
        if (parent.getRightChild() != null) {
            separateLine.putString(offset + nameoffset + 1, "\\");
            calculateLines(parent.getRightChild(), offset + nameoffset + 1, lineMap, level + 2, true);
        }

    }

    /**
     * 需要打印的某一行
     *
     * @author zhuguohui
     *
     */
    private static class PrintLine {
        /**
         * 记录了offset和String的map
         */
        Map<Integer, String> printItemsMap = new HashMap<>();
        int maxOffset = 0;

        public void putString(int offset, String info) {
            printItemsMap.put(offset, info);
            if (offset > maxOffset) {
                maxOffset = offset;
            }
        }

        public String getLineString() {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i <= maxOffset; i++) {
                String info = printItemsMap.get(i);
                if (info == null) {
                    buffer.append(" ");
                } else {
                    buffer.append(info);
                    //此处i加上字符长度很可能跳过一些节点，导致最终没有打印出来
                    i += info.length();
                }
            }
            return buffer.toString();
        }

    }

    private static int findMaxOffset(TreeNode parent, int offset, boolean findLeft) {
        if (parent != null) {
            offset += parent.toString().length();
        }
        if (findLeft && parent.getLeftChild() != null) {
            offset += 1;
            return findMaxOffset(parent.getLeftChild(), offset, findLeft);
        }
        if (!findLeft && parent.getRightChild() != null) {
            return findMaxOffset(parent.getRightChild(), offset, findLeft);
        }
        return offset;
    }
}
