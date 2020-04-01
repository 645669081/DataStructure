package com.atguigu.huffmancode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:w_liangwei
 * date:2020/3/31
 * Description: 实现了一个霍夫曼编码并对文件或消息的压缩和解压缩
 * 霍夫曼编码，一种前缀编码，可实现无损压缩。使用霍夫曼树可达到发送频率高的字符编码短，频率小的字符编码长
 * 有多个相同的权值时，构建霍夫曼树过程中新生成的父节点的权值如果和原有的节点的权值相同，那父节点可能的排序到相同大小的任何位置，所以生成的霍夫曼树可能不一样，但WPD是一样的最小的。
 * 如2，4，6，6，6，取出2和4生成6可能放到原有2个6的中间或者两边，导致最终生成的霍夫曼树就可能不一样
 *
 * 原码：最高位表示符号位 "1"为负、"0"为正，其他位为正常二进制形式。
 * 反码：正数的反码即原码本身；负数则在原码的基础上，除符号位之外的其他各位置反。
 * 补码：正数的补码即原码本身；负数则在反码基础上+1。
 * 计算机中所有二进制的操作都是通过补码进行
 * 正数：3 = 0000 0011[原 | 反 | 补]
 * 负数：-3 = 1000 0011[原] = 1111 1100[反] = 1111 1101[补]
 */
public class HuffmanCode {

    //存放霍夫曼编码
    static Map<Byte, String> huffmanCodes = new HashMap<Byte,String>();

    static String str;

    public static void main(String[] args) throws UnsupportedEncodingException {
//        String content = "i like like like java do you like a java";
//        byte[] contentBytes = content.getBytes();
//        System.out.println("未压缩的长度：" + contentBytes.length);

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

        //测试二进制字符串经过转换后能否还原到和原来一样
//        testConvert("10101000");



//        byte[] bytes = huffmanZip(contentBytes);
//        System.out.println("压缩后长度：" + bytes.length);

        //发送编码后的byte数组，实际使用时这么做

        /*
          .解码
           1.先将编码后的字节数组转为原先的二进制字符串
           2.根据得到的编码表将二进制字符串翻译为可见文字
         */
//        byte[] sourceBytes = decode(huffmanCodes, bytes);
//        String receiveContent = new String(sourceBytes);
//        System.out.println("原来的字符串=" + receiveContent);
//        System.out.println("与发送内容是否相同：" + content.equals(receiveContent));

        //测试toBinaryString方法返回的二进制位数问题
//        String s1 = Integer.toBinaryString(0);
//        String s2 = Integer.toBinaryString(-3);
        //转换后总共本应该是32位二进制构成的int，但是正数会省略前边是0的部分，而负数由于首位为1代表符号位则不会
//        System.out.println(1);
//        System.out.println(-1);


        //测试文件压缩
//        String src = "d:/timg.jpg";
//        String dst = "d:/111.jpg";
//        zipFile(src, dst);
//        unZipFile(dst, "d:/222.jpg");
    }

