package com.atguigu.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author:w_liangwei
 * date:2020/3/31
 * Description: 霍夫曼树是一种WPL最小的树，即所有的叶子节点的带权路径长度之和最小。下面以此树为例
 *                    1
 *              2           3
 *        4         5
 *叶子节点只有4,5,3。WPL=4 * 2 + 5 * 2 + 3 * 1 = 21。这就是WPL的计算方式，即所有叶子节点的权值乘以该叶子节点距离根结点的长度的和
 *
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int arr[] = { 13, 7, 8, 3, 29, 6, 1 };
        Node root = createHuffmanTree(arr);
        root.preOrder();
    }

    private static Node createHuffmanTree(int[] arr) {
        //利用权值数组构建节点放入集合方便使用集合特性操作
        List<Node> nodeList = new ArrayList<>();
        for (int weight : arr) {
            Node node = new Node(weight);
            nodeList.add(node);
        }
        //将权值数据排序
        Collections.sort(nodeList);

        //当集合中剩余节点大于1，表明还没有创建霍夫曼树完成
        while (nodeList.size() > 1) {
            //取出权值最小的结点（二叉树）
            Node left = nodeList.get(0);
            //取出权值第二小的结点（二叉树）
            Node right = nodeList.get(1);
            //使用这两个节点生成一个父节点
            Node parent = new Node(left.value + right.value);
            //删除合并完成的两个节点并将新节点添加到集合
            nodeList.remove(left);
            nodeList.remove(right);
            nodeList.add(parent);
        }
        return nodeList.get(0);
    }
}



/**
 * 定义节点类
 * 为了让Node 对象持续排序Collections集合排序
 */
class Node implements Comparable<Node> {
    //节点权值
    public int value;
    public Node left;
    public Node right;

    public Node(int value) {
        this.value = value;
    }

    public void preOrder(Node root) {
        if (root == null) {
            return;
        }
        System.out.println(root);
        preOrder(root.left);
        preOrder(root.right);
    }

    public void preOrder() {
        preOrder(this);
    }

    @Override
    public String toString() {
        return "Node [value=" + value + "]";
    }

    @Override
    public int compareTo(Node o) {
        //表示从小到大排序
        return this.value - o.value;
    }
}