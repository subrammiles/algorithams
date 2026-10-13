package interview.a_twopointers_slidingwindow;

import common.Check;

/*
 * Problem: Smallest substring of s that contains every character of t (with multiplicity).
 *
 * Approach: Variable-size sliding window.
 *  - Expand right until the window is "valid" (missing == 0).
 *  - Then shrink left while still valid, recording the best window.
 * Using an int[128] count table instead of HashMap keeps it fast.
 *
 * Time O(|s| + |t|), Space O(charset).
 *
 * Template (memorise it, it solves a whole family of problems):
 *   for right in s: add s[right]
 *       while window valid: update answer; remove s[left++]
 */
public class C_MinimumWindowSubstring {

	static String minWindow(String s, String t) {
		int[] need = new int[128];
		for (char c : t.toCharArray()) need[c]++;
		int missing = t.length();
		int bestStart = 0, bestLen = Integer.MAX_VALUE;
		int left = 0;
		for (int right = 0; right < s.length(); right++) {
			if (need[s.charAt(right)]-- > 0) missing--;
			while (missing == 0) {
				if (right - left + 1 < bestLen) {
					bestLen = right - left + 1;
					bestStart = left;
				}
				if (++need[s.charAt(left++)] > 0) missing++;
			}
		}
		return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLen);
	}

	public static void main(String[] args) {
		Check.eq("basic", minWindow("ADOBECODEBANC", "ABC"), "BANC");
		Check.eq("same", minWindow("a", "a"), "a");
		Check.eq("impossible", minWindow("a", "aa"), "");
	}
}
