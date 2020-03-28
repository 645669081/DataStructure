package com.atguigu.tree;



/**
 * author:w_liangwei
 * date:2020/3/27
 * Description: 构建二叉树并遍历，该二叉树不保证元素插入顺序
 *
 * 前中后的遍历说的是根结点的输出顺序
 *
 */
public class BinaryTreeDemo {

    public static void main(String[] args) {
        //先需要创建一颗二叉树
        BinaryTree binaryTree = new BinaryTree();
        //创建需要的结点
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");

        HeroNode node6 = new HeroNode(6, "林冲1111");
        HeroNode node7 = new HeroNode(7, "关胜111");

        //说明，我们先手动创建该二叉树，后面我们学习递归的方式创建二叉树
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);
        binaryTree.setRoot(root);

        node5.setLeft(node6);
        node5.setRight(node7);

        System.out.println("前序遍历"); // 1,2,3,5,4
		binaryTree.preOrder();

//		System.out.println("中序遍历");
//		binaryTree.infixOrder(); // 2,1,5,3,4

//        System.out.println("后序遍历");
//		binaryTree.postOrder(); // 2,5,4,3,1


//        System.out.println("====================================================");
//        System.out.println("前序查找" + binaryTree.preOrderSearch(5));//4次
//        System.out.println("中序查找" + binaryTree.infixOrderSearch(5));//3次
//        System.out.println("后序查找" + binaryTree.postOrderSearch(5));//2次

        //删除节点
        binaryTree.delNode1(3);
        System.out.println("删除节点后");
        binaryTree.preOrder();
    }
}




//定义BinaryTree 二叉树
class BinaryTree {
    private HeroNode root;

    public HeroNode getRoot() {
        return root;
    }

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    /**
     * 根左右
     */
    public void preOrder() {
        if (root == null) {
            System.out.println("树中没有节点");
            return;
        }
        root.preOrder();
    }

    /**
     * 左根右
     */
    public void infixOrder() {
        if (root == null) {
            System.out.println("树中没有节点");
            return;
        }
        root.infixOrder();
    }

    /**
     * 左右根
     */
    public void postOrder() {
        if (root == null) {
            System.out.println("树中没有节点");
            return;
        }
        root.postOrder();
    }

    //前序查找
    public HeroNode preOrderSearch(int no) {
        if(root != null) {
            return root.preOrderFind(no);
        } else {
            return null;
        }
    }
    //中序查找
    public HeroNode infixOrderSearch(int no) {
        if(root != null) {
            return root.infixOrderFind(no);
        }else {
            return null;
        }
    }
    //后序查找
    public HeroNode postOrderSearch(int no) {
        if(root != null) {
            return this.root.postOrderFind(no);
        }else {
            return null;
        }
    }

    //删除结点
    public void delNode(int no) {
        if(root != null) {
            //当前节点是要删除的节点，这个操作只能在这个方法中删除，不然在deleteNode方法中改变的是地址值不能置为null
            if (root.getNo() == no) {
                root = null;
                return;
            }
            root.deleteNode(no, root);
        }else{
            System.out.println("空树，不能删除~");
        }
    }

    //删除结点,不删除子树
    public void delNode1(int no) {
        if(root != null) {
            //目前无法删除根节点，因为删除了根节点后，提升了左节点，但是右侧的子树挂到哪里呢
//            if (root.getNo() == no) {
//                root = null;
//                return;
//            }
            root.deleteNode(no);
        }else{
            System.out.println("空树，不能删除~");
        }
    }
}



//先创建HeroNode 结点
class HeroNode {
    private int no;
    private String name;
    private HeroNode left; //默认null
    private HeroNode right; //默认null


