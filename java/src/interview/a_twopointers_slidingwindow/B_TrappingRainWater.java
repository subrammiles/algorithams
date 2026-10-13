package interview.a_twopointers_slidingwindow;

import interview.Check;

/*
 * Problem: Given bar heights, compute how much rain water is trapped.
 *
 * Key insight: water above bar i = min(maxLeft(i), maxRight(i)) - height[i].
 *
 * Approach 1 (prefix arrays): precompute maxLeft[] and maxRight[]. O(n) time, O(n) space.
 * Approach 2 (two pointers): move the side with the smaller max inward. That side's
 * answer is already decided because the other side is guaranteed to be at least as tall.
 * O(n) time, O(1) space.
 *
 * Senior follow-ups:
 *  - Monotonic stack solution (fills water layer by layer).
 *  - 2D version (Trapping Rain Water II): min-heap BFS from the border, O(mn log(mn)).
 */
public class B_TrappingRainWater {

	static int trapPrefix(int[] h) {
		int n = h.length;
		if (n == 0) return 0;
		int[] left = new int[n], right = new int[n];
		left[0] = h[0];
		for (int i = 1; i < n; i++) left[i] = Math.max(left[i - 1], h[i]);
		right[n - 1] = h[n - 1];
		for (int i = n - 2; i >= 0; i--) right[i] = Math.max(right[i + 1], h[i]);
		int water = 0;
		for (int i = 0; i < n; i++) water += Math.min(left[i], right[i]) - h[i];
		return water;
	}

	static int trapTwoPointers(int[] h) {
		int lo = 0, hi = h.length - 1, leftMax = 0, rightMax = 0, water = 0;
		while (lo < hi) {
			if (h[lo] < h[hi]) {
				leftMax = Math.max(leftMax, h[lo]);
				water += leftMax - h[lo];
				lo++;
			} else {
				rightMax = Math.max(rightMax, h[hi]);
				water += rightMax - h[hi];
				hi--;
			}
		}
		return water;
	}

	public static void main(String[] args) {
		int[] h = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
		Check.eq("prefix", trapPrefix(h), 6);
		Check.eq("two pointers", trapTwoPointers(h), 6);
		Check.eq("two pointers 2", trapTwoPointers(new int[] { 4, 2, 0, 3, 2, 5 }), 9);
		Check.eq("empty", trapTwoPointers(new int[] {}), 0);
	}
}
