package datastructures.trees.bst.problems;

import java.util.concurrent.atomic.AtomicInteger;

import datastructures.trees.binarytree.MyBinaryTree;



class KthSmallestElementBST {
	// Recursive function to find the k'th smallest node
	// in the BST (using inorder traversal)
	public static MyBinaryTree kthSmallest(MyBinaryTree root, AtomicInteger counter, int k) {
		// base case
		if (root == null) {
			return null;
		}

		// recur for the left subtree
		MyBinaryTree left = kthSmallest(root.left, counter, k);

		// if k'th smallest node is found
		if (left != null) {
			return left;
		}

		// if the root is k'th smallest node
		if (counter.incrementAndGet() == k) {
			return root;
		}

		// recur for the right subtree only if k'th smallest node is not found
		// in the right subtree
		return kthSmallest(root.right, counter, k);
	}

	// Function to find the k'th smallest node in the BST
	public static MyBinaryTree findKthSmallest(MyBinaryTree root, int k) {
		// Counter to keep track of the total number of the visited nodes.
		// `AtomicInteger` is used here since `Integer` is passed by value in Java
		AtomicInteger counter = new AtomicInteger(0);

		// Recursively find the k'th smallest node
		return kthSmallest(root, counter, k);
	}

	public static void main(String[] args) {
		/*
		 * Construct the following BST 15 / \ / \ 10 20 / \ / \ / \ / \ 8 12 16 25
		 */

		MyBinaryTree root = new MyBinaryTree(15);
		root.left = new MyBinaryTree(10);
		root.right = new MyBinaryTree(20);
		root.left.left = new MyBinaryTree(8);
		root.left.right = new MyBinaryTree(12);
		root.right.left = new MyBinaryTree(16);
		root.right.right = new MyBinaryTree(25);

		int k = 4;

		// find the k'th smallest node
		MyBinaryTree result = findKthSmallest(root, k);

		if (result != null) {
			System.out.printf("%d'th smallest node is %d", k, result.value);
		} else {
			System.out.printf("%d'th smallest node does not exist.", k);
		}
	}
}





