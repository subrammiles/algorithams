package datastructures.trees.binarytree;

public class BinaryTreeNode {
	public int value;
	public BinaryTreeNode left;
	public BinaryTreeNode right;
	
	public BinaryTreeNode(){
		
	}
	public BinaryTreeNode(BinaryTreeNode left,BinaryTreeNode right,int value){
		this.left=left;
		this.right=right;
		this.value=value;
	}
	public BinaryTreeNode(int value){
		this.value=value;
	}
}
