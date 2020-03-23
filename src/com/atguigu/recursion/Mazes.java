package com.atguigu.recursion;

/**
 * author:w_liangwei
 * date:2020/3/23
 * Description: 使用递归实现走出迷宫
 */
public class Mazes {
    public static void main(String[] args) {
        //构建迷宫
        int[][] map = new int[8][8];
        //生成墙壁,1代表墙壁
        for (int i = 0; i < map.length; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        for (int i = 0; i < map.length; i++) {
            map[i][0] = 1;
            map[i][7] = 1;
        }
        //设置挡板, 1 表示
        map[3][1] = 1;
        map[3][2] = 1;
        map[1][2] = 1;
//		map[2][2] = 1;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        setWay(map, 1, 1);
        System.out.println("走出迷宫的路径如下");

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 使用递归给小球找路径
     *	//1. map 表示地图
     * 	//2. i,j 表示从地图的哪个位置开始出发 (1,1)
     * 	//3. 如果小球能到 map[6][5] 位置，则说明通路找到.
     * 	//4. 约定： 当map[i][j] 为 0 表示该点没有走过 当为 1 表示墙  ； 2 表示通路可以走 ； 3 表示该点已经走过，但是走不通
     * 	//5. 在走迷宫时，需要确定一个策略(方法) 下->右->上->左 , 如果该点走不通，再回溯
     * @param map 表示地图
     * @param i 从哪个位置开始找
     * @param j
     * @return 如果找到通路，就返回true, 否则返回false
     */
    public static boolean setWay(int[][] map, int i, int j) {
        //退出条件是当走到出口时
        if (map[6][6] == 2) {
            return true;
            //当前点没有走过，先走一次
        } else if (map[i][j] == 0){
            //先假设认为当前点map[i}[j]=2，即当前点为可通过的点
            //不是出口，说明还需要找出口。按照下左上右来找出口
            map[i][j] = 2;
            if (setWay(map, i, j + 1)) {
                return true;
            } else if (setWay(map, i -1, j)) {
                return true;
            } else if (setWay(map, i + 1, j)) {
                return true;
            } else if (setWay(map, i, j + 1)) {
                return true;
            } else {
                //此时说明都没有走通，因为只有返回的为true才算通。
                //当前点走过了不通设置为3
                map[i][j] = 3;
                return false;
            }
        } else {
            //此时map[i][j] != 0只可能是1,2,3其中的一个，但是这些值都不是通路
            return false;
        }
    }
}
