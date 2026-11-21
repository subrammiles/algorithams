package interview.c_monotonicstack;

import java.util.ArrayDeque;
import java.util.Deque;

import common.Check;

/*
 * Three harder monotonic-stack problems that interviewers use to separate "knows the template"
 * from "understands it".
 *
 * 1. Sum of subarray minimums - the CONTRIBUTION technique.
 *    Instead of enumerating O(n^2) subarrays, ask: in how many subarrays is a[i] the minimum?
 *      left[i]  = distance to the previous element  < a[i]   (strictly smaller)
 *      right[i] = distance to the next element     <= a[i]   (smaller or equal)
 *    a[i] is the minimum of left[i] * right[i] subarrays, so answer = sum a[i] * left[i] * right[i].
 *    Trap: using < on both sides double-counts subarrays with equal minimums; using <= on both
 *    misses them. One strict side and one non-strict side breaks ties consistently.
 *    O(n) with two monotonic-stack passes. Result mod 1e9+7.
 *    Same trick: sum of subarray ranges (= sum of maxima - sum of minima), count of subarrays where
 *    a[i] is the max, "number of visible people".
 *
 * 2. Remove duplicate letters / smallest subsequence of distinct characters - GREEDY stack.
 *    Keep the stack increasing. Before pushing c, pop larger top characters that appear again later
 *    (we can take them later and get a smaller string). Skip c if it is already in the stack.
 *    "bcabc" -> "abc", "cbacdcbc" -> "acdb". O(n). Related: remove k digits (greedy/E_StringGreedy).
 *
 * 3. 132 pattern - is there i < j < k with a[i] < a[k] < a[j]?
 *    Scan from the right with a decreasing stack. Anything popped is a valid "2" (it had a bigger
 *    "3" to its left); keep the largest popped value as `third`. If some a[i] < third, found.
 *    O(n). The brute force is O(n^3), the prefix-min + TreeSet answer O(n log n).
 *
 * Senior follow-ups:
 *  - Why is a monotonic stack O(n)? Each element is pushed and popped at most once.
 *  - What is the stack invariant? Say it before coding: "indices with increasing values".
 */
public class C_MonotonicStackAdvanced {

	static final int MOD = 1_000_000_007;

	static int sumSubarrayMins(int[] a) {
		int n = a.length;
		int[] left = new int[n], right = new int[n];
		Deque<Integer> stack = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			while (!stack.isEmpty() && a[stack.peek()] >= a[i]) stack.pop(); // previous strictly smaller survives
			left[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
			stack.push(i);
		}
		stack.clear();
		for (int i = n - 1; i >= 0; i--) {
			while (!stack.isEmpty() && a[stack.peek()] > a[i]) stack.pop(); // next smaller-or-equal survives
			right[i] = stack.isEmpty() ? n - i : stack.peek() - i;
			stack.push(i);
		}
		long total = 0;
		for (int i = 0; i < n; i++) total = (total + (long) a[i] * left[i] % MOD * right[i]) % MOD;
		return (int) total;
	}

	static String removeDuplicateLetters(String s) {
		int[] last = new int[26];
		for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
		boolean[] inStack = new boolean[26];
		StringBuilder stack = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (inStack[c - 'a']) continue;
			while (stack.length() > 0) {
				char top = stack.charAt(stack.length() - 1);
				if (top <= c || last[top - 'a'] < i) break; // keep top: smaller, or never seen again
				inStack[top - 'a'] = false;
				stack.deleteCharAt(stack.length() - 1);
			}
			stack.append(c);
			inStack[c - 'a'] = true;
		}
		return stack.toString();
	}

	static boolean find132pattern(int[] a) {
		Deque<Integer> stack = new ArrayDeque<>(); // decreasing from bottom to top
		long third = Long.MIN_VALUE; // best "2" so far
		for (int i = a.length - 1; i >= 0; i--) {
			if (a[i] < third) return true; // a[i] is the "1"
			while (!stack.isEmpty() && stack.peek() < a[i]) third = stack.pop(); // a[i] is a "3"
			stack.push(a[i]);
		}
		return false;
	}

	public static void main(String[] args) {
		Check.eq("sum of mins [3,1,2,4]", sumSubarrayMins(new int[] { 3, 1, 2, 4 }), 17);
		Check.eq("sum of mins [11,81,94,43,3]", sumSubarrayMins(new int[] { 11, 81, 94, 43, 3 }), 444);
		Check.eq("sum of mins with ties [2,2,2]", sumSubarrayMins(new int[] { 2, 2, 2 }), 12);

		Check.eq("remove dup letters bcabc", removeDuplicateLetters("bcabc"), "abc");
		Check.eq("remove dup letters cbacdcbc", removeDuplicateLetters("cbacdcbc"), "acdb");

		Check.eq("132 in [3,1,4,2]", find132pattern(new int[] { 3, 1, 4, 2 }), true);
		Check.eq("132 in [1,2,3,4]", find132pattern(new int[] { 1, 2, 3, 4 }), false);
		Check.eq("132 in [-1,3,2,0]", find132pattern(new int[] { -1, 3, 2, 0 }), true);
	}
}
