package datastructures.trees.bst;

import datastructures.trees.binarytree.MyBinaryTree;

public class BSTProperties1 {
	
	int getMin(MyBinaryTree root)
	{
		MyBinaryTree temp = root;
	    while(temp.left != null)
	        temp = temp.left;
	    return temp.value;
	}
	
	int getMax(MyBinaryTree root)
	{
		MyBinaryTree temp = root;
	    while(temp.right != null)
	        temp = temp.right;
	    return temp.value;
	}
	
	boolean isBST(MyBinaryTree root)
	{
	    if (root == null)
	        return true;
	    int max_left = getMax(root.left);
	    int min_right = getMin(root.right);
	    if (max_left > root.value || min_right < root.value)
	        return false;
	    if (isBST(root.left) && isBST(root.right))
	        return true;
	    return false;    
	}
	
	 public static boolean isBSTEfficient(MyBinaryTree root)  {
	        return isBSTUtil(root, Integer.MIN_VALUE,Integer.MAX_VALUE);
	    }
	 
	  
	    private static boolean isBSTUtil(MyBinaryTree node, int min, int max)
	    {
	        /* an empty tree is BST */
	        if (node == null)
	            return true;
	        /* false if this node violates the min/max constraints */
	        if (node.value < min || node.value > max)
	            return false;
	        boolean isLeftSubTreeBST=isBSTUtil(node.left, min, node.value-1);
	        boolean isRightSubTreeBST=isBSTUtil(node.right, node.value+1, max);
	        return (isLeftSubTreeBST&&isRightSubTreeBST);
	    }
	    
	    /*Method 3*/
	    // Root of the Binary Tree
	    MyBinaryTree root;
	    // To keep tract of previous node in Inorder Traversal
	    MyBinaryTree prev;
	    boolean isBST()  {
	        prev = null;
	        return isBSTInOrder(root);
	    }
	 
	    boolean isBSTInOrder(MyBinaryTree node)
	    {
	        // traverse the tree in inorder fashion and
	        // keep a track of previous node
	        if (node != null)
	        {
	            if (!isBSTInOrder(node.left))
	                return false;
	            // allows only distinct values node
	            if (prev != null && node.value <= prev.value )
	                return false;
	            prev = node;
	            return isBSTInOrder(node.right);
	        }
	        return true;
	    }
	    

}
