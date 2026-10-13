package interview.a_twopointers_slidingwindow;

import interview.Check;

/*
 * Problem (Longest Repeating Character Replacement): you may replace at most k characters.
 * Return the length of the longest substring that can be made of one repeated letter.
 *
 * Approach: Window is valid while (windowLength - countOfMostFrequentChar) <= k.
 * Trick: maxFreq never needs to decrease. A stale (too high) maxFreq can only keep
 * the window the same size, never report a longer invalid answer, because the answer
 * only grows when a genuinely higher maxFreq appears.
 *
 * Time O(n), Space O(26).
 */
public class F_LongestSubstringWithAtMostKReplacements {

	static int characterReplacement(String s, int k) {
		int[] count = new int[26];
		int left = 0, maxFreq = 0, best = 0;
		for (int right = 0; right < s.length(); right++) {
			maxFreq = Math.max(maxFreq, ++count[s.charAt(right) - 'A']);
			while (right - left + 1 - maxFreq > k) {
				count[s.charAt(left++) - 'A']--;
			}
			best = Math.max(best, right - left + 1);
		}
		return best;
	}

	public static void main(String[] args) {
		Check.eq("ABAB k=2", characterReplacement("ABAB", 2), 4);
		Check.eq("AABABBA k=1", characterReplacement("AABABBA", 1), 4);
	}
}
