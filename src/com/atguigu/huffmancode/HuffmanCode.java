package com.atguigu.huffmancode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:w_liangwei
 * date:2020/3/31
 * Description: 霍夫曼编码，一种前缀编码，可实现无损压缩。使用霍夫曼树可达到发送频率高的字符编码短，频率小的字符编码长
 * 有多个相同的权值时，构建霍夫曼树过程中新生成的父节点的权值如果和原有的节点的权值相同，那父节点可能的排序到相同大小的任何位置，所以生成的霍夫曼树可能不一样，但WPD是一样的最小的。
 * 如2，4，6，6，6，取出2和4生成6可能放到原有2个6的中间或者两边，导致最终生成的霍夫曼树就可能不一样
 */
public class HuffmanCode {

    //存放霍夫曼编码
    static Map<Byte, String> huffmanCodes = new HashMap<Byte,String>();

    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        System.out.println("未压缩的长度：" + contentBytes.length);

        //测试一把，创建的赫夫曼树
//        List<Node> nodes = getNodes(contentBytes);
//        System.out.println("nodes=" + nodes);

//        System.out.println("赫夫曼树前序遍历");
//        Node huffmanTreeRoot = createHuffmanTree(nodes);
//        huffmanTreeRoot.preOrder();

        //测试一把是否生成了对应的赫夫曼编码
//        getCodes(huffmanTreeRoot);
//        System.out.println("~生成的赫夫曼编码表= " + huffmanCodes);

        //测试
//        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
//        System.out.println("huffmanCodeBytes=" + Arrays.toString(huffmanCodeBytes));//17

