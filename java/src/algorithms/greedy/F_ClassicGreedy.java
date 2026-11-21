package algorithms.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * Three textbook greedy problems (older versions: FractionalKnapSack, AssignMiceToHoles,
 * PolicemenCatchThieves in this folder).
 *
 * 1. Fractional knapsack - items can be split.
 *    Take items by value/weight ratio, best first; take a fraction of the last one. O(n log n).
 *    Exchange argument: swapping any weight of a lower-ratio item for a higher-ratio one never
 *    lowers the value. The SAME greedy is wrong for 0/1 knapsack (items can't be split) -> DP
 *    (dynamic_programming/C_KnapsackFamily). Interviewers love asking why.
 *
 * 2. Assign mice to holes - minimise the time until the LAST mouse is in a hole (one per hole,
 *    one step per minute). Sort both lists and pair them in order; answer = max |mouse - hole|.
 *    Crossing pairs (a < b but hole(a) > hole(b)) can always be uncrossed without making the max
 *    worse, so sorted pairing is optimal. O(n log n).
 *
 * 3. Policemen catch thieves - each policeman catches at most one thief within distance k.
 *    Keep index lists of P and T; two pointers: if they are within k, pair them and advance both,
 *    otherwise advance whichever is further left (it can never be paired with anything later).
 *    O(n). Same shape as "assign cookies" and "boats to save people".
 *
 * How to argue a greedy in an interview: state the choice, then either an exchange argument
 * ("any optimal solution can be changed to include my choice") or "greedy stays ahead".
 */
public class F_ClassicGreedy {

	static double fractionalKnapsack(int[] values, int[] weights, int capacity) {
		Integer[] order = new Integer[values.length];
		for (int i = 0; i < order.length; i++) order[i] = i;
		Arrays.sort(order, (a, b) -> Double.compare((double) values[b] / weights[b], (double) values[a] / weights[a]));
		double total = 0;
		int left = capacity;
		for (int i : order) {
			if (left == 0) break;
			int take = Math.min(left, weights[i]);
			total += (double) values[i] * take / weights[i];
			left -= take;
		}
		return total;
	}

	static int assignMiceToHoles(int[] mice, int[] holes) {
		int[] m = mice.clone(), h = holes.clone();
		Arrays.sort(m);
		Arrays.sort(h);
		int worst = 0;
		for (int i = 0; i < m.length; i++) worst = Math.max(worst, Math.abs(m[i] - h[i]));
		return worst;
	}

	static int policemenCatchThieves(char[] line, int k) {
		List<Integer> police = new ArrayList<>(), thieves = new ArrayList<>();
		for (int i = 0; i < line.length; i++) (line[i] == 'P' ? police : thieves).add(i);
		int caught = 0, p = 0, t = 0;
		while (p < police.size() && t < thieves.size()) {
			int pi = police.get(p), ti = thieves.get(t);
			if (Math.abs(pi - ti) <= k) {
				caught++;
				p++;
				t++;
			} else if (ti < pi) {
				t++;
			} else {
				p++;
			}
		}
		return caught;
	}

	public static void main(String[] args) {
		Check.near("fractional knapsack", fractionalKnapsack(new int[] { 60, 100, 120 }, new int[] { 10, 20, 30 }, 50), 240);
		Check.near("everything fits", fractionalKnapsack(new int[] { 10, 20 }, new int[] { 1, 2 }, 10), 30);

		Check.eq("mice to holes", assignMiceToHoles(new int[] { 4, -4, 2 }, new int[] { 4, 0, 5 }), 4);
		Check.eq("mice to holes 2", assignMiceToHoles(new int[] { -10, -79, -79, 67, 93, -85, -28, -94 },
				new int[] { -2, 9, 69, 25, -31, 23, 50, 78 }), 102);

		Check.eq("policemen k=1", policemenCatchThieves("PTTPT".toCharArray(), 1), 2);
		Check.eq("policemen k=2", policemenCatchThieves("TTPPTP".toCharArray(), 2), 3);
		Check.eq("policemen k=3", policemenCatchThieves("PTPTTP".toCharArray(), 3), 3);
	}
}
