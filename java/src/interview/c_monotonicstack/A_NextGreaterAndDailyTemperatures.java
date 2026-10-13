package interview.c_monotonicstack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import common.Check;

/*
 * Pattern: Monotonic stack. Answers "next/previous greater/smaller element" in O(n).
 * Keep a stack of indices whose answer is still unknown; when a new value resolves them, pop.
 *
 * 1. Next greater element (circular array) -> loop twice over indices 0..2n-1 using i % n.
 * 2. Daily temperatures -> distance to the next warmer day.
 * 3. Stock span -> previous greater element.
 */
public class A_NextGreaterAndDailyTemperatures {

	static int[] nextGreaterCircular(int[] nums) {
		int n = nums.length;
		int[] result = new int[n];
		Arrays.fill(result, -1);
		Deque<Integer> stack = new ArrayDeque<>(); // indices, values decreasing
		for (int i = 0; i < 2 * n; i++) {
			int x = nums[i % n];
			while (!stack.isEmpty() && nums[stack.peek()] < x) result[stack.pop()] = x;
			if (i < n) stack.push(i);
		}
		return result;
	}

	static int[] dailyTemperatures(int[] t) {
		int[] wait = new int[t.length];
		Deque<Integer> stack = new ArrayDeque<>();
		for (int i = 0; i < t.length; i++) {
			while (!stack.isEmpty() && t[stack.peek()] < t[i]) {
				int j = stack.pop();
				wait[j] = i - j;
			}
			stack.push(i);
		}
		return wait;
	}

	static int[] stockSpan(int[] prices) {
		int[] span = new int[prices.length];
		Deque<Integer> stack = new ArrayDeque<>();
		for (int i = 0; i < prices.length; i++) {
			while (!stack.isEmpty() && prices[stack.peek()] <= prices[i]) stack.pop();
			span[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
			stack.push(i);
		}
		return span;
	}

	public static void main(String[] args) {
		Check.eq("next greater circular", nextGreaterCircular(new int[] { 1, 2, 1 }), new int[] { 2, -1, 2 });
		Check.eq("daily temps", dailyTemperatures(new int[] { 73, 74, 75, 71, 69, 72, 76, 73 }),
				new int[] { 1, 1, 4, 2, 1, 1, 0, 0 });
		Check.eq("stock span", stockSpan(new int[] { 100, 80, 60, 70, 60, 75, 85 }),
				new int[] { 1, 1, 1, 2, 1, 4, 6 });
	}
}
