package com.atguigu.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author:w_liangwei
 * date:2020/4/3
 * Description: 构建一个图
 */
public class Graph {

    //存放图的顶点
    public List<String> vertexList;

    //存放图边的邻接矩阵
    public int[][] edges;

    //边的数量
    public int edgeNum;

    //供遍历时查看该节点是否被访问过，索引下标和vertexList中的顺序一致
    private boolean[] visitList;

    public static void main(String[] args) {
        String[] vertexs = {"A", "B", "C", "D", "E"};
        Graph graph = new Graph(5);
        //添加节点
        for (int i = 0; i < vertexs.length; i++) {
            graph.addVertex(vertexs[i]);
        }
        //添加边
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(1, 4, 1);

        graph.show();
    }

    /**
     *  初始化图
     * @param vertexNum 要创建的图的顶点数量
     */
    public Graph(int vertexNum) {
        vertexList = new ArrayList<>(vertexNum);
        edges = new int[vertexNum][vertexNum];
        visitList = new boolean[vertexNum];
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
        visitList[vertexIndex] = true;
        //获取下一个邻接顶点的索引位置
        int adjoinVertex = -1;
        //如果没找到邻接点就回溯一直找，直至找到
        //需要判断是否已经全部访问过了
        while (adjoinVertex == -1) {
            adjoinVertex = getAdjoinVertex(vertexIndex);
        }
        //处理没有找到邻接点的情况
        if (adjoinVertex == -1) {
            //回退到上次访问的顶点再找未访问邻接点
            getAdjoinVertex(visitList.length -1);
        }
        //如果访问过了，则继续寻找下一个邻接点
        if (visitList[adjoinVertex]) {

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
