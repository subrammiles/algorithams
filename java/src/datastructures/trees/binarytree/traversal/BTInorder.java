package datastructures.trees.binarytree.traversal;

import java.util.Stack;

import datastructures.trees.binarytree.MyBinaryTree;

public class BTInorder {
	public  static void inOrder(MyBinaryTree root)
    {
        if (root != null) {
            inOrder(root.left);
            System.out.print(root.value + " ");
            inOrder(root.right);
        }
    }
	public static void inOrderTraversal(MyBinaryTree root)
    {
      Stack<MyBinaryTree> stack = new Stack();
      MyBinaryTree curr = root;
      while (!stack.empty() || curr != null)
      {
        if (curr != null)
        {
          stack.push(curr);
          curr = curr.left;
        }
        else
        {
          curr = stack.pop();
          System.out.print(curr.value + " ");
          curr = curr.right;
        }
      }
    }
}
