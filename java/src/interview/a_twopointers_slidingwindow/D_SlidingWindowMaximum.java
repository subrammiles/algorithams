package interview.a_twopointers_slidingwindow;

import java.util.ArrayDeque;
import java.util.Deque;

import common.Check;

/*
 * Problem: For each window of size k, report the maximum.
 *
 * Approach: Monotonic deque of indices, values decreasing from front to back.
 *  - Pop from the back while the new value is >= back (they can never be a max again).
 *  - Pop from the front when it slides out of the window.
 *  - Front is the current max.
 * Every index is pushed and popped at most once -> O(n).
 *
 * Naive O(nk); heap O(n log n). Interviewers expect the deque.
 */
public class D_SlidingWindowMaximum {

	static int[] maxSlidingWindow(int[] nums, int k) {
		int[] result = new int[nums.length - k + 1];
		Deque<Integer> dq = new ArrayDeque<>();
		for (int i = 0; i < nums.length; i++) {
			if (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
			while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) dq.pollLast();
			dq.offerLast(i);
			if (i >= k - 1) result[i - k + 1] = nums[dq.peekFirst()];
		}
		return result;
	}

	public static void main(String[] args) {
		Check.eq("k=3", maxSlidingWindow(new int[] { 1, 3, -1, -3, 5, 3, 6, 7 }, 3), new int[] { 3, 3, 5, 5, 6, 7 });
		Check.eq("k=1", maxSlidingWindow(new int[] { 4, 2 }, 1), new int[] { 4, 2 });
	}
}
