package interview.c_monotonicstack;

import java.util.ArrayDeque;
import java.util.Deque;

import common.Check;

/*
 * Problem: Largest rectangle in a histogram.
 *
 * Approach: Increasing stack of indices. When bar i is lower than the stack top, the top bar
 * can no longer extend right, so pop it and compute its area: its width spans from the new
 * stack top + 1 to i - 1. A sentinel height 0 at the end flushes the stack.
 *
 * Time O(n), Space O(n).
 *
 * Follow-up: Maximal Rectangle in a binary matrix -> build a histogram per row and reuse this. O(rows*cols).
 */
public class B_LargestRectangleInHistogram {

	static int largestRectangle(int[] h) {
		Deque<Integer> stack = new ArrayDeque<>();
		int best = 0;
		for (int i = 0; i <= h.length; i++) {
			int cur = i == h.length ? 0 : h[i];
			while (!stack.isEmpty() && h[stack.peek()] > cur) {
				int height = h[stack.pop()];
				int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
				best = Math.max(best, height * (i - leftBoundary - 1));
			}
			stack.push(i);
		}
		return best;
	}

	static int maximalRectangle(char[][] matrix) {
		if (matrix.length == 0) return 0;
		int[] heights = new int[matrix[0].length];
		int best = 0;
		for (char[] row : matrix) {
			for (int c = 0; c < row.length; c++) heights[c] = row[c] == '1' ? heights[c] + 1 : 0;
			best = Math.max(best, largestRectangle(heights));
		}
		return best;
	}

	public static void main(String[] args) {
		Check.eq("histogram", largestRectangle(new int[] { 2, 1, 5, 6, 2, 3 }), 10);
		Check.eq("single", largestRectangle(new int[] { 2, 4 }), 4);
		char[][] m = { "10100".toCharArray(), "10111".toCharArray(), "11111".toCharArray(), "10010".toCharArray() };
		Check.eq("maximal rectangle", maximalRectangle(m), 6);
	}
}
