package interview.e_intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import common.Check;

/*
 * Sweep line: turn intervals into events (x, +/-), sort them, walk left to right keeping a
 * small "active set". Meeting Rooms (B_MeetingRooms) is the simplest case: the active set is a count.
 *
 * 1. The Skyline problem (hard, a favourite).
 *    Events: building start (x, -height), building end (x, +height). Sort by x, then by the
 *    second value. Negative start heights mean that at the same x:
 *      - starts come before ends (no fake drop to 0 between touching buildings),
 *      - taller starts come first, and lower ends come first.
 *    Active set = multiset of heights (TreeMap height -> count). After each event, if the max height
 *    changed, emit (x, newMax). O(n log n).
 *    Without a TreeMap (Python/JS/Go): max-heap + lazy deletion map ("remove when it reaches the top").
 *
 * 2. Interval list intersections - two sorted lists, two pointers.
 *    Overlap = [max(starts), min(ends)] if non-empty; advance the one that ends first. O(m + n).
 *
 * 3. Employee free time - flatten every schedule, sort by start, merge; the gaps between merged
 *    blocks are the common free time. O(N log N). (A heap of k list heads gives O(N log k).)
 *
 * 4. Car pooling - difference array over time: +passengers at pickup, -passengers at drop-off.
 *    Running sum ever above capacity -> false. Use a TreeMap of events if times are huge/sparse.
 *
 * Senior follow-ups:
 *  - Skyline with millions of buildings: divide and conquer merge of two skylines (like merge sort).
 *  - "Max overlapping intervals at any point" / "busiest time": same sweep, track the peak.
 *  - Calendar booking online (My Calendar I/II/III): TreeMap floor/ceiling or a sweep over a TreeMap.
 */
public class C_SweepLine {

	static List<List<Integer>> getSkyline(int[][] buildings) {
		List<int[]> events = new ArrayList<>();
		for (int[] b : buildings) {
			events.add(new int[] { b[0], -b[2] }); // start
			events.add(new int[] { b[1], b[2] }); // end
		}
		events.sort((p, q) -> p[0] != q[0] ? Integer.compare(p[0], q[0]) : Integer.compare(p[1], q[1]));

		TreeMap<Integer, Integer> heights = new TreeMap<>();
		heights.put(0, 1); // ground, never removed
		List<List<Integer>> out = new ArrayList<>();
		int prevMax = 0;
		for (int[] e : events) {
			if (e[1] < 0) heights.merge(-e[1], 1, Integer::sum);
			else if (heights.merge(e[1], -1, Integer::sum) == 0) heights.remove(e[1]);
			int curMax = heights.lastKey();
			if (curMax != prevMax) {
				out.add(List.of(e[0], curMax));
				prevMax = curMax;
			}
		}
		return out;
	}

	static int[][] intervalIntersection(int[][] a, int[][] b) {
		List<int[]> out = new ArrayList<>();
		int i = 0, j = 0;
		while (i < a.length && j < b.length) {
			int lo = Math.max(a[i][0], b[j][0]);
			int hi = Math.min(a[i][1], b[j][1]);
			if (lo <= hi) out.add(new int[] { lo, hi });
			if (a[i][1] < b[j][1]) i++;
			else j++;
		}
		return out.toArray(new int[0][]);
	}

	static int[][] employeeFreeTime(int[][][] schedules) {
		List<int[]> all = new ArrayList<>();
		for (int[][] person : schedules) all.addAll(Arrays.asList(person));
		all.sort((p, q) -> Integer.compare(p[0], q[0]));
		List<int[]> free = new ArrayList<>();
		int end = all.get(0)[1];
		for (int[] in : all) {
			if (in[0] > end) free.add(new int[] { end, in[0] });
			end = Math.max(end, in[1]);
		}
		return free.toArray(new int[0][]);
	}

	// trips[i] = {passengers, from, to}; passengers leave at `to`
	static boolean carPooling(int[][] trips, int capacity) {
		TreeMap<Integer, Integer> delta = new TreeMap<>();
		for (int[] t : trips) {
			delta.merge(t[1], t[0], Integer::sum);
			delta.merge(t[2], -t[0], Integer::sum);
		}
		int load = 0;
		for (int d : delta.values()) {
			load += d;
			if (load > capacity) return false;
		}
		return true;
	}

	public static void main(String[] args) {
		int[][] city = { { 2, 9, 10 }, { 3, 7, 15 }, { 5, 12, 12 }, { 15, 20, 10 }, { 19, 24, 8 } };
		Check.eq("skyline", getSkyline(city), List.of(List.of(2, 10), List.of(3, 15), List.of(7, 12),
				List.of(12, 0), List.of(15, 10), List.of(20, 8), List.of(24, 0)));
		Check.eq("skyline touching equal heights", getSkyline(new int[][] { { 0, 2, 3 }, { 2, 5, 3 } }),
				List.of(List.of(0, 3), List.of(5, 0)));
		Check.eq("skyline same start", getSkyline(new int[][] { { 1, 2, 1 }, { 1, 2, 2 }, { 1, 2, 3 } }),
				List.of(List.of(1, 3), List.of(2, 0)));

		int[][] a = { { 0, 2 }, { 5, 10 }, { 13, 23 }, { 24, 25 } };
		int[][] b = { { 1, 5 }, { 8, 12 }, { 15, 24 }, { 25, 26 } };
		Check.eq("interval intersections", intervalIntersection(a, b),
				new int[][] { { 1, 2 }, { 5, 5 }, { 8, 10 }, { 15, 23 }, { 24, 24 }, { 25, 25 } });

		Check.eq("free time", employeeFreeTime(new int[][][] { { { 1, 2 }, { 5, 6 } }, { { 1, 3 } }, { { 4, 10 } } }),
				new int[][] { { 3, 4 } });
		Check.eq("free time 2",
				employeeFreeTime(new int[][][] { { { 1, 3 }, { 6, 7 } }, { { 2, 4 } }, { { 2, 5 }, { 9, 12 } } }),
				new int[][] { { 5, 6 }, { 7, 9 } });

		int[][] trips = { { 2, 1, 5 }, { 3, 3, 7 } };
		Check.eq("car pooling cap 4", carPooling(trips, 4), false);
		Check.eq("car pooling cap 5", carPooling(trips, 5), true);
		Check.eq("car pooling drop-off then pickup", carPooling(new int[][] { { 3, 2, 7 }, { 3, 7, 9 } }, 3), true);
	}
}
