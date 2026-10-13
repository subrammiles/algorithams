package algorithms.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * Interval greedy. The usual proof: "the interval that ENDS first leaves the most room for the rest",
 * so any optimal solution can be exchanged for one that picks it.
 *
 *  - Activity selection: sort by end, take every activity that starts after the last chosen end.
 *  - Minimum arrows to burst balloons: same as activity selection - one arrow per chosen end.
 *    (Sort with Integer.compare, not a - b: coordinates can overflow.)
 *  - Partition labels: record each char's last index; cut when i reaches the furthest last index.
 *  - Minimum railway platforms: sort arrivals and departures separately, sweep. O(n log n).
 */
public class A_IntervalGreedy {

	static int maxActivities(int[] start, int[] end) {
		Integer[] order = new Integer[start.length];
		for (int i = 0; i < order.length; i++) order[i] = i;
		Arrays.sort(order, (a, b) -> Integer.compare(end[a], end[b]));
		int count = 0, lastEnd = Integer.MIN_VALUE;
		for (int i : order) {
			if (start[i] >= lastEnd) {
				count++;
				lastEnd = end[i];
			}
		}
		return count;
	}

	static int findMinArrowShots(int[][] points) {
		int[][] p = points.clone();
		Arrays.sort(p, (a, b) -> Integer.compare(a[1], b[1]));
		int arrows = 0;
		long arrowAt = Long.MIN_VALUE;
		for (int[] balloon : p) {
			if (balloon[0] > arrowAt) { // not hit by the last arrow
				arrows++;
				arrowAt = balloon[1];
			}
		}
		return arrows;
	}

	static List<Integer> partitionLabels(String s) {
		int[] last = new int[26];
		for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
		List<Integer> sizes = new ArrayList<>();
		int start = 0, end = 0;
		for (int i = 0; i < s.length(); i++) {
			end = Math.max(end, last[s.charAt(i) - 'a']);
			if (i == end) {
				sizes.add(end - start + 1);
				start = i + 1;
			}
		}
		return sizes;
	}

	static int minPlatforms(int[] arrivals, int[] departures) {
		int[] arr = arrivals.clone(), dep = departures.clone();
		Arrays.sort(arr);
		Arrays.sort(dep);
		int platforms = 0, best = 0, d = 0;
		for (int a : arr) {
			while (dep[d] < a) { // trains that left before this one arrives
				d++;
				platforms--;
			}
			best = Math.max(best, ++platforms);
		}
		return best;
	}

	public static void main(String[] args) {
		Check.eq("activities", maxActivities(new int[] { 1, 3, 0, 5, 8, 5 }, new int[] { 2, 4, 6, 7, 9, 9 }), 4);
		Check.eq("arrows", findMinArrowShots(new int[][] { { 10, 16 }, { 2, 8 }, { 1, 6 }, { 7, 12 } }), 2);
		Check.eq("arrows separate", findMinArrowShots(new int[][] { { 1, 2 }, { 3, 4 }, { 5, 6 }, { 7, 8 } }), 4);
		Check.eq("arrows touching", findMinArrowShots(new int[][] { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 } }), 2);
		Check.eq("arrows extreme", findMinArrowShots(new int[][] { { -2147483646, -2147483645 }, { 2147483646, 2147483647 } }), 2);
		Check.eq("partition labels", partitionLabels("ababcbacadefegdehijhklij"), List.of(9, 7, 8));
		Check.eq("platforms", minPlatforms(new int[] { 900, 940, 950, 1100, 1500, 1800 }, new int[] { 910, 1200, 1120, 1130, 1900, 2000 }), 3);
	}
}
