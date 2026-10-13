package algorithms.dynamic_programming;

import common.Check;

/*
 * 1D DP where the answer at i depends on a few previous answers ("what was the last step?").
 * All of these keep only O(1) previous values instead of a full array.
 *
 *  - Climbing stairs (1 or 2 steps): ways(i) = ways(i-1) + ways(i-2)  -> Fibonacci.
 *  - Min cost climbing stairs: cost to stand on i = cost[i] + min(prev, prev2).
 *  - House Robber II (houses in a circle): first and last are neighbours, so answer =
 *    max(rob houses 0..n-2, rob houses 1..n-1) using the linear House Robber.
 *  - Tribonacci: three previous values.
 *  - Delete and Earn: taking value v deletes v-1 and v+1 -> bucket points by value, then it IS
 *    House Robber over values. Spotting such reductions is what senior interviews look for.
 */
public class A_ClimbingStairsFamily {

	static int climbStairs(int n) {
		int prev2 = 1, prev1 = 1; // ways to stand on step 0 and step 1
		for (int i = 2; i <= n; i++) {
			int cur = prev1 + prev2;
			prev2 = prev1;
			prev1 = cur;
		}
		return prev1;
	}

	static int minCostClimbingStairs(int[] cost) {
		int prev2 = 0, prev1 = 0; // min cost to reach step i-2, i-1 (top is step n)
		for (int i = 2; i <= cost.length; i++) {
			int cur = Math.min(prev1 + cost[i - 1], prev2 + cost[i - 2]);
			prev2 = prev1;
			prev1 = cur;
		}
		return prev1;
	}

	static int robLinear(int[] nums, int from, int to) {
		int prev2 = 0, prev1 = 0;
		for (int i = from; i <= to; i++) {
			int cur = Math.max(prev1, prev2 + nums[i]);
			prev2 = prev1;
			prev1 = cur;
		}
		return prev1;
	}

	static int robCircular(int[] nums) {
		if (nums.length == 1) return nums[0];
		return Math.max(robLinear(nums, 0, nums.length - 2), robLinear(nums, 1, nums.length - 1));
	}

	static int tribonacci(int n) {
		if (n == 0) return 0;
		int a = 0, b = 1, c = 1;
		for (int i = 3; i <= n; i++) {
			int d = a + b + c;
			a = b;
			b = c;
			c = d;
		}
		return c;
	}

	static int deleteAndEarn(int[] nums) {
		int max = 0;
		for (int x : nums) max = Math.max(max, x);
		int[] points = new int[max + 1];
		for (int x : nums) points[x] += x;
		return robLinear(points, 0, max);
	}

	public static void main(String[] args) {
		Check.eq("stairs 5", climbStairs(5), 8);
		Check.eq("stairs 1", climbStairs(1), 1);
		Check.eq("min cost", minCostClimbingStairs(new int[] { 10, 15, 20 }), 15);
		Check.eq("min cost 2", minCostClimbingStairs(new int[] { 1, 100, 1, 1, 1, 100, 1, 1, 100, 1 }), 6);
		Check.eq("rob circle", robCircular(new int[] { 2, 3, 2 }), 3);
		Check.eq("rob circle 2", robCircular(new int[] { 1, 2, 3, 1 }), 4);
		Check.eq("rob single", robCircular(new int[] { 1 }), 1);
		Check.eq("tribonacci 25", tribonacci(25), 1389537);
		Check.eq("delete and earn", deleteAndEarn(new int[] { 3, 4, 2 }), 6);
		Check.eq("delete and earn 2", deleteAndEarn(new int[] { 2, 2, 3, 3, 3, 4 }), 9);
	}
}
