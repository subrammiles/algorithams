package interview.g_trees;

import java.util.LinkedList;
import java.util.Queue;

/* Shared binary tree node. build() takes LeetCode-style level order with nulls. */
public class TreeNode {
	public int val;
	public TreeNode left, right;

	public TreeNode(int val) {
		this.val = val;
	}

	public static TreeNode build(Integer... levelOrder) {
		if (levelOrder.length == 0 || levelOrder[0] == null) return null;
		TreeNode root = new TreeNode(levelOrder[0]);
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		int i = 1;
		while (i < levelOrder.length) {
			TreeNode node = q.poll();
			if (i < levelOrder.length && levelOrder[i] != null) q.add(node.left = new TreeNode(levelOrder[i]));
			i++;
			if (i < levelOrder.length && levelOrder[i] != null) q.add(node.right = new TreeNode(levelOrder[i]));
			i++;
		}
		return root;
	}

	public static TreeNode find(TreeNode root, int val) {
		if (root == null || root.val == val) return root;
		TreeNode left = find(root.left, val);
		return left != null ? left : find(root.right, val);
	}
}
