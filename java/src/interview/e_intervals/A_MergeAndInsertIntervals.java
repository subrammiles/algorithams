package interview.e_intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * Interval pattern: almost always "sort by start, then sweep".
 *
 * 1. Merge overlapping intervals: sort by start; extend the last merged interval while
 *    the next one starts before it ends. O(n log n).
 * 2. Insert into a sorted, non-overlapping list: three phases (before, overlapping, after). O(n).
 * 3. Non-overlapping intervals (min removals): sort by END and greedily keep the earliest-ending.
 */
public class A_MergeAndInsertIntervals {

	static int[][] merge(int[][] intervals) {
		int[][] sorted = intervals.clone();
		Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
		List<int[]> merged = new ArrayList<>();
		for (int[] cur : sorted) {
			if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < cur[0]) {
				merged.add(new int[] { cur[0], cur[1] });
			} else {
				int[] last = merged.get(merged.size() - 1);
				last[1] = Math.max(last[1], cur[1]);
			}
		}
		return merged.toArray(new int[0][]);
	}

	static int[][] insert(int[][] intervals, int[] add) {
		List<int[]> out = new ArrayList<>();
		int i = 0, n = intervals.length;
		while (i < n && intervals[i][1] < add[0]) out.add(intervals[i++]);
		int start = add[0], end = add[1];
		while (i < n && intervals[i][0] <= end) {
			start = Math.min(start, intervals[i][0]);
			end = Math.max(end, intervals[i][1]);
			i++;
		}
		out.add(new int[] { start, end });
		while (i < n) out.add(intervals[i++]);
		return out.toArray(new int[0][]);
	}

	static int eraseOverlapIntervals(int[][] intervals) {
		int[][] sorted = intervals.clone();
		Arrays.sort(sorted, (a, b) -> Integer.compare(a[1], b[1]));
		int removed = 0, end = Integer.MIN_VALUE;
		for (int[] in : sorted) {
			if (in[0] >= end) end = in[1];
			else removed++;
		}
		return removed;
	}

	public static void main(String[] args) {
		Check.eq("merge", merge(new int[][] { { 1, 3 }, { 8, 10 }, { 2, 6 }, { 15, 18 } }),
				new int[][] { { 1, 6 }, { 8, 10 }, { 15, 18 } });
		Check.eq("merge touching", merge(new int[][] { { 1, 4 }, { 4, 5 } }), new int[][] { { 1, 5 } });
		Check.eq("insert", insert(new int[][] { { 1, 2 }, { 3, 5 }, { 6, 7 }, { 8, 10 }, { 12, 16 } }, new int[] { 4, 8 }),
				new int[][] { { 1, 2 }, { 3, 10 }, { 12, 16 } });
		Check.eq("erase overlap", eraseOverlapIntervals(new int[][] { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 3 } }), 1);
	}
}
