package com.atguigu.linklist;

/**
 * author:w_liangwei
 * date:2020/3/19
 * Description:
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
// 测试
        System.out.println("双向链表的测试");
        // 先创建节点
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");
        // 创建一个双向链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero4);
        doubleLinkedList.addByOrder(hero3);
        doubleLinkedList.addByOrder(hero2);

        doubleLinkedList.list();

        // 修改
//        HeroNode2 newHeroNode = new HeroNode2(4, "公孙胜", "入云龙");
//        doubleLinkedList.updateNode(newHeroNode);
//        System.out.println("修改后的链表情况");
//        doubleLinkedList.list();

        // 删除
//        doubleLinkedList.deleteNode(3);
//        System.out.println("删除后的链表情况~~");
//        doubleLinkedList.list();
    }
}

class DoubleLinkedList {
    // 先初始化一个头节点, 头节点不要动, 不存放具体的数据
    private HeroNode2 head = new HeroNode2(0, "", "");

    // 返回头节点
    public HeroNode2 getHead() {
        return head;
    }

    /**
     * 添加节点
     * @param node
     */
    public void add(HeroNode2 node) {
        //从头结点找最后一个节点的位置
        HeroNode2 temp = getHead();
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
        node.pre = temp;
    }

    /**
     * 插入node时按照节点no排序插入
     */
    public void addByOrder(HeroNode2 node) {
        //取temp为头结点是为了使用temp.next判断是否已经到达链表结尾后，获取到的当前被遍历节点temp不为null后边进行节点插入时比较好处理。不用关注更多的null的情况
        //而如果使用head.next作为被遍历节点，那么temp == null时得到的temp已经是null这个节点是没有价值的，需要考虑更多的null的情况
        HeroNode2 temp = getHead();
        //表示当前no的节点是否已经存在，true为存在，false不存在
        boolean flag = false;
        while (true) {
            //从头节点的next开始遍历所有元素，直至next为null表示到达链表的末尾
            if (temp.next == null) {
                break;
            }
            //比较当前遍历节点和待插入节点的no，只要比当前比当前遍历的节no小就说明找到了插入位置，即插入到当前被遍历节点的前边
            if (temp.next.no > node.no) {
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
            //将节点插入,必须保证代码的顺序，不然会造成节点丢失
            //待插入节点next指向当前被遍历节点的next
            node.next = temp.next;
            //当前被遍历节点的next指向待插入节点
            temp.next = node;
            //待插入节点的pre指向当前被遍历节点
            node.pre = temp;
            //如果当前被遍历节点是最后一个节点那么它的next就是null，由于上边的赋值导致node.next也会是null，此时不能对null的pre进行赋值。因为待插入节点自己就是最后一个节点
            if (node.next != null) {
                node.next.pre = node;
            }
        }
    }

    /**
     * 根据no更新节点信息,和单向链表的修改代码一致
     * @param node
     */
    public void updateNode(HeroNode2 node) {
        if (getHead().next == null) {
            System.out.println("链表为空");
            return;
        }
        //表示该编号的节点是否存在，true存在，false不存在
        boolean flag = false;
        HeroNode2 temp = getHead().next;
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
     * 1 对于双向链表，我们可以直接找到要删除的这个节点
     * 2 找到后，自我删除即可
     */
    public void deleteNode(int no) {
        if (getHead().next == null) {
            System.out.println("链表为空");
            return;
        }
        boolean flag = false;
        //在删除时被遍历的节点就是要删除节点
        HeroNode2 temp = getHead().next;
        while (true) {
            if (temp == null) {
                //已经对所有节点遍历完成
                break;
            }
            if (temp.no == no) {
                flag = true;
                break;
            }
            //被遍历节点后移
            temp = temp.next;
        }
        if (flag) {
            //将被删除节点的前后节点进行连接即实现删除该节点
            temp.pre.next = temp.next;
            //如果被删除的节点恰好是最后一个节点那么temp.next就是null，所以也就不能挂next的pre，因为next都不存在
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.printf("编号为%d的节点不存在");
        }
    }


    /**
     * 打印链表中的全部节点
     */
    public void list() {
        //如果头结点的next为null那么链表为空
        if (this.head.next == null) {
            System.out.println("链表为空");
            return;
        }
        //设置遍历的起始点为第一个元素节点
        HeroNode2 temp = this.head.next;
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

class HeroNode2 {
    public int no;
    public String name;
    public String nickName;
    public HeroNode2 next;
    public HeroNode2 pre;

    public HeroNode2(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + ", nickname=" + nickName + "]";
    }
}
