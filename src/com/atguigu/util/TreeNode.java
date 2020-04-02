package com.atguigu.util;

/**
 * author:w_liangwei
 * date:2020/4/2
 * Description:
 */
public interface TreeNode {
    /**
     * 需要打印的信息
     * @return
     */
    String getPrintInfo();

    TreeNode getLeftChild();

    TreeNode getRightChild();
}
