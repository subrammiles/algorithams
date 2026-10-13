package algorithms.dynamic_programming;

import common.Check;

/*
 * Counting DP: the answer is a number of ways, often reported modulo 1e9+7.
 *
 *  - Unique BSTs with n keys: choose root r, left has r-1 keys, right has n-r keys.
 *    G(n) = sum G(r-1) * G(n-r) -> Catalan numbers (1, 1, 2, 5, 14, 42, ...).
 *  - Count palindromic substrings: expand around 2n-1 centres, count every successful expansion.
 *  - Dice rolls: ways[d][t] = sum over face f of ways[d-1][t-f]. O(dice * target * faces).
 */
public class H_CountingDP {

	static final int MOD = 1_000_000_007;

	static long numTrees(int n) {
		long[] g = new long[n + 1];
		g[0] = 1;
		for (int nodes = 1; nodes <= n; nodes++) {
			for (int root = 1; root <= nodes; root++) g[nodes] += g[root - 1] * g[nodes - root];
		}
		return g[n];
	}

	static int countPalindromicSubstrings(String s) {
		int count = 0;
		for (int centre = 0; centre < 2 * s.length() - 1; centre++) {
			int lo = centre / 2, hi = lo + centre % 2;
			while (lo >= 0 && hi < s.length() && s.charAt(lo) == s.charAt(hi)) {
				count++;
				lo--;
				hi++;
			}
		}
		return count;
	}

	static int numRollsToTarget(int dice, int faces, int target) {
		int[] ways = new int[target + 1];
		ways[0] = 1;
		for (int d = 1; d <= dice; d++) {
			int[] next = new int[target + 1];
			for (int t = 1; t <= target; t++) {
				for (int f = 1; f <= faces && f <= t; f++) next[t] = (next[t] + ways[t - f]) % MOD;
			}
			ways = next;
		}
		return ways[target];
	}

	public static void main(String[] args) {
		Check.eq("unique BSTs 3", numTrees(3), 5L);
		Check.eq("unique BSTs 5", numTrees(5), 42L);
		Check.eq("palindromic substrings aaa", countPalindromicSubstrings("aaa"), 6);
		Check.eq("palindromic substrings abc", countPalindromicSubstrings("abc"), 3);
		Check.eq("dice 2x6 -> 7", numRollsToTarget(2, 6, 7), 6);
		Check.eq("dice 30x30 -> 500", numRollsToTarget(30, 30, 500), 222616187);
	}
}
