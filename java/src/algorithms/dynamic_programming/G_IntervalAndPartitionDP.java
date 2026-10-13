package algorithms.dynamic_programming;

import common.Check;

/*
 * Interval / partition DP.
 *
 *  - Matrix chain multiplication: dims[i-1] x dims[i] is matrix i. cost[i][j] = min over split k of
 *    cost[i][k] + cost[k+1][j] + dims[i-1]*dims[k]*dims[j]. Fill by increasing chain length. O(n^3).
 *  - Palindrome partitioning, min cuts: isPal[i][j] table, then cuts[j] = min(cuts[i-1] + 1) over
 *    palindromes s[i..j]. O(n^2).
 *  - Egg drop (k eggs, n floors, minimum moves in the worst case). Flip the question:
 *    floors(m, k) = how many floors can be checked with m moves and k eggs
 *                 = floors(m-1, k-1)  (egg breaks: check below)
 *                 + floors(m-1, k)    (egg survives: check above)
 *                 + 1                 (the floor we dropped from)
 *    Find the smallest m with floors(m, k) >= n. O(k * answer) instead of the naive O(k * n^2).
 */
public class G_IntervalAndPartitionDP {

	static int matrixChainOrder(int[] dims) {
		int n = dims.length - 1; // number of matrices
		int[][] cost = new int[n + 1][n + 1];
		for (int len = 2; len <= n; len++) {
			for (int i = 1; i + len - 1 <= n; i++) {
				int j = i + len - 1;
				cost[i][j] = Integer.MAX_VALUE;
				for (int k = i; k < j; k++) {
					cost[i][j] = Math.min(cost[i][j], cost[i][k] + cost[k + 1][j] + dims[i - 1] * dims[k] * dims[j]);
				}
			}
		}
		return cost[1][n];
	}

	static int minCutPalindromePartition(String s) {
		int n = s.length();
		boolean[][] pal = new boolean[n][n];
		int[] cuts = new int[n];
		for (int j = 0; j < n; j++) {
			cuts[j] = j; // worst case: cut between every character
			for (int i = 0; i <= j; i++) {
				if (s.charAt(i) == s.charAt(j) && (j - i < 2 || pal[i + 1][j - 1])) {
					pal[i][j] = true;
					cuts[j] = i == 0 ? 0 : Math.min(cuts[j], cuts[i - 1] + 1);
				}
			}
		}
		return cuts[n - 1];
	}

	static int eggDrop(int eggs, int floors) {
		int[] covered = new int[eggs + 1]; // covered[k] = floors checkable with the current number of moves
		int moves = 0;
		while (covered[eggs] < floors) {
			moves++;
			for (int k = eggs; k >= 1; k--) covered[k] = covered[k - 1] + covered[k] + 1;
		}
		return moves;
	}

	public static void main(String[] args) {
		Check.eq("matrix chain", matrixChainOrder(new int[] { 40, 20, 30, 10, 30 }), 26000);
		Check.eq("matrix chain 2", matrixChainOrder(new int[] { 10, 20, 30, 40, 30 }), 30000);
		Check.eq("min cut aab", minCutPalindromePartition("aab"), 1);
		Check.eq("min cut a", minCutPalindromePartition("a"), 0);
		Check.eq("min cut ab", minCutPalindromePartition("ab"), 1);
		Check.eq("egg drop 2/100", eggDrop(2, 100), 14);
		Check.eq("egg drop 1/10", eggDrop(1, 10), 10);
		Check.eq("egg drop 3/14", eggDrop(3, 14), 4);
	}
}
