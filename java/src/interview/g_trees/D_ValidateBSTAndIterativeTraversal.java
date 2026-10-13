package interview.g_trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import interview.Check;

/*
 * 1. Validate BST: pass down (low, high) bounds. Comparing only with direct children is the
 *    classic WRONG answer (fails for 5,4,6,null,null,3,7). Use long/null bounds for Integer.MIN/MAX.
 * 2. Iterative inorder with an explicit stack (what interviewers ask when recursion is "not allowed").
 * 3. Morris inorder: O(1) extra space by temporarily threading right pointers to the successor.
 */
public class D_ValidateBSTAndIterativeTraversal {

	static boolean isValidBST(TreeNode node, Long low, Long high) {
		if (node == null) return true;
		if ((low != null && node.val <= low) || (high != null && node.val >= high)) return false;
		return isValidBST(node.left, low, (long) node.val) && isValidBST(node.right, (long) node.val, high);
	}

	static List<Integer> inorderIterative(TreeNode root) {
		List<Integer> out = new ArrayList<>();
		Deque<TreeNode> stack = new ArrayDeque<>();
		TreeNode cur = root;
		while (cur != null || !stack.isEmpty()) {
			while (cur != null) {
				stack.push(cur);
				cur = cur.left;
			}
			cur = stack.pop();
			out.add(cur.val);
			cur = cur.right;
		}
		return out;
	}

	static List<Integer> inorderMorris(TreeNode root) {
		List<Integer> out = new ArrayList<>();
		TreeNode cur = root;
		while (cur != null) {
			if (cur.left == null) {
				out.add(cur.val);
				cur = cur.right;
			} else {
				TreeNode pred = cur.left;
				while (pred.right != null && pred.right != cur) pred = pred.right;
				if (pred.right == null) { // first visit: create thread
					pred.right = cur;
					cur = cur.left;
				} else { // second visit: remove thread
					pred.right = null;
					out.add(cur.val);
					cur = cur.right;
				}
			}
		}
		return out;
	}

	public static void main(String[] args) {
		Check.isTrue("valid", isValidBST(TreeNode.build(2, 1, 3), null, null));
		Check.eq("invalid deep", isValidBST(TreeNode.build(5, 4, 6, null, null, 3, 7), null, null), false);
		Check.isTrue("int max", isValidBST(TreeNode.build(Integer.MAX_VALUE), null, null));
		TreeNode t = TreeNode.build(4, 2, 6, 1, 3, 5, 7);
		Check.eq("iterative inorder", inorderIterative(t), List.of(1, 2, 3, 4, 5, 6, 7));
		Check.eq("morris inorder", inorderMorris(t), List.of(1, 2, 3, 4, 5, 6, 7));
		Check.eq("morris restores tree", inorderIterative(t), List.of(1, 2, 3, 4, 5, 6, 7));
	}
}
