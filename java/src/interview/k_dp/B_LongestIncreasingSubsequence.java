package interview.k_dp;

import java.util.Arrays;

import interview.Check;

/*
 * Problem: Length of the longest strictly increasing subsequence.
 *
 * O(n^2) DP: dp[i] = 1 + max(dp[j]) for j < i with a[j] < a[i].
 * O(n log n) patience sorting: tails[k] = smallest possible tail of an increasing subsequence
 *   of length k+1. For each x, binary-search the first tail >= x and replace it (or append).
 *   tails is NOT itself an LIS, but its length is the answer.
 *
 * Follow-ups: Russian Doll Envelopes (sort by width asc, height DESC, then LIS on heights),
 *             number of LIS, reconstructing the sequence (keep parent indices).
 */
public class B_LongestIncreasingSubsequence {

	static int lisQuadratic(int[] a) {
		int[] dp = new int[a.length];
		int best = 0;
		for (int i = 0; i < a.length; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) if (a[j] < a[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
			best = Math.max(best, dp[i]);
		}
		return best;
	}

	static int lisFast(int[] a) {
		int[] tails = new int[a.length];
		int size = 0;
		for (int x : a) {
			int lo = 0, hi = size;
			while (lo < hi) {
				int mid = (lo + hi) / 2;
				if (tails[mid] < x) lo = mid + 1;
				else hi = mid;
			}
			tails[lo] = x;
			if (lo == size) size++;
		}
		return size;
	}

	static int maxEnvelopes(int[][] envelopes) {
		int[][] e = envelopes.clone();
		Arrays.sort(e, (x, y) -> x[0] != y[0] ? Integer.compare(x[0], y[0]) : Integer.compare(y[1], x[1]));
		int[] heights = new int[e.length];
		for (int i = 0; i < e.length; i++) heights[i] = e[i][1];
		return lisFast(heights);
	}

	public static void main(String[] args) {
		int[] a = { 10, 9, 2, 5, 3, 7, 101, 18 };
		Check.eq("quadratic", lisQuadratic(a), 4);
		Check.eq("n log n", lisFast(a), 4);
		Check.eq("all equal", lisFast(new int[] { 7, 7, 7 }), 1);
		Check.eq("envelopes", maxEnvelopes(new int[][] { { 5, 4 }, { 6, 4 }, { 6, 7 }, { 2, 3 } }), 3);
	}
}
