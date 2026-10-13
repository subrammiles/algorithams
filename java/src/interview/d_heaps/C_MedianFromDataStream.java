package interview.d_heaps;

import java.util.Collections;
import java.util.PriorityQueue;

import common.Check;

/*
 * Problem: Support addNum(x) and findMedian() over a stream.
 *
 * Approach: Two heaps.
 *   low  = max-heap holding the smaller half
 *   high = min-heap holding the larger half
 * Invariant: low.size() == high.size() or low.size() == high.size() + 1.
 * add: push to low, move low's max to high, rebalance if high grew bigger.
 * addNum O(log n), findMedian O(1).
 *
 * Senior follow-ups:
 *  - Sliding window median: needs deletions -> two TreeMaps/multisets or lazy deletion heaps.
 *  - If values are in a small range (0..100): counting array, O(100) median.
 *  - Huge distributed stream: approximate quantiles (t-digest, GK sketch).
 */
public class C_MedianFromDataStream {

	private final PriorityQueue<Integer> low = new PriorityQueue<>(Collections.reverseOrder());
	private final PriorityQueue<Integer> high = new PriorityQueue<>();

	void addNum(int x) {
		low.offer(x);
		high.offer(low.poll());
		if (high.size() > low.size()) low.offer(high.poll());
	}

	double findMedian() {
		if (low.size() > high.size()) return low.peek();
		return (low.peek() + (double) high.peek()) / 2.0;
	}

	public static void main(String[] args) {
		C_MedianFromDataStream m = new C_MedianFromDataStream();
		m.addNum(1);
		m.addNum(2);
		Check.near("after 1,2", m.findMedian(), 1.5);
		m.addNum(3);
		Check.near("after 1,2,3", m.findMedian(), 2.0);
		m.addNum(-5);
		m.addNum(10);
		Check.near("after 5 numbers", m.findMedian(), 2.0);
	}
}
