package algorithms.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Check;

/*
 * Path problems.
 *
 *  - Path Sum II: all root-to-leaf paths with a given sum. Backtracking DFS. O(n * h) with copies.
 *  - Path Sum III: count DOWNWARD paths (any start/end) with the sum. Prefix sums along the
 *    current root-to-node path in a hash map - the tree version of "subarray sum equals k". O(n).
 *  - Sum root-to-leaf numbers: carry value*10 + digit down the recursion.
 *  - All nodes at distance K: record parent pointers, then BFS from the target in 3 directions.
 */
public class C_PathProblems {

	static List<List<Integer>> pathSum(TreeNode root, int target) {
		List<List<Integer>> out = new ArrayList<>();
		pathSum(root, target, new ArrayList<>(), out);
		return out;
	}

	private static void pathSum(TreeNode n, int remaining, List<Integer> path, List<List<Integer>> out) {
		if (n == null) return;
		path.add(n.val);
		if (n.left == null && n.right == null && remaining == n.val) out.add(new ArrayList<>(path));
		pathSum(n.left, remaining - n.val, path, out);
		pathSum(n.right, remaining - n.val, path, out);
		path.remove(path.size() - 1);
	}

	static int pathSumAnyDownward(TreeNode root, int target) {
		Map<Long, Integer> prefixCount = new HashMap<>();
		prefixCount.put(0L, 1);
		return countPaths(root, 0, target, prefixCount);
	}

	private static int countPaths(TreeNode n, long prefix, int target, Map<Long, Integer> prefixCount) {
		if (n == null) return 0;
		prefix += n.val;
		int count = prefixCount.getOrDefault(prefix - target, 0);
		prefixCount.merge(prefix, 1, Integer::sum);
		count += countPaths(n.left, prefix, target, prefixCount) + countPaths(n.right, prefix, target, prefixCount);
		prefixCount.merge(prefix, -1, Integer::sum); // backtrack: leave the path
		return count;
	}

	static int sumNumbers(TreeNode root) {
		return sumNumbers(root, 0);
	}

	private static int sumNumbers(TreeNode n, int value) {
		if (n == null) return 0;
		value = value * 10 + n.val;
		if (n.left == null && n.right == null) return value;
		return sumNumbers(n.left, value) + sumNumbers(n.right, value);
	}

	static List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
		Map<TreeNode, TreeNode> parent = new HashMap<>();
		recordParents(root, null, parent);
		List<TreeNode> frontier = new ArrayList<>(List.of(target));
		java.util.Set<TreeNode> seen = new java.util.HashSet<>(frontier);
		for (int dist = 0; dist < k; dist++) {
			List<TreeNode> next = new ArrayList<>();
			for (TreeNode n : frontier) {
				for (TreeNode nb : new TreeNode[] { n.left, n.right, parent.get(n) }) {
					if (nb != null && seen.add(nb)) next.add(nb);
				}
			}
			frontier = next;
		}
		List<Integer> out = new ArrayList<>();
		for (TreeNode n : frontier) out.add(n.val);
		out.sort(null);
		return out;
	}

	private static void recordParents(TreeNode n, TreeNode p, Map<TreeNode, TreeNode> parent) {
		if (n == null) return;
		parent.put(n, p);
		recordParents(n.left, n, parent);
		recordParents(n.right, n, parent);
	}

	public static void main(String[] args) {
		TreeNode t = TreeNode.build(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1);
		Check.eq("path sum II", pathSum(t, 22), List.of(List.of(5, 4, 11, 2), List.of(5, 8, 4, 5)));
		Check.eq("path sum III", pathSumAnyDownward(TreeNode.build(10, 5, -3, 3, 2, null, 11, 3, -2, null, 1), 8), 3);
		Check.eq("root-to-leaf numbers", sumNumbers(TreeNode.build(1, 2, 3)), 25);
		Check.eq("root-to-leaf numbers 2", sumNumbers(TreeNode.build(4, 9, 0, 5, 1)), 1026);
		TreeNode r = TreeNode.build(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
		Check.eq("distance k", distanceK(r, TreeNode.find(r, 5), 2), List.of(1, 4, 7));
	}
}
