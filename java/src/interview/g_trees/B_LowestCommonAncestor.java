package interview.g_trees;

import interview.Check;

/*
 * Problem: Lowest common ancestor (LCA) of nodes p and q.
 *
 * Binary tree: recurse; if the current node is p or q return it. If both subtrees return
 * non-null, the current node is the LCA. O(n).
 * BST: walk down; if both values are smaller go left, both larger go right, else here. O(h).
 *
 * Senior follow-ups:
 *  - Nodes with parent pointers: it becomes "intersection of two linked lists".
 *  - Many LCA queries on a static tree: binary lifting, O(n log n) preprocessing, O(log n) per query.
 *  - p or q may not exist: count found nodes instead of returning early.
 */
public class B_LowestCommonAncestor {

	static TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || root == p || root == q) return root;
		TreeNode left = lca(root.left, p, q);
		TreeNode right = lca(root.right, p, q);
		if (left != null && right != null) return root;
		return left != null ? left : right;
	}

	static TreeNode lcaBst(TreeNode root, int p, int q) {
		while (root != null) {
			if (p < root.val && q < root.val) root = root.left;
			else if (p > root.val && q > root.val) root = root.right;
			else return root;
		}
		return null;
	}

	public static void main(String[] args) {
		TreeNode root = TreeNode.build(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
		Check.eq("lca 5,1", lca(root, TreeNode.find(root, 5), TreeNode.find(root, 1)).val, 3);
		Check.eq("lca 5,4", lca(root, TreeNode.find(root, 5), TreeNode.find(root, 4)).val, 5);
		TreeNode bst = TreeNode.build(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
		Check.eq("bst lca 2,8", lcaBst(bst, 2, 8).val, 6);
		Check.eq("bst lca 3,5", lcaBst(bst, 3, 5).val, 4);
	}
}
