package algorithms.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import common.Check;

/*
 * Traversal variants built on BFS/DFS.
 *
 *  - Zigzag level order: normal BFS by level, reverse every other level.
 *  - Right side view: last node of each BFS level (or DFS right-first, first node per depth).
 *  - Vertical order: (col, row, value) for every node; group by column, sort by row then value.
 *  - Boundary traversal: root, left boundary (no leaves), all leaves left to right,
 *    right boundary bottom-up (no leaves). Needs care to not count a node twice.
 */
public class A_TraversalVariants {

	static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		List<List<Integer>> levels = new ArrayList<>();
		Deque<TreeNode> q = new ArrayDeque<>();
		if (root != null) q.add(root);
		while (!q.isEmpty()) {
			List<Integer> level = new ArrayList<>();
			for (int size = q.size(); size > 0; size--) {
				TreeNode n = q.poll();
				level.add(n.val);
				if (n.left != null) q.add(n.left);
				if (n.right != null) q.add(n.right);
			}
			if (levels.size() % 2 == 1) Collections.reverse(level);
			levels.add(level);
		}
		return levels;
	}

	static List<Integer> rightSideView(TreeNode root) {
		List<Integer> view = new ArrayList<>();
		rightFirst(root, 0, view);
		return view;
	}

	private static void rightFirst(TreeNode n, int depth, List<Integer> view) {
		if (n == null) return;
		if (depth == view.size()) view.add(n.val); // first node seen at this depth
		rightFirst(n.right, depth + 1, view);
		rightFirst(n.left, depth + 1, view);
	}

	static List<List<Integer>> verticalOrder(TreeNode root) {
		Map<Integer, List<int[]>> byColumn = new TreeMap<>(); // col -> [row, val]
		collect(root, 0, 0, byColumn);
		List<List<Integer>> out = new ArrayList<>();
		for (List<int[]> cells : byColumn.values()) {
			cells.sort((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
			List<Integer> column = new ArrayList<>();
			for (int[] c : cells) column.add(c[1]);
			out.add(column);
		}
		return out;
	}

	private static void collect(TreeNode n, int row, int col, Map<Integer, List<int[]>> byColumn) {
		if (n == null) return;
		byColumn.computeIfAbsent(col, k -> new ArrayList<>()).add(new int[] { row, n.val });
		collect(n.left, row + 1, col - 1, byColumn);
		collect(n.right, row + 1, col + 1, byColumn);
	}

	static List<Integer> boundary(TreeNode root) {
		List<Integer> out = new ArrayList<>();
		if (root == null) return out;
		if (!isLeaf(root)) out.add(root.val);
		for (TreeNode n = root.left; n != null; n = n.left != null ? n.left : n.right) if (!isLeaf(n)) out.add(n.val);
		leaves(root, out);
		List<Integer> right = new ArrayList<>();
		for (TreeNode n = root.right; n != null; n = n.right != null ? n.right : n.left) if (!isLeaf(n)) right.add(n.val);
		Collections.reverse(right);
		out.addAll(right);
		return out;
	}

	private static boolean isLeaf(TreeNode n) {
		return n.left == null && n.right == null;
	}

	private static void leaves(TreeNode n, List<Integer> out) {
		if (n == null) return;
		if (isLeaf(n)) out.add(n.val);
		leaves(n.left, out);
		leaves(n.right, out);
	}

	public static void main(String[] args) {
		TreeNode t = TreeNode.build(3, 9, 20, null, null, 15, 7);
		Check.eq("zigzag", zigzagLevelOrder(t), List.of(List.of(3), List.of(20, 9), List.of(15, 7)));
		Check.eq("right side", rightSideView(TreeNode.build(1, 2, 3, null, 5, null, 4)), List.of(1, 3, 4));
		Check.eq("vertical", verticalOrder(t), List.of(List.of(9), List.of(3, 15), List.of(20), List.of(7)));
		Check.eq("vertical ties", verticalOrder(TreeNode.build(1, 2, 3, 4, 5, 6, 7)), List.of(List.of(4), List.of(2), List.of(1, 5, 6), List.of(3), List.of(7)));
		Check.eq("boundary", boundary(TreeNode.build(1, 2, 3, 4, 5, 6, null, null, null, 7, 8, 9, 10)), List.of(1, 2, 4, 7, 8, 9, 10, 6, 3));
	}
}
