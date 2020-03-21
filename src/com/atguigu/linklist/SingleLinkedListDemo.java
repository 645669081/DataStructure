package com.atguigu.linklist;

import java.util.Stack;

/**
 * author:w_liangwei
 * date:2020/3/18
 * Description:
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
//进行测试
        //先创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        HeroNode hero5 = new HeroNode(5, "天勇星", "大刀关胜");
        HeroNode hero6 = new HeroNode(6, "天猛星", "霹雳火秦明");
        HeroNode hero6_1 = new HeroNode(6, "天猛星111", "霹雳火秦明111");
        HeroNode hero7 = new HeroNode(7, "天威星", "双鞭呼延灼");
        HeroNode hero8 = new HeroNode(8, "天富星", "扑天雕李应");

        //创建要给链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        SingleLinkedList singleLinkedList1 = new SingleLinkedList();
//        //加入
//        singleLinkedList.add(hero1);
//        singleLinkedList.add(hero4);
//        singleLinkedList.add(hero2);
//        singleLinkedList.add(hero3);

        //加入按照编号的顺序
//        singleLinkedList.addByOrder(hero1);
//        singleLinkedList.addByOrder(hero4);
//        singleLinkedList.addByOrder(hero2);
//        singleLinkedList.addByOrder(hero3);
//        singleLinkedList.list();

//        System.out.println("反转单链表~~");
//		reversetList(singleLinkedList);
//		singleLinkedList.list();

//        System.out.println("使用栈逆序打印后====");
//        reversePrint(singleLinkedList);

//        HeroNode newHeroNode = new HeroNode(2, "小卢", "玉麒麟~~");
//        singleLinkedList.updateNode(newHeroNode);
//        System.out.println("修改后的链表情况~~");
//        singleLinkedList.list();

        //删除一个节点
//        singleLinkedList.deleteNode(1);
//        singleLinkedList.deleteNode(4);
//        System.out.println("删除后的链表情况~~");
//        singleLinkedList.list();

        //测试一下 求单链表中有效节点的个数
//        System.out.println("有效的节点个数=" + singleLinkedList.getLength());

        //测试一下看看是否得到了倒数第K个节点
//        HeroNode res = findLastIndexNode(singleLinkedList, 1);
//        System.out.println("res=" + res);


        //合并两个有序链表为新的有序链表
        singleLinkedList.add(hero3);
        singleLinkedList.add(hero4);
        singleLinkedList.add(hero6);
        singleLinkedList.add(hero8);

        singleLinkedList.list();
        System.out.println("========================");

        singleLinkedList1.add(hero1);
        singleLinkedList1.add(hero2);
        singleLinkedList1.add(hero5);
        singleLinkedList1.add(hero6_1);
        singleLinkedList1.add(hero7);

        singleLinkedList1.list();
        System.out.println("合并后=================");
        SingleLinkedList linkedList = mergeLinkList(singleLinkedList1, singleLinkedList);
        linkedList.list();
    }

    /**
     * 合并两个有序的单链表,即合并后按照编号是有序的
     * 1.同时遍历2个链表，并创建一个新的链表
     * 2.比较节点编号大小确定前后顺序
     * 3.将节点添加到新的链表
     *
     */
    public static SingleLinkedList mergeLinkList(SingleLinkedList linkedList1, SingleLinkedList linkedList2) {
        HeroNode headNode1 = linkedList1.headHero;
        HeroNode headNode2 = linkedList2.headHero;
        if (headNode1.next ==null || headNode2.next == null) {
            System.out.println("要合并的两个链表不能为空");
            return null;
        }
        SingleLinkedList linkedList = new SingleLinkedList();
        //temp的next是下一个被插入节点的位置
        HeroNode temp = linkedList.headHero;
        //两个链表上当前被拿来遍历的两个节点
        HeroNode current1 = headNode1.next;
        HeroNode current2 = headNode2.next;
        //如果有一个节点为null时说明有一个链表已经遍历插入完毕
        while (current1 != null && current2 != null) {
            //分别对两个链表中的被遍历节点进行对比，决定将哪个节点放入到新的链表
            if (current2.no > current1.no) {
                temp.next = current1;
                current1 = current1.next;
            } else {
                temp.next = current2;
                current2 = current2.next;
            }
            temp = temp.next;
        }
        //对某一个链表有剩余长度进行处理，直接放到新的链表后
        if (current1 != null) {
            temp.next = current1;
        }
        if (current2 != null) {
            temp.next = current2;
        }
        return linkedList;
    }


    /**
     * 单链表反转
     * 1.遍历摘取每一个节点
     * 2.重新创建一个头结点将摘取下的节点挂到该头结点
     * 3.然后采用头插法，将每一个要挂的元素都放到新的头结点后
     * 4.将新的头结点的next赋值给原头结点的next
     * @param linkedList
     */
    public static void reversetList(SingleLinkedList linkedList) {
        //为空或只有一个元素时无法反转
        if (linkedList.headHero.next == null || linkedList.headHero.next.next == null) {
            System.out.println("链表为空或只有一个节点");
            return;
        }
        //被遍历的当前节点
        HeroNode temp = linkedList.headHero.next;
        //用来保存当前被遍历节点的next节点，防止当执行temp.next = reverseHead.next时造成了原来的节点链已经断掉无法继续遍历
        HeroNode next = null;
        //创建新的head头用于挂倒序的节点
        HeroNode reverseHead = new HeroNode(0, "", "");
        while (temp != null) {
            //头插法将取下的节点挂到新的头结点后
            //保存当前节点的下一个节点，防止对当前节点next赋值造成断链从而无法完成后续遍历
            next = temp.next;
            temp.next = reverseHead.next;
            reverseHead.next = temp;
            //节点后移
            temp = next;
        }
        //为倒序后的链表更换为原来的头结点
        linkedList.headHero.next = reverseHead.next;
    }

    /**
     * 利用栈对单链表逆序打印
     */
    public static void reversePrint(SingleLinkedList linkedList) {
        if (linkedList.headHero.next == null) {
            System.out.println("链表为空");
        }
        //遍历节点并压栈
        Stack<HeroNode> stack = new Stack<>();
        HeroNode temp = linkedList.headHero.next;
        while (temp != null) {
            stack.push(temp);
            temp = temp.next;
        }
        //逐一出栈打印，直到栈为空
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    /**
     * 查找链表中倒数第K个节点
     * 1.获取链表长度
     * 2.通过length-index确定倒数节点的正数位置
     * 3.从链表的第一个开始遍历 (length-index)个，就可以得到
     * @param index  要查找的是倒数第几个节点
     * @return
     */
    public static HeroNode findLastIndexNode(SingleLinkedList linkedList, int index) {
        if (linkedList.headHero.next == null) {
            System.out.println("链表为空");
            return null;
        }
        //该元素的正数的位置是链表节点个数-倒数位置,也就是length-index就是被查找节点的正数位置
        int length = linkedList.getLength();
        //判断该位置是否超出链表范围
        if (index <= 0 || index > length) {
            return null;
        }
        HeroNode temp = linkedList.headHero.next;
        //表示从第一个节点走length-index次时，此时的temp就是被查找节点。
        //被遍历的第一个不能包含头结点
        for (int i = 0; i < length - index; i++) {
            temp = temp.next;
        }
        return temp;
    }
}