    /**
     * 完成对压缩文件的解压
     * @param zipFile 准备解压的文件
     * @param dstFile 将文件解压到哪个路径
     */
    public static void unZipFile(String zipFile, String dstFile) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(zipFile);
            objectInputStream = new ObjectInputStream(fileInputStream);
            //读取顺序需和写入顺序一致
            byte[] huffmanZip = (byte[]) objectInputStream.readObject();
            Map<Byte, String> codeMap = (Map<Byte, String>) objectInputStream.readObject();
            byte[] unzipBytes = decode(codeMap, huffmanZip);
            fileOutputStream = new FileOutputStream(dstFile);
            fileOutputStream.write(unzipBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param srcFile 你传入的希望压缩的文件的全路径
     * @param dstFile 我们压缩后将压缩文件放到哪个目录
     */
    public static void zipFile(String srcFile, String dstFile) {
        FileInputStream inputStream = null;
        ObjectOutputStream objectOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = new FileInputStream(srcFile);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            byte[] huffmanZip = huffmanZip(bytes);
            //将压缩后的字节和码表写入文件,使用objectOutputStream方便写入。直接序列化对象
            fileOutputStream = new FileOutputStream(dstFile);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(huffmanZip);
            objectOutputStream.writeObject(huffmanCodes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                objectOutputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 测试二进制字符串转int----->强转byte----->转换到int---->还原到二进制字符串的过程
     * 即看一下最后获得到的字符串是否与原先的二进制字符串一致，由于int值位数比byte多，而byte只有8位，所以自取末尾的8位比较
     * @param binaryStr 最初的二进制字符串
     */
    public static void testConvert(String binaryStr) {
        System.out.println("=========================================================");
        System.out.println("要转换的二进制字符串是：" + binaryStr);
        int parseInt = Integer.parseInt(binaryStr, 2);
        Byte a = (byte) parseInt;
        System.out.println("转换后的int：" + parseInt);
        System.out.println("转换后的byte：" + a);
        int b = a.intValue();
        System.out.println("byte强转到int后：" + b);
        String binaryString = Integer.toBinaryString(b);
        System.out.println("int转为二进制后：" + binaryString);
        String substring;
        if (binaryString.length() - 8 < 0) {
            substring = binaryStr;
        } else {
            substring = binaryString.substring(binaryString.length() - 8);
        }
        System.out.println("截取后8位的二进制值：" + substring);
        System.out.println("是否与原先二进制字符串相同：" + substring.equals(binaryStr));
    }

    /**
     * 将一个byte 转成一个二进制的字符串, 如果看不懂，可以参考我讲的Java基础 二进制的原码，反码，补码
     * @param b 传入的 byte
     * @param flag 标志是否需要补高位如果是true ，表示需要补高位，如果是false表示不补, 如果是最后一个字节，无需补高位
     * @return 是该b 对应的二进制的字符串，（注意是按补码返回）
     */
    private static String byteToBitString(boolean flag, byte b) {
        //与编码时的逆顺序，先将byte转为int，再把int转为二进制
        int temp = (int)b;
        //在处理负数时由于最高位是符号位1所以返回的是完整的32位，但是在处理正数时由于符号位为0，前边全部都是0填充，被省略。直接从补码不为0的位置开始返回。
        //所以这里需要对正数进行处理，使其返回完整的32位，而处理时还不能影响到最后边的补码，所以改变符号位就是最好的
        if (temp >= 0 && flag) {
            //按位或 256  1 0000 0000  | 0000 0001 => 1 0000 0001。相应位置有值为1则最终结果对应位置也为1
            temp |= 256;
        }
        //这里得到的是二进制的补码
        String binaryString = Integer.toBinaryString(temp);
        //判断生成的二进制长度是否满足截取要求
        String result;
        if (binaryString.length() - 8 < 0) {
            result = binaryString;
        } else {
            result = binaryString.substring(binaryString.length() - 8);
        }
        return result;
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
        for (int i = 0; i < huffmanBytes.length; i++) {
            //如果是最后一个byte就不需要补位，因为我们当初对zip方法生成的霍夫曼编码后的二进制字符串经过每8位切割为1个byte时，最后一次切割的位数是不足8位的，此时还原为2进制字符串时不需要
            //补位，补位反而和原来的字符串不对应
            boolean flag = huffmanBytes.length -1 == i;//判断是否处理的是最后一位，最后一位无需补高位
            bitString.append(byteToBitString(!flag, huffmanBytes[i]));
            //验证对当前字节的处理是否和预期一致
//            boolean startsWith = str.startsWith(bitString.toString());
//            if (!startsWith) {
//                System.out.println(huffmanBytes[i]);
//                System.out.println(startsWith);
//            }
        }
//        System.out.println("还原后的二进制字符串：" + bitString);
//        System.out.println("长度是" + bitString.length());
//        System.out.println("还原后的二进制传是否与原来相同：" + bitString.toString().equals(str));
        //构建一个反向映射的map便于解码时使用
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> byteStringEntry : huffmanCodes.entrySet()) {
            map.put(byteStringEntry.getValue(), byteStringEntry.getKey());
        }
        //从编码表中拿到编码对应的字母
        int index = 0;
        List<Byte> asciiList = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        //遍历字符串找对应的字符,我们当初存储编码表时是以byte类型的字符的ascii码为key，存到了编码表的map中。此时从编码表中取出时还应该是byte类型的ascii码
        while (index < bitString.length()) {
            //该字符解码表中不包含则继续移动到下一位。进来先去拼接，然后去根据拼接的路径取值。如果是先取后拼，每次都获取的是上一次拼接后的结果，那么就会少处理一个字符
            temp.append(bitString.charAt(index));
            Byte aByte = map.get(temp.toString());
            if (aByte != null) {
                asciiList.add(map.get(temp.toString()));
                //清空拼接内容进行下一次拼接
                temp.delete(0, temp.length());
            }
            //不管是获取成功或失败都需要移动索引到下一个拼接位置
            index++;
        }
        //创建数组时写入的是容量，不是索引
        byte[] contentBytes = new byte[asciiList.size()];
        for (int i = 0; i < asciiList.size(); i++) {
            contentBytes[i] = asciiList.get(i);
        }
        return contentBytes;
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
        str = builder.toString();
        System.out.println("霍夫曼编码后的二进制字符串是：" + builder);
        System.out.println("长度是" + builder.length());
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
                byteStr = builder.substring(j);
            } else {
                byteStr = builder.substring(j, j + 8);
            }
            //验证加入的byte值是否最后可以恢复到二进制字符串
//            testConvert(byteStr);
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
