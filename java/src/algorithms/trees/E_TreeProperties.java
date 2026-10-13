package algorithms.trees;

import java.util.ArrayList;
import java.util.List;

import common.Check;

/*
 * Tree property checks and in-place transformations.
 *
 *  - Symmetric tree: compare left.left with right.right and left.right with right.left.
 *  - Subtree of another tree: at each node check "same tree". O(m*n) worst case;
 *    O(m+n) by serialising both and running KMP (mention as the optimisation).
 *  - Flatten to linked list (preorder, in place): reverse-preorder walk (right, left, root)
 *    with a "prev" pointer, or Morris-style rewiring for O(1) space.
 *  - Count nodes of a COMPLETE tree in O(log^2 n): if the leftmost and rightmost depths match,
 *    the tree is perfect -> 2^h - 1; otherwise recurse on both children.
 */
public class E_TreeProperties {

	static boolean isSymmetric(TreeNode root) {
		return root == null || mirror(root.left, root.right);
	}

	private static boolean mirror(TreeNode a, TreeNode b) {
		if (a == null || b == null) return a == b;
		return a.val == b.val && mirror(a.left, b.right) && mirror(a.right, b.left);
	}

	static boolean isSubtree(TreeNode root, TreeNode sub) {
		if (root == null) return sub == null;
		return sameTree(root, sub) || isSubtree(root.left, sub) || isSubtree(root.right, sub);
	}

	private static boolean sameTree(TreeNode a, TreeNode b) {
		if (a == null || b == null) return a == b;
		return a.val == b.val && sameTree(a.left, b.left) && sameTree(a.right, b.right);
	}

	private static TreeNode flattenPrev;

	static void flatten(TreeNode root) {
		flattenPrev = null;
		flattenReverse(root);
	}

	private static void flattenReverse(TreeNode n) {
		if (n == null) return;
		flattenReverse(n.right);
		flattenReverse(n.left);
		n.right = flattenPrev;
		n.left = null;
		flattenPrev = n;
	}

	static int countCompleteNodes(TreeNode root) {
		if (root == null) return 0;
		int leftDepth = 0, rightDepth = 0;
		for (TreeNode n = root; n != null; n = n.left) leftDepth++;
		for (TreeNode n = root; n != null; n = n.right) rightDepth++;
		if (leftDepth == rightDepth) return (1 << leftDepth) - 1; // perfect tree
		return 1 + countCompleteNodes(root.left) + countCompleteNodes(root.right);
	}

	public static void main(String[] args) {
		Check.isTrue("symmetric", isSymmetric(TreeNode.build(1, 2, 2, 3, 4, 4, 3)));
		Check.eq("not symmetric", isSymmetric(TreeNode.build(1, 2, 2, null, 3, null, 3)), false);
		Check.isTrue("subtree", isSubtree(TreeNode.build(3, 4, 5, 1, 2), TreeNode.build(4, 1, 2)));
		Check.eq("not subtree", isSubtree(TreeNode.build(3, 4, 5, 1, 2, null, null, null, null, 0), TreeNode.build(4, 1, 2)), false);
		TreeNode t = TreeNode.build(1, 2, 5, 3, 4, null, 6);
		flatten(t);
		List<Integer> chain = new ArrayList<>();
		boolean leftsNull = true;
		for (TreeNode n = t; n != null; n = n.right) {
			chain.add(n.val);
			leftsNull &= n.left == null;
		}
		Check.eq("flatten order", chain, List.of(1, 2, 3, 4, 5, 6));
		Check.isTrue("flatten left pointers cleared", leftsNull);
		Check.eq("complete count", countCompleteNodes(TreeNode.build(1, 2, 3, 4, 5, 6)), 6);
		Check.eq("complete count empty", countCompleteNodes(null), 0);
	}
}
