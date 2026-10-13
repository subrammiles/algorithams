package datastructures.trees.binarytree.problems;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import datastructures.trees.binarytree.MyBinaryTree;

public class BinaryTreePaths {
	
	// Finds the path from root node to given root of the tree, Stores the
	 // path in a vector path[], returns true if path exists otherwise false
	 public static  boolean findPathFromRootToGivenNode(MyBinaryTree root, int n, List<Integer> path)
	 {
	     // base case
	     if (root == null) {
	         return false;
	     }
	      
	     // Store this node . The node will be removed if
	     // not in path from root to n.
	     path.add(root.value);

	     if (root.value == n) {
	         return true;
	     }

	     if (root.left != null && findPathFromRootToGivenNode(root.left, n, path)) {
	         return true;
	     }

	     if (root.right != null && findPathFromRootToGivenNode(root.right, n, path)) {
	         return true;
	     }

	     // If not present in subtree rooted with root, remove root from
	     // path[] and return false
	     path.remove(path.size()-1);

	     return false;
	 }
	 //Roor to leaf 
	 
	// The main function to print paths from the root node to every leaf node
	    public static void printRootToLeafPaths(MyBinaryTree node)
	    {
	        // list to store root-to-leaf path
	        Deque<Integer> path = new ArrayDeque<>();
	        printRootToLeafPaths(node, path);
	    }
	    
	 // Function to check if a given node is a leaf node or not
	    public static boolean isLeaf(MyBinaryTree node) {
	        return (node.left == null && node.right == null);
	    }
	 
	    // Recursive function to find paths from the root node to every leaf node
	    public static void printRootToLeafPaths(MyBinaryTree node, Deque<Integer> path)
	    {
	        // base case
	        if (node == null) {
	            return;
	        }
	 
	        // include the current node to the path
	        path.addLast(node.value);
	 
	        // if a leaf node is found, print the path
	        if (isLeaf(node)) {
	            System.out.println(path);
	        }
	 
	        // recur for the left and right subtree
	        printRootToLeafPaths(node.left, path);
	        printRootToLeafPaths(node.right, path);
	 
	        // backtrack: remove the current node after the left, and right subtree are done
	        path.removeLast();
	    }
	    
	    /*Iteration*/
	 // A Pair class
	    static class NodeAndString
	    {
	        public MyBinaryTree node;     
	        public String path;    
	        private NodeAndString(MyBinaryTree node, String path)
	        {
	            this.node = node;
	            this.path = path;
	        } 
	    }
	    
	    public static void printRootToleafPathIterative(MyBinaryTree root)
	    {
	        if (root == null) {
	            return;
	        }
	        // create an empty stack to store a pair of tree nodes and
	        // its path from the root node
	        Deque<NodeAndString> stack = new ArrayDeque<>();
	        // push the root node
	        stack.add(new NodeAndString(root, ""));
	 
	        // loop till stack is empty
	        while (!stack.isEmpty())
	        {
	            // pop a node from the stack and push the data into the output stack
	        	NodeAndString  pair = stack.pollLast();
	            // fetch current node
	        	MyBinaryTree curr = pair.node;
	            String path = pair.path;

	            // add the current node to the existing path
	            String separator = (path.equals("")) ? "\n" : " —> ";
	            path += (separator + curr.value);
	 
	            // print the path if the node is a leaf
	            if (curr.left == null && curr.right == null) {
	                System.out.print(path);
	            }
	            // push the left and right child of the popped node into the stack
	            if (curr.right != null) {
	                stack.add(new NodeAndString(curr.right, path));
	            }
	            if (curr.left != null) {
	                stack.add(new NodeAndString(curr.left, path));
	            }
	        }
	    }

}
