package datastructures.trees.bst.problems;

import java.util.HashSet;

import datastructures.trees.binarytree.MyBinaryTree;

public class TwoBSTsContainSameSetOfElements {
	
	
	// function to insert elements of the
	// tree to map m
	static void insertToHash(MyBinaryTree root, HashSet<Integer> s)
	{
	    if (root == null)
	        return;
	    insertToHash(root.left, s);
	    s.add(root.value);
	    insertToHash(root.right, s);
	}
	 
	// function to check if the two BSTs contain
	// same set of elements
	static boolean checkBSTs(MyBinaryTree root1, MyBinaryTree root2)
	{
	    // Base cases
	    if (root1 != null && root2 != null)
	        return true;
	    if ((root1 == null && root2 != null) || (root1 != null && root2 == null))
	        return false;
	         
	    // Create two hash sets and store
	    // elements both BSTs in them.
	    HashSet<Integer> s1 = new HashSet<Integer>();
	    HashSet<Integer> s2 = new HashSet<Integer>();
	    insertToHash(root1, s1);
	    insertToHash(root2, s2);
	     
	    // Return true if both hash sets
	    // contain same elements.
	    return (s1.equals(s2));
	}

}
