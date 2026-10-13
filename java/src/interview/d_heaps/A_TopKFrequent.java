package interview.d_heaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import common.Check;

/*
 * Problem: Return the k most frequent elements.
 *
 * Approach 1 - min-heap of size k: keep the k best seen so far; evict the smallest.
 *   O(n log k) time, O(n) space. Good when k << n or the data is a stream.
 * Approach 2 - bucket sort by frequency: bucket[f] = values with frequency f.
 *   O(n) time. Good when the whole array fits in memory.
 *
 * Senior follow-ups:
 *  - Quickselect gives average O(n) for "kth largest".
 *  - Huge stream / distributed: Count-Min Sketch + heap (approximate heavy hitters),
 *    or map-reduce counts per shard then merge the per-shard top-k.
 */
public class A_TopKFrequent {

	static List<Integer> topKHeap(int[] nums, int k) {
		Map<Integer, Integer> freq = new HashMap<>();
		for (int x : nums) freq.merge(x, 1, Integer::sum);
		PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
		for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
			heap.offer(e);
			if (heap.size() > k) heap.poll();
		}
		List<Integer> result = new ArrayList<>();
		while (!heap.isEmpty()) result.add(0, heap.poll().getKey()); // most frequent first
		return result;
	}

	@SuppressWarnings("unchecked")
	static List<Integer> topKBucket(int[] nums, int k) {
		Map<Integer, Integer> freq = new HashMap<>();
		for (int x : nums) freq.merge(x, 1, Integer::sum);
		List<Integer>[] buckets = new List[nums.length + 1];
		for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
			if (buckets[e.getValue()] == null) buckets[e.getValue()] = new ArrayList<>();
			buckets[e.getValue()].add(e.getKey());
		}
		List<Integer> result = new ArrayList<>();
		for (int f = nums.length; f > 0 && result.size() < k; f--) {
			if (buckets[f] != null) result.addAll(buckets[f]);
		}
		return result.subList(0, k);
	}

	static int kthLargestQuickselect(int[] nums, int k) {
		int[] a = nums.clone();
		int target = a.length - k, lo = 0, hi = a.length - 1;
		java.util.Random rnd = new java.util.Random(42);
		while (true) {
			int pivotIndex = lo + rnd.nextInt(hi - lo + 1); // random pivot avoids O(n^2) on sorted input
			int p = partition(a, lo, hi, pivotIndex);
			if (p == target) return a[p];
			if (p < target) lo = p + 1;
			else hi = p - 1;
		}
	}

	private static int partition(int[] a, int lo, int hi, int pivotIndex) {
		int pivot = a[pivotIndex];
		swap(a, pivotIndex, hi);
		int store = lo;
		for (int i = lo; i < hi; i++) {
			if (a[i] < pivot) swap(a, i, store++);
		}
		swap(a, store, hi);
		return store;
	}

	private static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	public static void main(String[] args) {
		int[] nums = { 1, 1, 1, 2, 2, 3 };
		Check.eq("heap", topKHeap(nums, 2), List.of(1, 2));
		Check.eq("bucket", topKBucket(nums, 2), List.of(1, 2));
		Check.eq("kth largest", kthLargestQuickselect(new int[] { 3, 2, 3, 1, 2, 4, 5, 5, 6 }, 4), 4);
	}
}
