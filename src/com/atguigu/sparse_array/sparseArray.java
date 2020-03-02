package com.atguigu.sparse_array;

/**
 * 使用稀疏数组实现五子棋存盘
 */
public class sparseArray {
    public static void main(String[] args) {
        //先创建一个原始二维数组
        int chessArr[][] = new int[11][11];
        chessArr[1][2]=1;
        chessArr[2][3]=2;
        //输出原始二维数组
        for (int[] row : chessArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
        //将二维数组转为稀疏数组
        //1.先遍历二维数组得到非0有效数据
        int sum = 0;
        for (int i = 0; i < chessArr.length; i++) {
            for (int j = 0; j < chessArr[i].length; j++) {
                if (chessArr[i][j] != 0) {
                    sum++;
                }
            }
        }
        System.out.println("原始数组中非0元素个数为" + sum);

        //根据上一步获取到的有效数据的个数创建对应的稀疏数组
        int sparseArr[][] = new int[sum + 1][3];
        //稀疏数组的第一行记录的是原数组有几行几列，多少有效数据
        sparseArr[0][0] = chessArr.length;
        sparseArr[0][1] = chessArr.length;
        sparseArr[0][2] = sum;

        //遍历二维数组将非0值放入稀疏数组
        int row = 1;//稀疏数组开始记录有效数据的起始行，第一行已经被记录整体大小和有效数据个数
        for (int i = 0; i < chessArr.length; i++) {
            for (int j = 0; j < chessArr[i].length; j++) {
                if (chessArr[i][j] != 0) {
                    sparseArr[row][0] = i;
                    sparseArr[row][1] = j;
                    sparseArr[row][2] = chessArr[i][j];
                    //下一次要被记录的稀疏数组的行号加1
                    row++;
                }
            }
        }

        //稀疏数组的输出
        System.out.println();
        System.out.println("得到的稀疏数组为======");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.printf("%d\t%d\t%d\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
        }


        //================================================
        //将稀疏数组恢复到二维数组
        //读取稀疏数组第一行数据创建对应大小的二维数组
        int[][] chessArr1 = new int[sparseArr[0][0]][sparseArr[0][1]];
        //i从1开始，索引0对应的第一行是二维数组大小，已经在创建数组时使用
        for (int i = 1; i < sparseArr.length; i++) {
            //每一个sparseArr[i]都是一个数据的位置，分别是行，列，值
            chessArr1[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        //输出恢复后的二维数组
        System.out.println("==========================");
        System.out.println("恢复后的二维数组是");
        for (int[] row1 : chessArr1) {
            for (int data : row1) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }
}
