package com.atguigu.linklist;

/**
 * author:w_liangwei
 * date:2020/3/19
 * Description:
 */
public class Josepfu {
    public static void main(String[] args) {
        // 测试一把看看构建环形链表，和遍历是否ok
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5);// 加入5个小孩节点
        circleSingleLinkedList.showBoys();

        //测试一把小孩出圈是否正确
        circleSingleLinkedList.countBoy(1, 2, 5); // 2->4->1->5->3
        String str = "7*2*2-5+1-5+3-3";
    }
}

// 创建一个环形的单向链表
class CircleSingleLinkedList {
    //创建first节点
    public Boy first = null;

    /**
     * 生成一个环形的链表，编号从1到nums
     * @param nums 整个环形链表节点个数
     */
    public void addBoy(int nums) {
        //nums做一个数据校验
        if (nums < 1) {
            System.out.println("nums的值不正确");
            return;
        }
        //为了每次添加节点时不需要遍历到链表的末尾，所以每次将被添加节点的前一个节点设为temp
        Boy current = null;
        for (int i = 1; i <= nums; i ++) {
            Boy boy = new Boy(i);
            //如果是链表的第一个节点，那么需要指定为first，first用来当做循环链表的开头
            if (i == 1) {
                first = boy;
                //第一个节点是first，将循环指针指向自己
                boy.setNext(first);
            } else {
                //添加节点
                current.setNext(boy);
                //形成环
                boy.setNext(first);
            }
            //将被遍历节点移动到新添加节点，下次添加时使用current的next
            current = boy;
        }
    }

    public void showBoys() {
        if (first == null) {
            System.out.println("链表中没有节点");
        }
        Boy current = first;
        while (true) {
            System.out.printf("小孩的编号 %d \n", current.getNo());
            //当遍历到first时代表链表已经又回到了头，完成遍历
            if (current.getNext() == first) {
                break;
            }
            //节点后移
            current = current.getNext();
        }
    }

    /**
     * 数数决定哪个男孩出圈
     * n = 5, 即5个人。
     * k = 3，从第三个开始数
     * m = 2，数2下
     * 1.因为是循环报数，每次出圈男孩相邻的下一个男孩又会从1开始报，所以first节点需要移动
     * 2.为了一个男孩出圈后方便环形的构建，不用去遍历去找最后一个节点，所以给一个指针到最后一个节点
     * 3.由于first随着出圈会发生变化，所以helper指针也需要跟着变化。这样也就满足first的前一个是最后一个元素
     * 4.在报数之前根据要求从第几个小孩开始数来重新定位first和helper指针，即移动k-1次
     * 4.小孩报数时first和helper需要跟着移动m-1次
     * 5.报数完成后first指向的节点就是出圈的节点
     * @param startNo 表示从第几个小孩开始数数
     * @param countNum 表示数几下
     * @param nums 表示最初有多少小孩在圈中
     */
    public void countBoy(int startNo, int countNum, int nums) {
        if (nums == 0 || startNo < 1 || countNum > nums) {
            System.out.println("参数输入有误， 请重新输入");
            return;
        }
        //将helper定位到最后一个节点
        Boy helper = first;
        while (true) {
            if (helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }
        //从第k个孩子开始报数，所以first和helper都要移动k-1次
        for (int i = 0; i < startNo -1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        //由于一直会报数直到圈子里只剩下一个人所以使用死循环
        while (true) {
            //当helper和first一致时说明链表中只剩下一个节点，此时游戏结束
            if (helper == first) {
                break;
            }
            //开始报数，first移动m-1次指向的就是要出圈的节点
            for (int i =0; i < countNum -1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            //打印出圈节点
            System.out.printf("小孩%d出圈\n", first.getNo());
            //删除节点
            first = first.getNext();
            helper.setNext(first);
        }
        //输出圈中最后一个小孩
        System.out.printf("小孩%d出圈\n", first.getNo());
    }
}



// 创建一个Boy类，表示一个节点
class Boy {
    private int no;// 编号
    private Boy next; // 指向下一个节点,默认null

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
