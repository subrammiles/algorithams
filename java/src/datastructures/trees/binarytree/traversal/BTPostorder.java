package datastructures.trees.binarytree.traversal;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import datastructures.trees.binarytree.MyBinaryTree;

public class BTPostorder {
	
	/*1 stack*/
	
	MyBinaryTree root;
    ArrayList<Integer> list = new ArrayList<Integer>();
 
    // An iterative function to do postorder traversal
    // of a given binary tree
    ArrayList<Integer> postOrderIterative(MyBinaryTree node)
    {
        Stack<MyBinaryTree> S = new Stack<MyBinaryTree>();
         
        // Check for empty tree
        if (node == null)
            return list;
        S.push(node);
        MyBinaryTree prev = null;
        while (!S.isEmpty())
        {
        	MyBinaryTree current = S.peek();
 
            /* go down the tree in search of a leaf an if so process it
            and pop stack otherwise move down */
            if (prev == null || prev.left == current ||
                                        prev.right == current)
            {
                if (current.left != null)
                    S.push(current.left);
                else if (current.right != null)
                    S.push(current.right);
                else
                {
                    S.pop();
                    list.add(current.value);
                }
 
                /* go up the tree from left node, if the child is right
                push it onto stack otherwise process parent and pop
                stack */
            }
            else if (current.left == prev)
            {
                if (current.right != null)
                    S.push(current.right);
                else
                {
                    S.pop();
                    list.add(current.value);
                }
                 
                /* go up the tree from right node and after coming back
                from right node process parent and pop stack */
            }
            else if (current.right == prev)
            {
                S.pop();
                list.add(current.value);
            }
 
            prev = current;
        }
 
        return list;
    }
	
	
	/*2 stacks*/
	
 // Two stacks as used in explanation
    static Stack<MyBinaryTree> s1, s2;
 
    static void postOrderIterative2(MyBinaryTree root)
    {
        // Create two stacks
        s1 = new Stack<>();
        s2 = new Stack<>();
 
        if (root == null)
            return;
 
        // push root to first stack
        s1.push(root);
 
        // Run while first stack is not empty
        while (!s1.isEmpty()) {
            // Pop an item from s1 and push it to s2
        	MyBinaryTree temp = s1.pop();
            s2.push(temp);
 
            // Push left and right children of
            // removed item to s1
            if (temp.left != null)
                s1.push(temp.left);
            if (temp.right != null)
                s1.push(temp.right);
        }
 
        // Print all elements of second stack
        while (!s2.isEmpty()) {
        	MyBinaryTree temp = s2.pop();
            System.out.print(temp.value + " ");
        }
    }
	
	
	/*No stacks */
	
	public List<Integer> postorderTraversal(MyBinaryTree root) {
		List<Integer> out = new ArrayList<Integer>();
		if(root == null)
			return out;
		MyBinaryTree dummy = new MyBinaryTree(-1), pre = null;
		dummy.left = root; root = dummy;
		while(root != null){
			if(root.left != null){
				pre = root.left;
				while(pre.right != null && pre.right != root)
					pre=pre.right;
				if(pre.right == null){
					pre.right = root;
					root = root.left;
				}
				else{
					MyBinaryTree node = pre;
					reverse(root.left,pre);
					while(node != root.left){
						out.add(node.value);
						node = node.right;
					}
					out.add(node.value);          // Print again since we are stopping at node=root.left
					reverse(pre,root.left);
					pre.right = null;
					root = root.right;
				}
			}
			else{
				root = root.right;
			}
		}
		return out;
	}
	public void reverse(MyBinaryTree from, MyBinaryTree to){
	    if(from == to)
	        return;
	    MyBinaryTree prev = from, node = from.right;
	    while(prev != to){
	    	MyBinaryTree next = node.right;
	        node.right = prev;
	        prev = node;
	        node = next;
	    }
	}

}