    private static int preCount;
    private static int infixCount;
    private static int postCount;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + "]";
    }

    //前序遍历，只要左右两侧还有子节点就一直递归往下找，直到只剩下自己然后打印出来
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    //后序遍历
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();;
        }
        System.out.println(this);
    }

    //前序遍历查找指定id节点，如果将比较次数统计的语句放到最开始，那么在遍历每个节点的时候就会去统计，而实际需要的是只有在与查找值发生实际比较时才去统计
    public HeroNode preOrderFind(int no) {
        System.out.println("前序查找次数是：" + ++preCount);
        //是当前节点直接返回
        if (this.getNo() == no) {
            return this;
        }
        HeroNode result = null;
        if (this.left != null) {
            result = this.left.preOrderFind(no);
        }
        //从左节点找到结果直接返回
        if (result != null) {
            return result;
        }
        //右节点查找完毕后不论是否有都要返回
        if (this.right != null) {
            result = this.right.preOrderFind(no);
        }
        return result;
    }

    //中序遍历查找指定id节点
    public HeroNode infixOrderFind(int no) {
        HeroNode result = null;
        if (this.left != null) {
            result = this.left.infixOrderFind(no);
        }
        //向左查找找到了直接返回
        if (result != null) {
            return result;
        }
        System.out.println("中序查找次数是：" + ++infixCount);
        if (this.getNo() == no) {
            return this;
        }
        //向右查找不管是否找到都需要返回
        if (this.right != null) {
            return this.right.infixOrderFind(no);
        }
        return result;
    }

    public HeroNode postOrderFind(int no) {
        HeroNode result = null;
        if (this.left != null) {
            result = this.left.postOrderFind(no);
        }
        //判断向左查找是否找到，找到就直接返回
        if (result != null) {
            return result;
        }
        //向右查找
        if (this.right != null) {
            result = this.right.postOrderFind(no);
        }
        if (result != null) {
            return result;
        }
        System.out.println("后序查找次数是：" + ++postCount);
        if (this.getNo() == no) {
            return this;
        }
        return result;
    }

    /**
     * 删除节点并删除该节点的子树
     */
    public void deleteNode(int no, HeroNode root) {
        //此处使用root.left和root.right能够在保证方法内对象root所指的地址是不变的情况下对其属性进行修改。
        //如果直接去对root赋值就会造成该变量指向了一个新的地址值，传来的对象还在没有变化。即改变属性会变化，改变地址值不会引发变化
        //所以当root是要删除的节点时就无法在该方法内置为null进行删除，所以提到二叉树的类中进行判断删除
        if (root == null) {
            return;
        }
        //已经找到了删除左节点
        if (root.left != null && root.left.getNo() == no) {
            root.left = null;
            return;
        }
        //已经找到了删除右节点
        if (root.right != null && root.right.getNo() == no) {
            root.right = null;
            return;
        }
        //向左遍历查找节点进行删除
        deleteNode(no, root.left);
        //向右遍历查找节点进行删除
        deleteNode(no, root.right);
    }

    /**
     * 删除节点不删除子树，如果只有一个子节点直接代替父节点，如果有两个子节点那么左子节点代替父节点
     */
    public void deleteNode(int no) {
        //删除的是左节点
        if (this.left != null && this.left.no == no) {
            HeroNode delLeft = this.left;
            //要删除的节点没有子节点直接进行删除
            if (delLeft.left == null && delLeft.right == null) {
                this.left = null;
            } else if (delLeft.left != null && delLeft.right != null) {
                //要删除的节点左右节点都有
                //往下递归左节点，直到出现该节点没有挂满，那么将右节点挂到该节点上
                //创建临时变量表示被删除节点的左节点的左右节点，被删除节点的右节点是不能找的，因为它是要挂到左节点底下的
                HeroNode left = delLeft.left;
                while (left.left != null && left.right != null) {
                    //这些节点都挂满了直接往下走
                    left = left.left;
                }
                //找到了可以挂右节点的位置
                if (left.left == null) {
                    left.left = delLeft.right;
                } else {
                    left.right = delLeft.right;
                }
                //将被删除节点的左节点升级为父节点
                this.left = delLeft.left;
            } else if (delLeft.left != null){
                //被删除节点的左节点不为null
                this.left = delLeft.left;
            } else {
                //被删除节点的右节点不为null
                this.left = delLeft.right;
            }
        }
        //删除的是右节点
        if (this.right != null && this.right.no == no) {
            HeroNode delRight = this.right;
            //要删除的节点没有子节点直接进行删除
            if (delRight.left == null && delRight.right == null) {
                this.right = null;
            } else if (delRight.left != null && delRight.right != null) {
                //要删除的节点左右节点都有
                //往下递归左节点，直到出现该节点没有挂满，那么将右节点挂到该节点上
                //创建临时变量表示被删除节点的左节点的左右节点，被删除节点的右节点是不能找的，因为它是要挂到左节点底下的
                HeroNode left = delRight.left;
                while (left.left != null && left.right != null) {
                    //这些节点都挂满了直接往下走
                    left = left.left;
                }
                //找到了可以挂右节点的位置
                if (left.left == null) {
                    left.left = delRight.right;
                } else {
                    left.right = delRight.right;
                }
                //将被删除节点的左节点升级为父节点
                this.right = delRight.left;
            } else if (delRight.left != null){
                //被删除节点的左节点不为null
                this.right = delRight.left;
            } else {
                //被删除节点的右节点不为null
                this.right = delRight.right;
            }
        }
        //递归左右节点
        if (this.getLeft() != null) {
            this.left.deleteNode(no);
        }
        if (this.getRight() != null) {
            this.right.deleteNode(no);
        }
    }
}