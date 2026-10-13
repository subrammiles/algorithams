package algorithms.dynamic_programming;

import common.Check;

/*
 * "Ending at i" DP: best[i] = best answer that MUST end at index i; the overall answer is the max.
 *
 *  - Kadane (max subarray sum): end[i] = max(a[i], end[i-1] + a[i]). O(n), O(1).
 *  - Max product subarray: keep BOTH the max and the min ending at i - a negative number turns the
 *    smallest product into the largest.
 *  - Longest common substring: dp[i][j] = dp[i-1][j-1] + 1 if chars match, else 0
 *    (contrast with LCS where a mismatch keeps the previous best).
 *  - Max-sum increasing subsequence: LIS variant summing values instead of counting. O(n^2).
 *  - Longest bitonic subsequence: LIS ending at i + LDS starting at i - 1.
 */
public class E_SubarrayAndSubsequence {

	static int maxSubarray(int[] a) {
		int best = a[0], endingHere = a[0];
		for (int i = 1; i < a.length; i++) {
			endingHere = Math.max(a[i], endingHere + a[i]);
			best = Math.max(best, endingHere);
		}
		return best;
	}

	static int maxProduct(int[] a) {
		int best = a[0], maxEnd = a[0], minEnd = a[0];
		for (int i = 1; i < a.length; i++) {
			int x = a[i];
			int candidateMax = Math.max(x, Math.max(maxEnd * x, minEnd * x));
			minEnd = Math.min(x, Math.min(maxEnd * x, minEnd * x));
			maxEnd = candidateMax;
			best = Math.max(best, maxEnd);
		}
		return best;
	}

	static int longestCommonSubstring(String a, String b) {
		int[] prev = new int[b.length() + 1];
		int best = 0;
		for (int i = 1; i <= a.length(); i++) {
			int[] cur = new int[b.length() + 1];
			for (int j = 1; j <= b.length(); j++) {
				if (a.charAt(i - 1) == b.charAt(j - 1)) {
					cur[j] = prev[j - 1] + 1;
					best = Math.max(best, cur[j]);
				}
			}
			prev = cur;
		}
		return best;
	}

	static int maxSumIncreasingSubsequence(int[] a) {
		int[] sum = a.clone();
		int best = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < i; j++) if (a[j] < a[i]) sum[i] = Math.max(sum[i], sum[j] + a[i]);
			best = Math.max(best, sum[i]);
		}
		return best;
	}

	static int longestBitonic(int[] a) {
		int n = a.length;
		int[] inc = new int[n], dec = new int[n];
		for (int i = 0; i < n; i++) {
			inc[i] = 1;
			for (int j = 0; j < i; j++) if (a[j] < a[i]) inc[i] = Math.max(inc[i], inc[j] + 1);
		}
		for (int i = n - 1; i >= 0; i--) {
			dec[i] = 1;
			for (int j = n - 1; j > i; j--) if (a[j] < a[i]) dec[i] = Math.max(dec[i], dec[j] + 1);
		}
		int best = 0;
		for (int i = 0; i < n; i++) best = Math.max(best, inc[i] + dec[i] - 1);
		return best;
	}

	public static void main(String[] args) {
		Check.eq("kadane", maxSubarray(new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 }), 6);
		Check.eq("max product", maxProduct(new int[] { 2, 3, -2, 4 }), 6);
		Check.eq("max product zero", maxProduct(new int[] { -2, 0, -1 }), 0);
		Check.eq("max product two negatives", maxProduct(new int[] { -2, 3, -4 }), 24);
		Check.eq("common substring", longestCommonSubstring("abcdxyz", "xyzabcd"), 4);
		Check.eq("max sum increasing", maxSumIncreasingSubsequence(new int[] { 1, 101, 2, 3, 100, 4, 5 }), 106);
		Check.eq("bitonic", longestBitonic(new int[] { 1, 11, 2, 10, 4, 5, 2, 1 }), 6);
	}
}
