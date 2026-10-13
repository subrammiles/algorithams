package interview.e_intervals;

import java.util.Arrays;
import java.util.PriorityQueue;

import interview.Check;

/*
 * Problem: Minimum number of meeting rooms needed for the given [start, end) meetings.
 *
 * Approach 1 - min-heap of end times: sort by start; if the earliest-ending room is free
 *   (end <= start) reuse it, else open a new room. Heap size = answer. O(n log n).
 * Approach 2 - sweep line: sort starts and ends separately; walk both with two pointers,
 *   +1 on a start, -1 on an end. Peak = answer. Generalises to "max overlapping at any time".
 *
 * Senior follow-up: "which room does each meeting get" (Meeting Rooms III) -> two heaps:
 *   free rooms by id, busy rooms by (endTime, id).
 */
public class B_MeetingRooms {

	static int minRoomsHeap(int[][] meetings) {
		int[][] sorted = meetings.clone();
		Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
		PriorityQueue<Integer> ends = new PriorityQueue<>();
		for (int[] m : sorted) {
			if (!ends.isEmpty() && ends.peek() <= m[0]) ends.poll();
			ends.offer(m[1]);
		}
		return ends.size();
	}

	static int minRoomsSweep(int[][] meetings) {
		int n = meetings.length;
		int[] starts = new int[n], ends = new int[n];
		for (int i = 0; i < n; i++) {
			starts[i] = meetings[i][0];
			ends[i] = meetings[i][1];
		}
		Arrays.sort(starts);
		Arrays.sort(ends);
		int rooms = 0, best = 0, e = 0;
		for (int s = 0; s < n; s++) {
			while (ends[e] <= starts[s]) {
				e++;
				rooms--;
			}
			rooms++;
			best = Math.max(best, rooms);
		}
		return best;
	}

	public static void main(String[] args) {
		int[][] m = { { 0, 30 }, { 5, 10 }, { 15, 20 } };
		Check.eq("heap", minRoomsHeap(m), 2);
		Check.eq("sweep", minRoomsSweep(m), 2);
		int[][] m2 = { { 1, 5 }, { 2, 6 }, { 3, 7 }, { 7, 8 } };
		Check.eq("heap 3", minRoomsHeap(m2), 3);
		Check.eq("sweep 3", minRoomsSweep(m2), 3);
	}
}
