package datastructures.trees.bst.problems;

import datastructures.trees.binarytree.MyBinaryTree;

public class BSTDeleteNode {
	
	// Returns true if tree with given root contains
	// dead end or not. min and max indicate range
	// of allowed values for current node. Initially
	// these values are full range.
	boolean deadEnd(MyBinaryTree root, int min, int max)
	{
	    // if the root is null or the recursion moves
	    // after leaf node it will return false
	    // i.e no dead end.
	    if (root==null)
	        return false;
	 
	    // if this occurs means dead end is present.
	    if (min == max)
	        return true;
	 
	    // heart of the recursion lies here.
	    return deadEnd(root.left, min, root.value - 1)||
	                deadEnd(root.right, root.value + 1, max);
	}
	 
	// Driver Program
    public static void main(String[] args) {
    	MyBinaryTree tree = new MyBinaryTree();
 
        /*       8
               /   \
              5    11
             /  \
            2    7
             \
              3
               \
                4 */
//        tree.insert(8);
//        tree.insert(5);
//        tree.insert(2);
//        tree.insert(3);
//        tree.insert(7);
//        tree.insert(11);
//        tree.insert(4);
 
//        if (tree.deadEnd(tree.root ,1 ,
//                Integer.MAX_VALUE) == true)
// 
//        System.out.println("Yes ");
//        else
//        System.out.println("No " );
    }

}
