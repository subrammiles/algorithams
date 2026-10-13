package interview.k_dp;

import java.util.Arrays;

import interview.Check;

/*
 * Classic 1D DP problems.
 *
 * 1. House Robber: dp[i] = max(dp[i-1], dp[i-2] + nums[i]); only two variables needed. O(n)/O(1).
 * 2. Coin Change (min coins): dp[a] = 1 + min(dp[a - coin]). O(amount * coins).
 *    Greedy fails for coins {1,3,4}, amount 6 (greedy 4+1+1 vs optimal 3+3) - mention this.
 * 3. Coin Change II (number of ways): loop coins OUTER to count combinations, not permutations.
 * 4. Decode Ways: dp[i] = dp[i-1] (one digit valid) + dp[i-2] (two digits 10..26).
 */
public class A_OneDimensionalDP {

	static int rob(int[] nums) {
		int prev2 = 0, prev1 = 0;
		for (int x : nums) {
			int cur = Math.max(prev1, prev2 + x);
			prev2 = prev1;
			prev1 = cur;
		}
		return prev1;
	}

	static int coinChangeMin(int[] coins, int amount) {
		int[] dp = new int[amount + 1];
		Arrays.fill(dp, amount + 1); // "infinity"
		dp[0] = 0;
		for (int a = 1; a <= amount; a++) {
			for (int coin : coins) if (coin <= a) dp[a] = Math.min(dp[a], dp[a - coin] + 1);
		}
		return dp[amount] > amount ? -1 : dp[amount];
	}

	static int coinChangeWays(int[] coins, int amount) {
		int[] dp = new int[amount + 1];
		dp[0] = 1;
		for (int coin : coins) {
			for (int a = coin; a <= amount; a++) dp[a] += dp[a - coin];
		}
		return dp[amount];
	}

	static int numDecodings(String s) {
		int prev2 = 1, prev1 = s.charAt(0) == '0' ? 0 : 1;
		for (int i = 1; i < s.length(); i++) {
			int cur = 0;
			if (s.charAt(i) != '0') cur += prev1;
			int two = Integer.parseInt(s.substring(i - 1, i + 1));
			if (two >= 10 && two <= 26) cur += prev2;
			prev2 = prev1;
			prev1 = cur;
		}
		return prev1;
	}

	public static void main(String[] args) {
		Check.eq("rob", rob(new int[] { 2, 7, 9, 3, 1 }), 12);
		Check.eq("coin min", coinChangeMin(new int[] { 1, 3, 4 }, 6), 2);
		Check.eq("coin impossible", coinChangeMin(new int[] { 2 }, 3), -1);
		Check.eq("coin ways", coinChangeWays(new int[] { 1, 2, 5 }, 5), 4);
		Check.eq("decode 226", numDecodings("226"), 3);
		Check.eq("decode 06", numDecodings("06"), 0);
	}
}
