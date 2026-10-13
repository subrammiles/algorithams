package algorithms.greedy;

import java.util.Arrays;

import common.Check;

/*
 * Array greedy problems, each with the one-line argument for why greedy is safe.
 *
 *  - Jump game: track the furthest reachable index; if i ever exceeds it, you're stuck.
 *  - Assign cookies: sort both; give the smallest sufficient cookie to the least greedy child.
 *  - Boats to save people (max 2 per boat): pair the heaviest with the lightest if they fit.
 *  - Two city scheduling: sort by (costA - costB); first half to A, rest to B.
 *  - Coin change greedy works only for CANONICAL coin systems (1, 5, 10, 25).
 *    Counterexample: coins {1, 3, 4}, amount 6 -> greedy 4+1+1 (3 coins), optimal 3+3 (2 coins).
 *    Use DP in general (dynamic_programming/C_KnapsackFamily, interview/k_dp).
 */
public class C_ArrayGreedy {

	static boolean canJump(int[] nums) {
		int furthest = 0;
		for (int i = 0; i < nums.length; i++) {
			if (i > furthest) return false;
			furthest = Math.max(furthest, i + nums[i]);
		}
		return true;
	}

	static int findContentChildren(int[] greed, int[] cookies) {
		int[] g = greed.clone(), s = cookies.clone();
		Arrays.sort(g);
		Arrays.sort(s);
		int child = 0;
		for (int cookie = 0; cookie < s.length && child < g.length; cookie++) {
			if (s[cookie] >= g[child]) child++;
		}
		return child;
	}

	static int numRescueBoats(int[] people, int limit) {
		int[] p = people.clone();
		Arrays.sort(p);
		int boats = 0;
		for (int lo = 0, hi = p.length - 1; lo <= hi; hi--, boats++) {
			if (p[lo] + p[hi] <= limit) lo++; // lightest rides along with the heaviest
		}
		return boats;
	}

	static int twoCitySchedCost(int[][] costs) {
		int[][] c = costs.clone();
		Arrays.sort(c, (a, b) -> Integer.compare(a[0] - a[1], b[0] - b[1]));
		int total = 0, half = c.length / 2;
		for (int i = 0; i < c.length; i++) total += i < half ? c[i][0] : c[i][1];
		return total;
	}

	static int greedyCoinCount(int[] coinsDescending, int amount) {
		int count = 0;
		for (int coin : coinsDescending) {
			count += amount / coin;
			amount %= coin;
		}
		return amount == 0 ? count : -1;
	}

	public static void main(String[] args) {
		Check.isTrue("can jump", canJump(new int[] { 2, 3, 1, 1, 4 }));
		Check.eq("stuck at 0", canJump(new int[] { 3, 2, 1, 0, 4 }), false);
		Check.eq("cookies", findContentChildren(new int[] { 1, 2, 3 }, new int[] { 1, 1 }), 1);
		Check.eq("cookies 2", findContentChildren(new int[] { 1, 2 }, new int[] { 1, 2, 3 }), 2);
		Check.eq("boats", numRescueBoats(new int[] { 3, 2, 2, 1 }, 3), 3);
		Check.eq("boats 2", numRescueBoats(new int[] { 3, 5, 3, 4 }, 5), 4);
		Check.eq("two city", twoCitySchedCost(new int[][] { { 10, 20 }, { 30, 200 }, { 400, 50 }, { 30, 20 } }), 110);
		Check.eq("greedy coins canonical", greedyCoinCount(new int[] { 25, 10, 5, 1 }, 63), 6);
		Check.eq("greedy coins NOT optimal", greedyCoinCount(new int[] { 4, 3, 1 }, 6), 3);
	}
}
