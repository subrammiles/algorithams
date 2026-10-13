package algorithms.dynamic_programming;

import common.Check;

/*
 * The knapsack family. State: dp[capacity] = best value (or count) using the items seen so far.
 *
 *  0/1 knapsack (each item once):
 *    dp[i][w] = max(dp[i-1][w], dp[i-1][w - wt[i]] + val[i])          O(n*W) time
 *    1D version: iterate w DOWNWARDS so dp[w - wt] still holds the previous item's row.
 *  Unbounded knapsack (items reusable): iterate w UPWARDS - the same item may be added again.
 *  Rod cutting: unbounded knapsack where piece length = weight, price = value.
 *  Target sum (+/- signs): sum(P) - sum(N) = target and sum(P) + sum(N) = total
 *    => sum(P) = (total + target) / 2 -> count subsets with that sum (0/1, counting).
 *  Minimum subset sum difference: find all reachable subset sums <= total/2, take the largest.
 *
 * Note: O(n*W) is "pseudo-polynomial" - exponential in the number of bits of W. Knapsack is NP-hard.
 */
public class C_KnapsackFamily {

	static int knapsack01Table(int[] wt, int[] val, int capacity) {
		int n = wt.length;
		int[][] dp = new int[n + 1][capacity + 1];
		for (int i = 1; i <= n; i++) {
			for (int w = 0; w <= capacity; w++) {
				dp[i][w] = dp[i - 1][w]; // skip item i
				if (wt[i - 1] <= w) dp[i][w] = Math.max(dp[i][w], dp[i - 1][w - wt[i - 1]] + val[i - 1]); // take it
			}
		}
		return dp[n][capacity];
	}

	static int knapsack01(int[] wt, int[] val, int capacity) {
		int[] dp = new int[capacity + 1];
		for (int i = 0; i < wt.length; i++) {
			for (int w = capacity; w >= wt[i]; w--) dp[w] = Math.max(dp[w], dp[w - wt[i]] + val[i]);
		}
		return dp[capacity];
	}

	static int unboundedKnapsack(int[] wt, int[] val, int capacity) {
		int[] dp = new int[capacity + 1];
		for (int i = 0; i < wt.length; i++) {
			for (int w = wt[i]; w <= capacity; w++) dp[w] = Math.max(dp[w], dp[w - wt[i]] + val[i]);
		}
		return dp[capacity];
	}

	/* prices[i] = price of a piece of length i+1. */
	static int rodCutting(int[] prices, int length) {
		int[] dp = new int[length + 1];
		for (int len = 1; len <= length; len++) {
			for (int piece = 1; piece <= len; piece++) dp[len] = Math.max(dp[len], prices[piece - 1] + dp[len - piece]);
		}
		return dp[length];
	}

	static int targetSumWays(int[] nums, int target) {
		int total = 0;
		for (int x : nums) total += x;
		if (Math.abs(target) > total || (total + target) % 2 != 0) return 0;
		int goal = (total + target) / 2;
		int[] ways = new int[goal + 1];
		ways[0] = 1;
		for (int x : nums) {
			for (int s = goal; s >= x; s--) ways[s] += ways[s - x];
		}
		return ways[goal];
	}

	static int minSubsetSumDifference(int[] nums) {
		int total = 0;
		for (int x : nums) total += x;
		boolean[] reachable = new boolean[total / 2 + 1];
		reachable[0] = true;
		for (int x : nums) {
			for (int s = total / 2; s >= x; s--) reachable[s] |= reachable[s - x];
		}
		for (int s = total / 2; s >= 0; s--) {
			if (reachable[s]) return total - 2 * s;
		}
		return total;
	}

	public static void main(String[] args) {
		int[] wt = { 1, 3, 4, 5 }, val = { 1, 4, 5, 7 };
		Check.eq("0/1 knapsack table", knapsack01Table(wt, val, 7), 9);
		Check.eq("0/1 knapsack 1D", knapsack01(wt, val, 7), 9);
		Check.eq("unbounded knapsack", unboundedKnapsack(new int[] { 1, 3, 4 }, new int[] { 15, 50, 60 }, 8), 130);
		Check.eq("rod cutting", rodCutting(new int[] { 1, 5, 8, 9, 10, 17, 17, 20 }, 8), 22);
		Check.eq("target sum", targetSumWays(new int[] { 1, 1, 1, 1, 1 }, 3), 5);
		Check.eq("target sum impossible", targetSumWays(new int[] { 1 }, 2), 0);
		Check.eq("min subset difference", minSubsetSumDifference(new int[] { 1, 6, 11, 5 }), 1);
	}
}