        byte[] bytes = huffmanZip(contentBytes);
//        System.out.println(Arrays.toString(bytes));
//        System.out.println(bytes.length);

    }

    /**
     * 将一个byte 转成一个二进制的字符串, 如果看不懂，可以参考我讲的Java基础 二进制的原码，反码，补码
     * @param b 传入的 byte
     * @param flag 标志是否需要补高位如果是true ，表示需要补高位，如果是false表示不补, 如果是最后一个字节，无需补高位
     * @return 是该b 对应的二进制的字符串，（注意是按补码返回）
     */
    private static String byteToBitString(byte b) {
//        String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
        String binaryString = Long.toString(b & 0xff, 2);
//        System.out.println(binaryString);
        return binaryString;
    }

    /**
     * 完成对压缩数据的解码
     * @param huffmanCodes 赫夫曼编码表 map
     * @param huffmanBytes 赫夫曼编码得到的字节数组
     * @return 就是原来的字符串对应的数组
     */
    private static byte[] decode(Map<Byte,String> huffmanCodes, byte[] huffmanBytes) {
        //获取霍夫曼编码后的二进制字符串
        StringBuilder bitString = new StringBuilder();
        for (byte huffmanByte : huffmanBytes) {
            bitString.append(byteToBitString(huffmanByte));
        }
        //构建一个反向映射的map便于解码时使用
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> byteStringEntry : huffmanCodes.entrySet()) {
            map.put(byteStringEntry.getValue(), byteStringEntry.getKey());
        }
        //从编码表中拿到编码对应的字母
        int index = 0;
        StringBuilder content = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        //遍历字符串找对应的字符
        while (index < bitString.length()) {
            //该字符解码表中不包含则继续移动到下一位
            if (!map.containsKey(temp.toString())) {
                temp.append(bitString.charAt(index));
                index++;
            } else {
                content.append(map.get(temp.toString()));
                temp.delete(0, temp.length());
            }
        }
        return content.toString().getBytes();
    }

    /**
     *使用一个方法，将前面的方法封装起来，便于我们的调用.
     * @param bytes 原始的字符串对应的字节数组
     * @return 是经过 赫夫曼编码处理后的字节数组(压缩后的数组)
     */
    private static byte[] huffmanZip(byte[] bytes) {
        List<Node> nodes = getNodes(bytes);
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        getCodes(huffmanTreeRoot);
        return zip(bytes, huffmanCodes);
    }

    /**
     *，将字符串对应的byte[] 数组，通过生成的赫夫曼编码表，返回一个赫夫曼编码压缩后的byte[]
     * @param bytes 这时原始的字符串对应的 byte[]
     * @param huffmanCodes 生成的赫夫曼编码map
     * @return 返回赫夫曼编码处理后的 byte[]
     * 举例： String content = "i like like like java do you like a java"; =》 byte[] contentBytes = content.getBytes();
     * 返回的是 字符串 "1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100"
     * => 对应的 byte[] huffmanCodeBytes  ，即 8位对应一个 byte,放入到 huffmanCodeBytes
     * huffmanCodeBytes[0] =  10101000(补码) => byte  [推导  10101000=> 10101000 - 1 => 10100111(反码)=> 11011000= -88 ]
     * huffmanCodeBytes[1] = -88
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        //获取要发送的霍夫曼编码过后的字符串
        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            String code = huffmanCodes.get(aByte);
            builder.append(code);
        }
//        System.out.println("霍夫曼编码后的二进制字符串是：" + builder);
        System.out.println(builder.toString());
        //因为霍夫曼编码是前缀编码，所以可以全部无间隔放到一起。此时的得到的字符串比未编码时还要长，所以需要使用最小的方式存储。即存储为byte的数组的形式。这样才达到了压缩的目的
        int length;
        //每8位就可以构成一个byte，计算byte长度用来创建数组
        if (builder.length() % 8 == 0) {
            length = builder.length() / 8;
        } else {
            length = builder.length() / 8 + 1;
        }
        //创建存储最终byte内容的数组
        byte[] huffmanCodeBytes = new byte[length];
        //分别用i表示数组索引，j表示builder字符串的切割位置
        for (int i = 0, j = 0; i < huffmanCodeBytes.length; i++, j += 8) {
            String byteStr;
            if (j + 8 > builder.length() -1) {
                byteStr = builder.substring(j, builder.length() - 1);
            } else {
                byteStr = builder.substring(j, j + 8);
            }
            huffmanCodeBytes[i] = (byte) Integer.parseInt(byteStr, 2);
        }
        return huffmanCodeBytes;
    }

    /**
     * 功能：将传入的node结点的所有叶子结点的赫夫曼编码得到，并放入到huffmanCodes集合
     * @param node  传入结点
     * @param code  路径： 左子结点是 0, 右子结点 1
     * @param stringBuilder 用于拼接路径
     */
    private static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        //每一个字符都需要自己的一个stringBuilder，当上一次node.data==null时才会拼接，每次进行拼接的都是上一次的
        //这样做只需要保证第一个处理的节点不为null，不然sb会多拼接一个，但实际根结点没有子节点。虽然此时sb是多拼接了一位而不是空白，但是if(node != null)保证了这个为空的子节点虽然拼接了，
        //但是不会被装入最终的huffmanCodes的map中去
        //每次为上一次的拼接前都先去创建一个StringBuilder保存上次的结果，然后再为其拼接
        StringBuilder sb = new StringBuilder(stringBuilder);
        sb.append(code);
        if (node != null) {
            //判断节点是否是叶子节点,因为我们生成霍夫曼树的时候非叶子节点的data域是没有值的，所以可以用这个来判断
            if (node.data == null) {
                //分别走左右子节点，由于最开始有node不为空的判断，所以此处不需要
                //将为上一次拼接完的结果sb传给下一次在此基础上进行拼接
                getCodes(node.left, "0", sb);
                getCodes(node.right, "1", sb);
            } else {
                //当时叶子节点的时说明此事获取该字符编码已经完成。将编码保存到map
                huffmanCodes.put(node.data, sb.toString());
            }
        }
    }

    /**
     * 获取整个霍夫曼树的霍夫曼编码
     * @param root 树的根结点
     */
    private static void getCodes(Node root) {
        //根结点有子节点,保证getCodes三个参数的方法第一次被调用时肯定是有子节点的，而不是出现无子节点
        if (root != null) {
            getCodes(root.left, "0", new StringBuilder());
            getCodes(root.right, "1", new StringBuilder());
        }
    }

    /**
     * 可以通过List 创建对应的赫夫曼树，此处可以参考HuffmanTree类中的构建霍夫曼树的代码
     */
    private static Node createHuffmanTree(List<Node> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            Node left = nodes.get(0);
            Node right = nodes.get(1);
            //因为生成的节点咩有data域所有只需要给出权重就可以
            Node parent = new Node(left.weight + right.weight);
            parent.left = left;
            parent.right = right;
            nodes.add(parent);
            nodes.remove(left);
            nodes.remove(right);
        }
        return nodes.get(0);
    }

    /**
     * 对字符在字符串中出现频率进行统计
     * @param bytes 接收字节数组
     * @return 返回的就是 List 形式   [Node[date=97 ,weight = 5], Node[]date=32,weight = 9]......],
     */
    private static List<Node> getNodes(byte[] bytes) {
        ArrayList<Node> nodeList = new ArrayList<>();
        if (bytes.length <= 0) {
            return nodeList;
        }
        //统计字符权重用于构建霍夫曼树
        Map<Byte, Integer> countMap = new HashMap<>();
        for (byte data : bytes) {
            //如果有这个字符的次数则在原有次数上加1
            if (countMap.containsKey(data)) {
                countMap.put(data, countMap.get(data) + 1);
            } else {
                countMap.put(data, 1);
            }
        }
        for (Map.Entry<Byte, Integer> entry : countMap.entrySet()) {
            nodeList.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodeList;
    }
}

/**
 * 定义节点类,存放要编码的字符和其权重
 * 为了让Node 对象持续排序Collections集合排序
 */
class Node implements Comparable<Node> {
    //节点权值
    public int weight;
    public Node left;
    public Node right;
    //节点对应的要编码的字符
    public Byte data;

    public Node(byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    public Node(int weight) {
        this.weight = weight;
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
        return "Node{" + "weight=" + weight + ", data=" + data + '}';
    }

    @Override
    public int compareTo(Node o) {
        //表示从小到大排序
        return this.weight - o.weight;
    }
}
