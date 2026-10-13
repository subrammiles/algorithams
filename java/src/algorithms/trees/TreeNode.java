package algorithms.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* Shared binary tree node: build from / serialise to LeetCode-style level order (null = missing). */
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
		for (int i = 1; i < levelOrder.length;) {
			TreeNode n = q.poll();
			if (i < levelOrder.length && levelOrder[i] != null) q.add(n.left = new TreeNode(levelOrder[i]));
			i++;
			if (i < levelOrder.length && levelOrder[i] != null) q.add(n.right = new TreeNode(levelOrder[i]));
			i++;
		}
		return root;
	}

	public static List<Integer> toLevelOrder(TreeNode root) {
		List<Integer> out = new ArrayList<>();
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		while (!q.isEmpty()) {
			TreeNode n = q.poll();
			out.add(n == null ? null : n.val);
			if (n != null) {
				q.add(n.left);
				q.add(n.right);
			}
		}
		while (!out.isEmpty() && out.get(out.size() - 1) == null) out.remove(out.size() - 1);
		return out;
	}

	public static TreeNode find(TreeNode root, int val) {
		if (root == null || root.val == val) return root;
		TreeNode left = find(root.left, val);
		return left != null ? left : find(root.right, val);
	}

	public static List<Integer> inorder(TreeNode root) {
		List<Integer> out = new ArrayList<>();
		inorder(root, out);
		return out;
	}

	private static void inorder(TreeNode n, List<Integer> out) {
		if (n == null) return;
		inorder(n.left, out);
		out.add(n.val);
		inorder(n.right, out);
	}
}
