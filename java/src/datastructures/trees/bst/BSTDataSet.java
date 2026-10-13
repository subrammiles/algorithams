package datastructures.trees.bst;

import datastructures.trees.binarytree.MyBinaryTree;

public class BSTDataSet {
	
	/*Non BST*/
	public static MyBinaryTree getASimpleNonBST1(){
		MyBinaryTree tree=new MyBinaryTree(1);
		tree.left=new MyBinaryTree(1);
		tree.right=new MyBinaryTree(3);
		return tree;
	}
	
	public static MyBinaryTree getASimpleBST1(){
		MyBinaryTree tree=new MyBinaryTree(2);
		tree.left=new MyBinaryTree(1);
		tree.right=new MyBinaryTree(3);
		return tree;
	}
	
	public static MyBinaryTree getASimpleBST2(){
		MyBinaryTree tree=new MyBinaryTree(4);
        tree.left = new MyBinaryTree(2);
        tree.right = new MyBinaryTree(5);
        tree.left.left = new MyBinaryTree(1);
        tree.left.right = new MyBinaryTree(3);
		return tree;
	}
	
	public static void main(String[] args ) {
		
	}

}
