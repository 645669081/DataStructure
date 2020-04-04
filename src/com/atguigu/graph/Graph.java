package com.atguigu.graph;

import java.util.*;

/**
 * author:w_liangwei
 * date:2020/4/3
 * Description: 构建一个图
 *
 * 深度优先遍历思路
 * 1.输出当前顶点，并将当前顶点标记为已访问
 * 2.获取当前顶点的邻接顶点
 *      2.1 行前顶点所在矩阵行的下一个顶点是当前顶点的邻接顶点并没有访问过，则递归执行遍历
 *      2.2 下一个顶点不是当前顶点的邻接顶点或已经被访问过，则继续往后找
 *
 *
 * 广度优先遍历思路：广度是通过队列访问的顺序性实现的
 * 1.访问当前顶点并入队
 * 2.只要队列不为空就弹出对头元素
 * 3.访问对头元素的邻接顶点并加入队列
 *
 *
 * DFS和BFS比较
 * 1.空间复杂度相同，都是O(n),DFS用了栈，BFS用了队列。递归也是栈，只不过是语言底层实现的
 * 2.时间复杂度只与存储结构（邻接矩阵或邻接表）有关，与搜索路径无关。
 *   用邻接矩阵实现的时间复杂度是n^2级，用邻接表实现的时间复杂度是(n+e)级。n表示顶点个数，e表示邻接表中表结点的个数
 *
 */
public class Graph {

    //存放图的顶点
    public List<String> vertexList;

    //存放图边的邻接矩阵
    public int[][] edges;

    //边的数量
    public int edgeNum;

    //供遍历时查看该节点是否被访问过，索引下标和vertexList中的顺序一致
    private boolean[] dfsvisitList;

    private boolean[] bfsvisitList;

    //使用队列的有序性实现广度优先遍历，在遍历时保存访问完的顶点，从而来保证下次访问邻接节点也是该顺序
    public Queue<Integer> queue = new LinkedList();

    public static void main(String[] args) {
//        String[] vertexs = {"A", "B", "C", "D", "E"};
        String[] vertexs = {"1", "2", "3", "4", "5", "6", "7", "8"};
        Graph graph = new Graph(vertexs.length);

        //添加节点
        for (int i = 0; i < vertexs.length; i++) {
            graph.addVertex(vertexs[i]);
        }

        //添加边
//        graph.addEdge(0, 1, 1);
//        graph.addEdge(0, 2, 1);
//        graph.addEdge(1, 2, 1);
//        graph.addEdge(1, 3, 1);
//        graph.addEdge(1, 4, 1);

        //更新边的关系
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(1, 4, 1);
        graph.addEdge(3, 7, 1);
        graph.addEdge(4, 7, 1);
        graph.addEdge(2, 5, 1);
        graph.addEdge(2, 6, 1);
        graph.addEdge(5, 6, 1);

        //打印邻接矩阵
        graph.show();
        System.out.println();
        System.out.println("深度优先遍历");
        graph.dfs(0);
        System.out.println("\n广度优先遍历");
        graph.bfs(0);
    }

    /**
     *  初始化图
     * @param vertexNum 要创建的图的顶点数量
     */
    public Graph(int vertexNum) {
        vertexList = new ArrayList<>(vertexNum);
        edges = new int[vertexNum][vertexNum];
        bfsvisitList = new boolean[vertexNum];
        dfsvisitList = new boolean[vertexNum];
    }

    //添加顶点
    public void addVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1 第一个顶点在顶点集合中的索引
     * @param v2 第二个顶点在顶点集合中的索引
     * @param weight 边的权重，0不存在边，非0存在边
     */
    public void addEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        edgeNum++;
    }

    //获取图中边的条数
    public int getEdgeNum() {
        return edgeNum;
    }

    //获取图中顶点个数
    public int getVertexNum() {
        return vertexList.size();
    }

    //查看图中边的情况，即打印邻接矩阵
    public void show() {
        if (edgeNum <= 0) {
            return;
        }
        for (int i = 0; i < edges.length; i++) {
            System.out.println(Arrays.toString(edges[i]));
        }
    }

    /**
     * 深度遍历图dfs
     * @param vertexIndex 顶点在vertexList中的索引位置
     */
    public void dfs(int vertexIndex) {
        System.out.print(vertexList.get(vertexIndex) + "->");
        //将顶点置为已访问
        dfsvisitList[vertexIndex] = true;
        //通过保存边信息的邻接矩阵，找到了当前顶点的邻接顶点
        for (int i = 0; i < vertexList.size(); i++) {
            if (edges[vertexIndex][i] != 0 && !dfsvisitList[i]) {
                dfs(i);
            }
        }
    }

    /** 广度优先遍历图bfs，不适用于非连通图
     * @auther 梁伟
     * @Description 这里的队列只是放入已经访问过的元素，下次根据当前顺序来获取这些顶点的邻接点并访问，这样就保证了邻接点的访问顺序实现了遍历是
     *              横着走的，而不是一条道走到黑，从而实现广度优先。始终保证先访问再入队
     * @Date 2020/4/4 12:46
     * @Param [vertexIndex] 起始的顶点
     * @return void
     **/
    public void bfs(int vertexIndex) {
        System.out.print(vertexList.get(vertexIndex) + "->");
        bfsvisitList[vertexIndex] = true;

        //队列用于将已访问的顶点存入，按照当前存入顺序访问其邻接点
        queue.add(vertexIndex);

        //队列不空就说明还没有未访问顶点
        while (!queue.isEmpty()) {
            //弹出队列头元素用于获取邻接点
            Integer head = queue.remove();
            //获取当前顶点的所有邻接点
            for (int i = 0; i < vertexList.size(); i++) {
                //当前邻接点在二维数组edges中的值，不为0表示存在邻接点，不然就没有邻接点。邻接点的索引就是i
                int adjoinPointInEdgesVal = edges[head][i];
                if (adjoinPointInEdgesVal != 0 && !bfsvisitList[i]) {
                    //访问并添加邻接点到队列。添加的邻接点的位置，是edges中列的索引位置，也就是二维数组的第二个下标，不是索引位置的值
                    System.out.print(vertexList.get(i) + "->");
                    bfsvisitList[i] = true;
                    queue.add(i);
                }
            }
        }
    }

    /**
     * @auther 梁伟
     * @Description 遍历整个图的方法，对非连通图也适用
     * @Date 2020/4/4 23:41
     * @Param []
     * @return void
     **/
    public void bfs() {
        for (int i = 0; i < vertexList.size(); i++) {
            if (!bfsvisitList[i]) {
                bfs(i);
            }
        }
    }


    /**
     * 获取当前顶点的下一个邻接顶点
     * 根据邻接矩阵的表示的边的情况可以看到，当前顶点处在行的位置，如果它与别的顶点有邻接就是非0，所以只需要找到当前顶点所在行的邻接矩阵中非0的点
     * @param vertexIndex 当前顶点在顶点集合中的索引
     * @return 下一个邻接顶点的索引
     */
    public int getAdjoinVertex(int vertexIndex) {
        //此时的i表示邻接矩阵中当前节点所在行的那个数组的索引，返回这个i实际上就是返回了邻接顶点的索引。
        for (int i = 0; i < edges[vertexIndex].length; i++) {
            if (edges[vertexIndex][i] > 0) {
                return i;
            }
        }
        return -1;
    }
}
