package com.atguigu.hashtab;

import java.util.Scanner;

/**
 * author:w_liangwei
 * date:2020/3/27
 * Description: 模拟一个哈希表
 * 使用数组 + 链表的结构
 */
public class HashTabDemo {
    public static void main(String[] args) {
        //创建哈希表
        HashTab hashTab = new HashTab(7);

        //写一个简单的菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("add:  添加雇员");
            System.out.println("list: 显示雇员");
            System.out.println("find: 查找雇员");
            System.out.println("exit: 退出系统");

            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();
                    //创建 雇员
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.showHashTable();
                    break;
                case "find":
                    System.out.println("请输入要查找的id");
                    id = scanner.nextInt();
                    Emp emp1 = hashTab.findEmpById(id);
                    System.out.println(emp1);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    break;
            }
        }
    }
}

/**
 * 创建HashTab 管理多条链表
 */
class HashTab {
    private EmpLinkedList[] hashTable;
    private int size;

    public HashTab(int size) {
        this.size = size;
        hashTable = new EmpLinkedList[size];
    }

    /**
     * 将一个员工挂到哈希计算过的链表末尾
     */
    public void add(Emp emp) {
        int hashVal = getHash(emp.id);
        //这个链上一个员工都没有
        if (hashTable[hashVal] == null) {
            //初始化这条链
            hashTable[hashVal] = new EmpLinkedList();
        }
        hashTable[hashVal].add(emp);
    }

    public void showHashTable() {
        if (size == 0) {
            return;
        }
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] == null) {
                System.out.println("第" + (i + 1) + "条链表为空");
                continue;
            }
            System.out.print("第" + (i + 1) + "条链的数据是:");
            hashTable[i].list();
        }
    }

    /**
     * 采用取模的方式计算哈希值
     */
    public int getHash(int empId) {
        return empId % size;
    }

    /**
     * 根据id查找员工
     * @param id
     * @return
     */
    public Emp findEmpById(int id) {
        int hash = getHash(id);
        Emp current = hashTable[hash].head;
        while (current != null && current.id != id) {
            current = current.next;
        }
        return current;
    }
}

/**
 * 创建EmpLinkedList ,表示链表
 */
class EmpLinkedList {
    public Emp head;

    public void add(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }
        Emp current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = emp;
    }

    public void list() {
        if (head == null) {
            return;
        }
        Emp current = head;
        while (current != null) {
            System.out.print(current);
            System.out.print(",");
            current = current.next;
        }
        System.out.println();
    }
}


/**
 * 表示一个雇员,用于构建链表
 */
class Emp {
    public int id;
    public String name;
    public Emp next; //next 默认为 null
    public Emp(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}