package interview.r_greedy;

import common.Check;

/*
 * Greedy works when a local choice can be proven never to hurt (exchange argument).
 * Always be ready to explain WHY the greedy choice is safe - or give a counterexample (coin change).
 *
 * 1. Jump Game II: BFS by levels over the array; each "level" is the furthest reach so far. O(n).
 * 2. Gas Station: if total gas >= total cost an answer exists; if the tank goes negative at i,
 *    no start in [start..i] works, so restart at i+1. O(n).
 * 3. Task Scheduler (cooldown n): answer = max(tasks, (maxFreq - 1) * (n + 1) + countOfMaxFreq).
 * 4. Candy: two passes (left-to-right, right-to-left), take the max. O(n).
 */
public class A_GreedyClassics {

	static int jump(int[] nums) {
		int jumps = 0, currentEnd = 0, farthest = 0;
		for (int i = 0; i < nums.length - 1; i++) {
			farthest = Math.max(farthest, i + nums[i]);
			if (i == currentEnd) {
				jumps++;
				currentEnd = farthest;
			}
		}
		return jumps;
	}

	static int canCompleteCircuit(int[] gas, int[] cost) {
		int total = 0, tank = 0, start = 0;
		for (int i = 0; i < gas.length; i++) {
			total += gas[i] - cost[i];
			tank += gas[i] - cost[i];
			if (tank < 0) {
				start = i + 1;
				tank = 0;
			}
		}
		return total >= 0 ? start : -1;
	}

	static int leastInterval(char[] tasks, int n) {
		int[] freq = new int[26];
		for (char t : tasks) freq[t - 'A']++;
		int max = 0, countMax = 0;
		for (int f : freq) {
			if (f > max) {
				max = f;
				countMax = 1;
			} else if (f == max) {
				countMax++;
			}
		}
		return Math.max(tasks.length, (max - 1) * (n + 1) + countMax);
	}

	static int candy(int[] ratings) {
		int n = ratings.length;
		int[] c = new int[n];
		java.util.Arrays.fill(c, 1);
		for (int i = 1; i < n; i++) if (ratings[i] > ratings[i - 1]) c[i] = c[i - 1] + 1;
		for (int i = n - 2; i >= 0; i--) if (ratings[i] > ratings[i + 1]) c[i] = Math.max(c[i], c[i + 1] + 1);
		int sum = 0;
		for (int x : c) sum += x;
		return sum;
	}

	public static void main(String[] args) {
		Check.eq("jump", jump(new int[] { 2, 3, 1, 1, 4 }), 2);
		Check.eq("gas", canCompleteCircuit(new int[] { 1, 2, 3, 4, 5 }, new int[] { 3, 4, 5, 1, 2 }), 3);
		Check.eq("gas impossible", canCompleteCircuit(new int[] { 2, 3, 4 }, new int[] { 3, 4, 3 }), -1);
		Check.eq("task scheduler", leastInterval("AAABBB".toCharArray(), 2), 8);
		Check.eq("task scheduler no idle", leastInterval("AAABBBCCDD".toCharArray(), 2), 10);
		Check.eq("candy", candy(new int[] { 1, 0, 2 }), 5);
	}
}
