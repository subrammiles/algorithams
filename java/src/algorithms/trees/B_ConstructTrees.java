package algorithms.trees;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Check;

/*
 * Constructing trees.
 *
 *  - From preorder + inorder: preorder[0] is the root; its index in inorder splits left/right
 *    subtrees. A value -> index map makes it O(n) (values must be unique - say so!).
 *  - From inorder + postorder: same, the root is the LAST postorder element; build right first.
 *    (Preorder + postorder alone does NOT determine a binary tree uniquely.)
 *  - Sorted array -> height-balanced BST: middle element as root, recurse on halves. O(n).
 *  - BST iterator: controlled inorder with an explicit stack; next() is amortised O(1),
 *    memory O(h) - the classic "lazy traversal" design question.
 */
public class B_ConstructTrees {

	private static int preIndex;

	static TreeNode buildFromPreIn(int[] preorder, int[] inorder) {
		Map<Integer, Integer> index = new HashMap<>();
		for (int i = 0; i < inorder.length; i++) index.put(inorder[i], i);
		preIndex = 0;
		return preIn(preorder, index, 0, inorder.length - 1);
	}

	private static TreeNode preIn(int[] pre, Map<Integer, Integer> index, int lo, int hi) {
		if (lo > hi) return null;
		TreeNode root = new TreeNode(pre[preIndex++]);
		int mid = index.get(root.val);
		root.left = preIn(pre, index, lo, mid - 1);
		root.right = preIn(pre, index, mid + 1, hi);
		return root;
	}

	private static int postIndex;

	static TreeNode buildFromInPost(int[] inorder, int[] postorder) {
		Map<Integer, Integer> index = new HashMap<>();
		for (int i = 0; i < inorder.length; i++) index.put(inorder[i], i);
		postIndex = postorder.length - 1;
		return inPost(postorder, index, 0, inorder.length - 1);
	}

	private static TreeNode inPost(int[] post, Map<Integer, Integer> index, int lo, int hi) {
		if (lo > hi) return null;
		TreeNode root = new TreeNode(post[postIndex--]);
		int mid = index.get(root.val);
		root.right = inPost(post, index, mid + 1, hi); // right subtree comes just before the root in postorder
		root.left = inPost(post, index, lo, mid - 1);
		return root;
	}

	static TreeNode sortedArrayToBST(int[] a) {
		return toBST(a, 0, a.length - 1);
	}

	private static TreeNode toBST(int[] a, int lo, int hi) {
		if (lo > hi) return null;
		int mid = (lo + hi) >>> 1;
		TreeNode n = new TreeNode(a[mid]);
		n.left = toBST(a, lo, mid - 1);
		n.right = toBST(a, mid + 1, hi);
		return n;
	}

	static class BSTIterator {
		private final Deque<TreeNode> stack = new ArrayDeque<>();

		BSTIterator(TreeNode root) {
			pushLeft(root);
		}

		private void pushLeft(TreeNode n) {
			for (; n != null; n = n.left) stack.push(n);
		}

		boolean hasNext() {
			return !stack.isEmpty();
		}

		int next() {
			TreeNode n = stack.pop();
			pushLeft(n.right);
			return n.val;
		}
	}

	private static int height(TreeNode n) {
		return n == null ? 0 : 1 + Math.max(height(n.left), height(n.right));
	}

	public static void main(String[] args) {
		Check.eq("pre + in", TreeNode.toLevelOrder(buildFromPreIn(new int[] { 3, 9, 20, 15, 7 }, new int[] { 9, 3, 15, 20, 7 })),
				java.util.Arrays.asList(3, 9, 20, null, null, 15, 7));
		Check.eq("in + post", TreeNode.toLevelOrder(buildFromInPost(new int[] { 9, 3, 15, 20, 7 }, new int[] { 9, 15, 7, 20, 3 })),
				java.util.Arrays.asList(3, 9, 20, null, null, 15, 7));
		TreeNode bst = sortedArrayToBST(new int[] { -10, -3, 0, 5, 9 });
		Check.eq("sorted array -> BST inorder", TreeNode.inorder(bst), List.of(-10, -3, 0, 5, 9));
		Check.eq("balanced height", height(bst), 3);
		BSTIterator it = new BSTIterator(TreeNode.build(7, 3, 15, null, null, 9, 20));
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) sb.append(it.next()).append(' ');
		Check.eq("bst iterator", sb.toString().trim(), "3 7 9 15 20");
	}
}
