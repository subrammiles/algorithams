package algorithms.trees;

import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * BST problems - all exploit "inorder is sorted" or the min/max bounds of each subtree.
 *
 *  - Recover BST (two nodes swapped): an inorder walk sees one or two "drops" (prev > cur);
 *    the first wrong node is prev at the first drop, the second is cur at the last drop.
 *    Swap their values. O(n) time, O(h) space (Morris traversal makes it O(1)).
 *  - Inorder successor: if x < node go left remembering node as a candidate, else go right. O(h).
 *  - Trim BST to [low, high]: a node below low -> answer is in its right subtree, above high ->
 *    left subtree, otherwise keep it and trim both children.
 *  - BST from preorder: pass an upper bound down; each value is consumed once. O(n).
 */
public class D_BSTProblems {

	private static TreeNode first, second, prev;

	static void recoverTree(TreeNode root) {
		first = second = prev = null;
		findSwapped(root);
		int tmp = first.val;
		first.val = second.val;
		second.val = tmp;
	}

	private static void findSwapped(TreeNode n) {
		if (n == null) return;
		findSwapped(n.left);
		if (prev != null && prev.val > n.val) {
			if (first == null) first = prev;
			second = n;
		}
		prev = n;
		findSwapped(n.right);
	}

	static Integer inorderSuccessor(TreeNode root, int x) {
		Integer candidate = null;
		for (TreeNode n = root; n != null;) {
			if (x < n.val) {
				candidate = n.val;
				n = n.left;
			} else {
				n = n.right;
			}
		}
		return candidate;
	}

	static TreeNode trimBST(TreeNode n, int low, int high) {
		if (n == null) return null;
		if (n.val < low) return trimBST(n.right, low, high);
		if (n.val > high) return trimBST(n.left, low, high);
		n.left = trimBST(n.left, low, high);
		n.right = trimBST(n.right, low, high);
		return n;
	}

	private static int idx;

	static TreeNode bstFromPreorder(int[] preorder) {
		idx = 0;
		return fromPreorder(preorder, Integer.MAX_VALUE);
	}

	private static TreeNode fromPreorder(int[] pre, int upperBound) {
		if (idx == pre.length || pre[idx] > upperBound) return null;
		TreeNode n = new TreeNode(pre[idx++]);
		n.left = fromPreorder(pre, n.val);
		n.right = fromPreorder(pre, upperBound);
		return n;
	}

	public static void main(String[] args) {
		TreeNode a = TreeNode.build(1, 3, null, null, 2);
		recoverTree(a);
		Check.eq("recover adjacent swap", TreeNode.toLevelOrder(a), Arrays.asList(3, 1, null, null, 2));
		TreeNode b = TreeNode.build(3, 1, 4, null, null, 2);
		recoverTree(b);
		Check.eq("recover distant swap", TreeNode.inorder(b), List.of(1, 2, 3, 4));
		TreeNode bst = TreeNode.build(5, 3, 6, 2, 4, null, null, 1);
		Check.eq("successor of 4", inorderSuccessor(bst, 4), 5);
		Check.eq("successor of 1", inorderSuccessor(bst, 1), 2);
		Check.eq("successor of max", inorderSuccessor(bst, 6), null);
		Check.eq("trim", TreeNode.toLevelOrder(trimBST(TreeNode.build(3, 0, 4, null, 2, null, null, 1), 1, 3)), Arrays.asList(3, 2, null, 1));
		Check.eq("from preorder", TreeNode.toLevelOrder(bstFromPreorder(new int[] { 8, 5, 1, 7, 10, 12 })), Arrays.asList(8, 5, 10, 1, 7, null, 12));
	}
}
