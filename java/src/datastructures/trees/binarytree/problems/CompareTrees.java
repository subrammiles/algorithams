package datastructures.trees.binarytree.problems;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import datastructures.trees.binarytree.MyBinaryTree;

public class CompareTrees {
	
	public boolean sameTree (MyBinaryTree x, MyBinaryTree y)
    {
        // if both trees are empty, return true
        if (x == null && y == null) {
            return true;
        }
        // if both trees are non-empty and the value of their root node matches,
        // recur for their left and right subtree
        boolean isEmpty=x != null && y != null;
        boolean isRootsAreEqual=x.value == y.value;
        boolean leftSubTreesAreEqual=sameTree (x.left, y.left);
        boolean rightSubtreeAreEqual=sameTree (x.right, y.right);
        return isEmpty && leftSubTreesAreEqual && leftSubTreesAreEqual &&
                    rightSubtreeAreEqual;
    }
	
	public static boolean isIdentical(MyBinaryTree root1, MyBinaryTree root2)
    {
		// Return true if both trees are empty
	    if (root1 == null && root2 == null)  return true;
	 
	    // Return false if one is empty and other is not
	    if (root1 == null || root2 == null) return false;
	 
	    // Create an empty queues for simultaneous traversals
	    Queue<MyBinaryTree > q1 = new LinkedList<MyBinaryTree> ();
	    Queue<MyBinaryTree>  q2 = new LinkedList<MyBinaryTree> ();
	 
	    // Enqueue Roots of trees in respective queues
	    q1.add(root1);
	    q2.add(root2);
	 
	    while (!q1.isEmpty() && !q2.isEmpty())
	    {
	        // Get front nodes and compare them
	    	MyBinaryTree n1 = q1.peek();
	    	MyBinaryTree n2 = q2.peek();
	 
	        if (n1.value != n2.value)
	        return false;
	 
	        // Remove front nodes from queues
	        q1.remove();
	        q2.remove();
	 
	        /* Enqueue left children of both nodes */
	        if (n1.left != null && n2.left != null)
	        {
	            q1.add(n1.left);
	            q2.add(n2.left);
	        }
	 
	        // If one left child is empty and other is not
	        else if (n1.left != null || n2.left != null)
	            return false;
	 
	        // Right child code (Similar to left child code)
	        if (n1.right != null && n2.right != null)
	        {
	            q1.add(n1.right);
	            q2.add(n2.right);
	        }
	        else if (n1.right != null || n2.right != null)
	            return false;
	    }
	 
	    return true;    
	  }
	
	
	
	 /* This function returns true if S is a subtree of T, otherwise false */
    boolean isSubtree(MyBinaryTree tree, MyBinaryTree subtree)
    {
        /* base cases */
        if (tree == null)
            return true;
  
        if (subtree == null)
            return false;
  
        /* Check the tree with root as current node */
        if (isIdentical(tree, subtree))
            return true;
  
        /* If the tree with root as current node doesn't match then
           try left and right subtrees one by one */
        return isSubtree(tree.left, subtree)
                || isSubtree(tree.right, subtree);
    }
    
 // Function to check if a given binary tree is a subtree of another
    // binary tree or not
    public static boolean checkSubtree(MyBinaryTree tree, MyBinaryTree subtree)
    {
        // base case: both trees are the same
        if (tree == subtree) {
            return true;
        }
 
        // base case: if the first tree is empty but the second tree is non-empty
        if (tree == null) {
            return false;
        }
 
        // store the inorder traversal of both trees in `first` and `second`,
        // respectively
        List<Integer> first = new ArrayList<>(); 
        List<Integer> second = new ArrayList<>();
        inorder(tree, first);
        inorder(subtree, second);
 
        // return false if the second list is not a sublist of the first list
        if (!isSublist(first, second)) {
            return false;
        }
 
        // reset both lists
        first.clear();
        second.clear();
 
        // Now store postorder traversal of both trees in `first` and `second`,
        // respectively
        postorder(tree, first);
        postorder(subtree, second);
 
        // return false if the second list is not a sublist of the first list
        if (!isSublist(first, second)) {
            return false;
        }
 
        return true;
    }
    
 // Function to store inorder traversal on the tree in a list
    public static void inorder(MyBinaryTree node, List<Integer> list)
    {
        if (node == null) {
            return;
        }
 
        inorder(node.left, list);
        list.add(node.value);
        inorder(node.right, list);
    }
 
    // Function to store postorder traversal on the tree in a list
    public static void postorder(MyBinaryTree node, List<Integer> list)
    {
        if (node == null) {
            return;
        }
 
        postorder(node.left, list);
        postorder(node.right, list);
        list.add(node.value);
    }
    
 // Utility function to check if y is sublist of x or not
    public static boolean isSublist(List<Integer> x, List<Integer> y) {
 
        for (int i = 0; i < x.size() - y.size() + 1; i++)
        {
            if (x.subList(i, i + y.size()).equals(y)) {
                return true;
            }
        }
        return false;
    }
 
    
    
	
	

 



}
