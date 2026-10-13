package interview.k_dp;

import java.util.Arrays;

import interview.Check;

/*
 * Three "harder" DP families that often separate senior candidates.
 *
 * 1. Partition Equal Subset Sum (0/1 knapsack, boolean): can a subset sum to total/2?
 *    Iterate capacity DOWNWARDS so each item is used at most once. O(n * sum).
 *
 * 2. Burst Balloons (interval DP): think of k as the LAST balloon burst in (l, r);
 *    dp[l][r] = max(dp[l][k] + dp[k][r] + v[l]*v[k]*v[r]). Fill by increasing interval length. O(n^3).
 *
 * 3. Travelling Salesman (bitmask DP, n <= ~20): dp[mask][i] = min cost to visit set `mask`
 *    ending at i. O(2^n * n^2). Compare with branch_and_bound/TravelingSalesman.java.
 */
public class D_KnapsackIntervalBitmaskDP {

	static boolean canPartition(int[] nums) {
		int sum = Arrays.stream(nums).sum();
		if (sum % 2 == 1) return false;
		boolean[] dp = new boolean[sum / 2 + 1];
		dp[0] = true;
		for (int x : nums) {
			for (int c = sum / 2; c >= x; c--) dp[c] |= dp[c - x];
		}
		return dp[sum / 2];
	}

	static int maxCoins(int[] nums) {
		int n = nums.length + 2;
		int[] v = new int[n];
		v[0] = v[n - 1] = 1;
		for (int i = 0; i < nums.length; i++) v[i + 1] = nums[i];
		int[][] dp = new int[n][n];
		for (int len = 2; len < n; len++) {
			for (int l = 0; l + len < n; l++) {
				int r = l + len;
				for (int k = l + 1; k < r; k++) {
					dp[l][r] = Math.max(dp[l][r], dp[l][k] + dp[k][r] + v[l] * v[k] * v[r]);
				}
			}
		}
		return dp[0][n - 1];
	}

	static int tsp(int[][] dist) {
		int n = dist.length, full = 1 << n, INF = Integer.MAX_VALUE / 2;
		int[][] dp = new int[full][n];
		for (int[] row : dp) Arrays.fill(row, INF);
		dp[1][0] = 0; // start at city 0
		for (int mask = 1; mask < full; mask++) {
			for (int last = 0; last < n; last++) {
				if (dp[mask][last] == INF || (mask & (1 << last)) == 0) continue;
				for (int next = 0; next < n; next++) {
					if ((mask & (1 << next)) != 0) continue;
					int nm = mask | (1 << next);
					dp[nm][next] = Math.min(dp[nm][next], dp[mask][last] + dist[last][next]);
				}
			}
		}
		int best = INF;
		for (int last = 1; last < n; last++) best = Math.min(best, dp[full - 1][last] + dist[last][0]);
		return best;
	}

	public static void main(String[] args) {
		Check.eq("partition yes", canPartition(new int[] { 1, 5, 11, 5 }), true);
		Check.eq("partition no", canPartition(new int[] { 1, 2, 3, 5 }), false);
		Check.eq("burst balloons", maxCoins(new int[] { 3, 1, 5, 8 }), 167);
		int[][] dist = { { 0, 10, 15, 20 }, { 10, 0, 35, 25 }, { 15, 35, 0, 30 }, { 20, 25, 30, 0 } };
		Check.eq("tsp", tsp(dist), 80);
	}
}
