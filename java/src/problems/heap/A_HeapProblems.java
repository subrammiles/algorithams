package problems.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import common.Check;

/*
 * Heap problems (top-k frequent, merge k lists, running median are in interview/d_heaps).
 *
 *  - K closest points to the origin: MAX-heap of size k on squared distance (no sqrt needed);
 *    evict the farthest. O(n log k). Quickselect gives O(n) average.
 *  - Kth largest in a stream: min-heap of size k; the top is the answer. add() is O(log k).
 *  - Ugly numbers (only factors 2, 3, 5): the heap version pops the smallest and pushes x*2, x*3,
 *    x*5 (dedupe!); the DP version with three pointers is O(n) and the one to aim for.
 *  - Last stone weight: max-heap simulation.
 */
public class A_HeapProblems {

	static List<List<Integer>> kClosest(int[][] points, int k) {
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(b[0] * b[0] + b[1] * b[1], a[0] * a[0] + a[1] * a[1]));
		for (int[] p : points) {
			heap.add(p);
			if (heap.size() > k) heap.poll();
		}
		List<List<Integer>> out = new ArrayList<>();
		for (int[] p : heap) out.add(List.of(p[0], p[1]));
		out.sort((a, b) -> a.get(0) != b.get(0).intValue() ? Integer.compare(a.get(0), b.get(0)) : Integer.compare(a.get(1), b.get(1)));
		return out;
	}

	static class KthLargest {
		private final PriorityQueue<Integer> heap = new PriorityQueue<>();
		private final int k;

		KthLargest(int k, int[] nums) {
			this.k = k;
			for (int x : nums) add(x);
		}

		int add(int val) {
			heap.add(val);
			if (heap.size() > k) heap.poll();
			return heap.peek();
		}
	}

	static int nthUglyNumber(int n) {
		int[] ugly = new int[n];
		ugly[0] = 1;
		int i2 = 0, i3 = 0, i5 = 0;
		for (int i = 1; i < n; i++) {
			int next = Math.min(ugly[i2] * 2, Math.min(ugly[i3] * 3, ugly[i5] * 5));
			ugly[i] = next;
			if (next == ugly[i2] * 2) i2++; // advance every pointer that produced it (dedupes 6 = 2*3 = 3*2)
			if (next == ugly[i3] * 3) i3++;
			if (next == ugly[i5] * 5) i5++;
		}
		return ugly[n - 1];
	}

	static int lastStoneWeight(int[] stones) {
		PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
		for (int s : stones) heap.add(s);
		while (heap.size() > 1) {
			int a = heap.poll(), b = heap.poll();
			if (a != b) heap.add(a - b);
		}
		return heap.isEmpty() ? 0 : heap.peek();
	}

	public static void main(String[] args) {
		Check.eq("k closest", kClosest(new int[][] { { 1, 3 }, { -2, 2 } }, 1), List.of(List.of(-2, 2)));
		Check.eq("k closest 2", kClosest(new int[][] { { 3, 3 }, { 5, -1 }, { -2, 4 } }, 2), List.of(List.of(-2, 4), List.of(3, 3)));
		KthLargest kth = new KthLargest(3, new int[] { 4, 5, 8, 2 });
		Check.eq("stream 3", kth.add(3), 4);
		Check.eq("stream 5", kth.add(5), 5);
		Check.eq("stream 10", kth.add(10), 5);
		Check.eq("stream 9", kth.add(9), 8);
		Check.eq("stream 4", kth.add(4), 8);
		Check.eq("ugly 10", nthUglyNumber(10), 12);
		Check.eq("ugly 1", nthUglyNumber(1), 1);
		Check.eq("last stone", lastStoneWeight(new int[] { 2, 7, 4, 1, 8, 1 }), 1);
	}
}