//构建链表管理英雄
class SingleLinkedList {
    //创建头结点
    public HeroNode headHero = new HeroNode(0, "", "");

    /**
     * 添加节点
     * @param node
     */
    public void add(HeroNode node) {
        //从头结点找最后一个节点的位置
        HeroNode temp = headHero;
        while (true) {
            //如果当前元素的next是null，那么当前元素就是链表的最后一个元素
            if (temp.next == null) {
                break;
            }
            //不是最后一个元素就后移一个node继续找
            temp = temp.next;
        }
        //将要添加节点放到最后一个元素的下一个位置
        temp.next = node;
    }

    /**
     * 插入node时按照节点no排序插入
     */
    public void addByOrder(HeroNode node) {
        HeroNode temp = headHero;
        //表示当前no的节点是否已经存在，true为存在，false不存在
        boolean flag = false;
        while (true) {
            //从头节点的next开始遍历所有元素，直至next为null表示到达链表的末尾
            if (temp.next == null) {
                break;
            }
            //因为插入的位置是一个元素的next位置，所以要和当前遍历元素的next元素进行编号比较
            if (temp.next.no > node.no) {
                //后一个元素编号比当前插入编号大，表示可以插到后一个元素之前，也就是当前被遍历元素的next来顶替原来next元素的位置
                //已经找到了添加位置，退出遍历
                break;
            }
            if (temp.next.no == node.no) {
                //该编号的节点已经存在
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            System.out.println("该编号的英雄已经存在，请不要重复添加");
        } else {
            node.next = temp.next;
            temp.next = node;
        }
    }

    /**
     * 根据no更新节点信息
     * @param node
     */
    public void updateNode(HeroNode node) {
        if (headHero.next == null) {
            System.out.println("链表为空");
            return;
        }
        //表示该编号的节点是否存在，true存在，false不存在
        boolean flag = false;
        HeroNode temp = headHero.next;
        while (true) {
            //如果当前遍历节点为null说明全部节点已经遍历完成
            if (temp == null) {
                break;
            }
            //找到了该编号的节点
            if (temp.no == node.no) {
                flag = true;
                break;
            }
            //节点后移继续遍历
            temp = temp.next;
        }
        if (flag) {
            temp.name = node.name;
            temp.nickName = node.nickName;
        } else {
            System.out.printf("编号为%d的节点不存在", node.no);
        }
    }

    /**
     * 删除该no的节点
     * @param no
     */
    public void deleteNode(int no) {
        if (headHero.next == null) {
            System.out.println("链表为空");
            return;
        }
        boolean flag = false;
        HeroNode temp = headHero;
        while (true) {
            if (temp.next == null) {
                //已经对所有节点遍历完成
                break;
            }
            //当前被遍历节点的下一个节点no和要删除的相同说明找到了要被删除的节点
            if (temp.next.no == no) {
                flag = true;
                break;
            }
            //被遍历节点后移
            temp = temp.next;
        }
        if (flag) {
            //将被删除元素的前一个的节点的next  指向  被删除元素的下一个节点，也就是被删除元素的next
            temp.next = temp.next.next;
        } else {
            System.out.printf("编号为%d的节点不存在");
        }
    }

    public int getLength() {
        if (headHero.next == null) {
            System.out.println("链表为空");
        }
        int length = 0;
        HeroNode temp = headHero;
        while (temp.next != null) {
            //统计节点个数
            length++;
            //后移节点
            temp = temp.next;
        }
        return length;
    }



    /**
     * 打印链表中的全部节点
     */
    public void list() {
        //如果头结点的next为null那么链表为空
        if (this.headHero.next == null) {
            System.out.println("链表为空");
            return;
        }
        //设置遍历的起始点为第一个元素节点
        HeroNode temp = this.headHero.next;
        while (true) {
            //当遍历的节点为null时那么已经到达了链表的结尾
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            //后移到下一个要遍历的节点
            temp = temp.next;
        }
    }
}


/**
 * 定义链表中的节点对象
 */
class HeroNode {
    public int no;
    public String name;
    public String nickName;
    public HeroNode next;

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    //为了显示方法，我们重新toString
    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + ", nickname=" + nickName + "]";
    }
}

