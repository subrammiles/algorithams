package interview.k_dp;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import common.Check;

/*
 * DP families that are missing from most prep lists but appear at senior level.
 *
 * 1. Digit DP - "count integers in [1, n] such that <property of their digits>".
 *    Walk the digits of n from the most significant. State:
 *      pos      which digit we are placing
 *      tight    are we still equal to n's prefix? (then the digit is capped at n[pos], else 0..9)
 *      started  have we placed a non-zero digit yet? (leading zeros are not "used" digits)
 *      + whatever the property needs (here: mask of digits already used)
 *    Memoise only the states with tight == false && started == true - those are the ones that repeat.
 *    Example: count special numbers (all digits distinct) <= n. States 10 * 2^10, O(10 * 2^10 * 10).
 *    Range [lo, hi]: f(hi) - f(lo - 1).
 *
 * 2. Longest valid parentheses - stack of indices (often asked as "DP", the stack is cleaner).
 *    Keep the index of the last unmatched ')' (or -1) at the bottom of the stack as a "base".
 *    '(' push i. ')' pop; if the stack is now empty push i as the new base, else the valid run is
 *    i - stack.peek(). O(n). DP alternative: dp[i] = length of valid run ending at i.
 *
 * 3. Partition into k equal-sum subsets - bitmask DP over subsets.
 *    dp[mask] = how full the current bucket is after using exactly the elements in mask
 *               (or -1 if mask is unreachable). Fill buckets one at a time, so the remainder
 *               "sum(mask) mod target" fully describes the state. O(n * 2^n), n <= 16.
 *    Backtracking with sorting + pruning also passes; the DP is the guaranteed bound.
 *
 * Senior follow-ups:
 *  - How do you recognise digit DP? "count numbers up to 10^18 with ..." - too many to enumerate.
 *  - Bitmask DP limits: 2^20 ~ 1e6 states is fine, 2^30 is not. Meet-in-the-middle for n ~ 40.
 */
public class E_DigitAndBitmaskDP {

	static int countSpecialNumbers(int n) {
		char[] digits = String.valueOf(n).toCharArray();
		int[][] memo = new int[digits.length][1 << 10];
		for (int[] row : memo) Arrays.fill(row, -1);
		return countDistinct(digits, 0, 0, true, false, memo);
	}

	private static int countDistinct(char[] digits, int pos, int mask, boolean tight, boolean started, int[][] memo) {
		if (pos == digits.length) return started ? 1 : 0;
		if (!tight && started && memo[pos][mask] >= 0) return memo[pos][mask];
		int limit = tight ? digits[pos] - '0' : 9;
		int total = 0;
		for (int d = 0; d <= limit; d++) {
			boolean nextTight = tight && d == limit;
			if (!started && d == 0) {
				total += countDistinct(digits, pos + 1, mask, nextTight, false, memo); // still a leading zero
			} else if ((mask & (1 << d)) == 0) {
				total += countDistinct(digits, pos + 1, mask | (1 << d), nextTight, true, memo);
			}
		}
		if (!tight && started) memo[pos][mask] = total;
		return total;
	}

	static int longestValidParentheses(String s) {
		Deque<Integer> stack = new ArrayDeque<>();
		stack.push(-1);
		int best = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				stack.push(i);
			} else {
				stack.pop();
				if (stack.isEmpty()) stack.push(i); // unmatched ')' becomes the new base
				else best = Math.max(best, i - stack.peek());
			}
		}
		return best;
	}

	static boolean canPartitionKSubsets(int[] nums, int k) {
		int sum = Arrays.stream(nums).sum();
		if (sum % k != 0) return false;
		int target = sum / k, n = nums.length;
		int[] dp = new int[1 << n];
		Arrays.fill(dp, -1);
		dp[0] = 0;
		for (int mask = 0; mask < (1 << n); mask++) {
			if (dp[mask] < 0) continue;
			for (int i = 0; i < n; i++) {
				int next = mask | (1 << i);
				if (next != mask && dp[next] < 0 && dp[mask] + nums[i] <= target) {
					dp[next] = (dp[mask] + nums[i]) % target;
				}
			}
		}
		return dp[(1 << n) - 1] == 0;
	}

	public static void main(String[] args) {
		Check.eq("special numbers <= 20", countSpecialNumbers(20), 19);
		Check.eq("special numbers <= 5", countSpecialNumbers(5), 5);
		Check.eq("special numbers <= 135", countSpecialNumbers(135), 110);

		Check.eq("valid parens (()", longestValidParentheses("(()"), 2);
		Check.eq("valid parens )()())", longestValidParentheses(")()())"), 4);
		Check.eq("valid parens ()(())", longestValidParentheses("()(())"), 6);
		Check.eq("valid parens empty", longestValidParentheses(""), 0);

		Check.eq("k subsets yes", canPartitionKSubsets(new int[] { 4, 3, 2, 3, 5, 2, 1 }, 4), true);
		Check.eq("k subsets no", canPartitionKSubsets(new int[] { 1, 2, 3, 4 }, 3), false);
	}
}
