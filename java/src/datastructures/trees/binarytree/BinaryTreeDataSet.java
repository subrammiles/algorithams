package datastructures.trees.binarytree;

import datastructures.trees.binarytree.problems.BinaryTreePaths;
import datastructures.trees.binarytree.problems.BinaryTreeViews;
import datastructures.trees.binarytree.problems.MirrorAndSym;
import datastructures.trees.binarytree.traversal.BTInorder;
import datastructures.trees.binarytree.traversal.BTLevelOrder;
import datastructures.trees.binarytree.traversal.BinaryTreeTraversal;

public class BinaryTreeDataSet extends BinaryTreeHelper{
	
	public static MyBinaryTree getASimpleBinaryTree1(){
		MyBinaryTree tree=new MyBinaryTree(2);
		tree.left=new MyBinaryTree(1);
		tree.right=new MyBinaryTree(3);
		return tree;
	}
	
	public static MyBinaryTree getASimpleBinaryTree2(){
		
		/*              15               */
		/*     10                 20      */
		/* 8       12           16    25  */
		
		MyBinaryTree root=new MyBinaryTree(15);
		 root.left = new MyBinaryTree(10);
	      root.right = new MyBinaryTree(20);
	      root.left.left = new MyBinaryTree(8);
	      root.left.right = new MyBinaryTree(12);
	      root.right.left = new MyBinaryTree(16);
	      root.right.right = new MyBinaryTree(25);
	      
	      
		return root;
	}
	
	public static MyBinaryTree getAFullBinaryTree() {
		MyBinaryTree tree = new MyBinaryTree();
		tree = new MyBinaryTree(1);
		tree.left = new MyBinaryTree(2);
		tree.right = new MyBinaryTree(3);
		tree.left.left = new MyBinaryTree(4);
		tree.left.right = new MyBinaryTree(5);
		tree.right.left = new MyBinaryTree(6);
		tree.right.right = new MyBinaryTree(7);
		return tree;

	}
	
	  
    
	public static  MyBinaryTree  getASimpleCompleteBinary() {
		MyBinaryTree tree = new MyBinaryTree();
		int arr[] = { 1, 2, 3, 4, 5, 9, 9, 9, 9 };
		tree =FormABinaryTree.getCompleteBinaryTreeFromAnArray(arr);
		return tree;
        
	}
	
	public static  MyBinaryTree  getASimpleNonCompleteBinary() {
		MyBinaryTree tree=new MyBinaryTree(12);
		tree.left=new MyBinaryTree(14);
		tree.right=new MyBinaryTree(15);
		tree.right.right=new MyBinaryTree(19);
		return tree;
        
	}
	public static  MyBinaryTree  getAPerfectBinaryTree(){
		MyBinaryTree root = null;
	    root = new MyBinaryTree(10);
	    root.left = new MyBinaryTree(20);
	    root.right = new MyBinaryTree(30);
	 
	    root.left.left = new MyBinaryTree(40);
	    root.left.right =new  MyBinaryTree(50);
	    root.right.left = new MyBinaryTree(60);
	    root.right.right = new MyBinaryTree(70);
	    return root;
	}
	public static  MyBinaryTree getASkewedBT() {
		MyBinaryTree root = new MyBinaryTree(5);
	    root.right = new MyBinaryTree(4);
	    root.right.left = new MyBinaryTree(3);
	    root.right.left.right = new MyBinaryTree(2);
	    return root;
		
	}
	public static  MyBinaryTree getANonSkewedBT() {
		MyBinaryTree root = new MyBinaryTree(5);
		root.left = new MyBinaryTree(4);
	    root.left.right =new  MyBinaryTree(3);
	    root.left.right.left =new  MyBinaryTree(2);
	    root.left.right.right = new MyBinaryTree(4);
	    return root;
		
	}
	 
	public static void main(String[] args) {
		
		//getASimpleCompleteBinary();
		//MyBinaryTree tree=getASimpleBinaryTree2();
	
		//int h=BinaryTreeHelper.heightofABinaryTreeIteration(tree);
		//System.out.println(h);
		
		//boolean b=isCompleteBT2(getASimpleNonCompleteBinary());
		//System.out.println(b);
		
		//BinaryTreeViews.bottomView2(getASimpleBinaryTree2());
		BinaryTreePaths.printRootToleafPathIterative(getASimpleBinaryTree2());
		
	}

}
