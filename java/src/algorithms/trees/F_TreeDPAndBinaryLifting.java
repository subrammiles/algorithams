package algorithms.trees;

import java.util.ArrayList;
import java.util.List;

import common.Check;

/*
 * Tree DP and binary lifting.
 *
 *  - House Robber III: each node returns a pair (best if robbed, best if not robbed).
 *    robbed = val + notRobbed(left) + notRobbed(right); notRobbed = max of each child. O(n).
 *  - Binary Tree Cameras (min cameras so every node is watched): post-order states
 *    0 = not covered, 1 = has camera, 2 = covered. Put a camera at a parent of any uncovered child.
 *  - Binary lifting: up[j][v] = 2^j-th ancestor of v. Build O(n log n); k-th ancestor and LCA in
 *    O(log n) per query by jumping powers of two (lift the deeper node, then lift both together).
 */
public class F_TreeDPAndBinaryLifting {

	static int rob(TreeNode root) {
		int[] r = robPair(root);
		return Math.max(r[0], r[1]);
	}

	/* [robbed, notRobbed] */
	private static int[] robPair(TreeNode n) {
		if (n == null) return new int[2];
		int[] l = robPair(n.left), r = robPair(n.right);
		int robbed = n.val + l[1] + r[1];
		int notRobbed = Math.max(l[0], l[1]) + Math.max(r[0], r[1]);
		return new int[] { robbed, notRobbed };
	}

	private static int cameras;

	static int minCameraCover(TreeNode root) {
		cameras = 0;
		if (cover(root) == 0) cameras++;
		return cameras;
	}

	private static int cover(TreeNode n) {
		if (n == null) return 2; // nothing to watch
		int l = cover(n.left), r = cover(n.right);
		if (l == 0 || r == 0) {
			cameras++;
			return 1;
		}
		return (l == 1 || r == 1) ? 2 : 0;
	}

	static class BinaryLifting {
		private final int[][] up;
		private final int[] depth;
		private final int log;

		/* parent[root] = -1 */
		BinaryLifting(int[] parent) {
			int n = parent.length;
			int lg = 1;
			while ((1 << lg) < n) lg++;
			log = lg;
			up = new int[log + 1][n];
			depth = new int[n];
			List<List<Integer>> children = new ArrayList<>();
			for (int i = 0; i < n; i++) children.add(new ArrayList<>());
			int root = 0;
			for (int v = 0; v < n; v++) {
				up[0][v] = parent[v];
				if (parent[v] == -1) root = v;
				else children.get(parent[v]).add(v);
			}
			java.util.Deque<Integer> stack = new java.util.ArrayDeque<>(List.of(root));
			while (!stack.isEmpty()) {
				int v = stack.pop();
				for (int c : children.get(v)) {
					depth[c] = depth[v] + 1;
					stack.push(c);
				}
			}
			for (int j = 1; j <= log; j++) {
				for (int v = 0; v < n; v++) up[j][v] = up[j - 1][v] == -1 ? -1 : up[j - 1][up[j - 1][v]];
			}
		}

		int kthAncestor(int v, int k) {
			for (int j = 0; j <= log && v != -1; j++) if ((k >> j & 1) == 1) v = up[j][v];
			return k >= (1 << (log + 1)) ? -1 : v;
		}

		int lca(int a, int b) {
			if (depth[a] < depth[b]) {
				int t = a;
				a = b;
				b = t;
			}
			a = kthAncestor(a, depth[a] - depth[b]); // lift to the same depth
			if (a == b) return a;
			for (int j = log; j >= 0; j--) {
				if (up[j][a] != up[j][b]) { // jump while the ancestors still differ
					a = up[j][a];
					b = up[j][b];
				}
			}
			return up[0][a];
		}
	}

	public static void main(String[] args) {
		Check.eq("rob tree", rob(TreeNode.build(3, 2, 3, null, 3, null, 1)), 7);
		Check.eq("rob tree 2", rob(TreeNode.build(3, 4, 5, 1, 3, null, 1)), 9);
		Check.eq("cameras", minCameraCover(TreeNode.build(0, 0, null, 0, 0)), 1);
		Check.eq("cameras chain", minCameraCover(TreeNode.build(0, 0, null, 0, null, 0, null, null, 0)), 2);
		//        0
		//      /   \
		//     1     2
		//    / \   / \
		//   3   4 5   6
		BinaryLifting bl = new BinaryLifting(new int[] { -1, 0, 0, 1, 1, 2, 2 });
		Check.eq("1st ancestor of 3", bl.kthAncestor(3, 1), 1);
		Check.eq("2nd ancestor of 5", bl.kthAncestor(5, 2), 0);
		Check.eq("3rd ancestor of 6", bl.kthAncestor(6, 3), -1);
		Check.eq("lca(3,4)", bl.lca(3, 4), 1);
		Check.eq("lca(3,5)", bl.lca(3, 5), 0);
		Check.eq("lca(5,2)", bl.lca(5, 2), 2);
		Check.eq("lca(4,4)", bl.lca(4, 4), 4);
	}
}
