package interview.g_trees;

import common.Check;

/*
 * Problem: Maximum path sum where a path is any node-to-node path (need not pass the root).
 *
 * Pattern: "return one thing, record another". The DFS returns the best DOWNWARD path
 * starting at the node (can extend to the parent), while it records in a field the best
 * path that BENDS at the node (left + node + right, cannot extend further).
 * Negative branches are dropped with max(0, gain). O(n).
 *
 * The same pattern solves: diameter of a binary tree, longest univalue path.
 */
public class C_BinaryTreeMaxPathSum {

	private int best;

	int maxPathSum(TreeNode root) {
		best = Integer.MIN_VALUE;
		gain(root);
		return best;
	}

	private int gain(TreeNode node) {
		if (node == null) return 0;
		int left = Math.max(0, gain(node.left));
		int right = Math.max(0, gain(node.right));
		best = Math.max(best, node.val + left + right);
		return node.val + Math.max(left, right);
	}

	private int diameter;

	int diameterOfBinaryTree(TreeNode root) {
		diameter = 0;
		depth(root);
		return diameter;
	}

	private int depth(TreeNode node) {
		if (node == null) return 0;
		int l = depth(node.left), r = depth(node.right);
		diameter = Math.max(diameter, l + r);
		return 1 + Math.max(l, r);
	}

	public static void main(String[] args) {
		C_BinaryTreeMaxPathSum s = new C_BinaryTreeMaxPathSum();
		Check.eq("max path", s.maxPathSum(TreeNode.build(-10, 9, 20, null, null, 15, 7)), 42);
		Check.eq("all negative", s.maxPathSum(TreeNode.build(-3)), -3);
		Check.eq("diameter", s.diameterOfBinaryTree(TreeNode.build(1, 2, 3, 4, 5)), 3);
	}
}
