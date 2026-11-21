package interview.a_twopointers_slidingwindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Check;

/*
 * The three sliding-window templates. Learn the shape, not the problems.
 *
 * A) Variable window, "longest valid":        grow right every step; while invalid, shrink left;
 *                                              record the answer AFTER the window is valid again.
 *      for (right...) { add(a[right]); while (!valid) remove(a[left++]); best = max(best, right-left+1); }
 *
 * B) Variable window, "shortest valid":       grow right; while valid, record and shrink left.
 *      for (right...) { add(a[right]); while (valid) { best = min(best, len); remove(a[left++]); } }
 *
 * C) Fixed window of size k:                  add a[right]; once right >= k-1, record; remove a[right-k+1].
 *
 * Plus the counting trick: exactly(K) = atMost(K) - atMost(K - 1), because "at most K" is
 * monotone (shrinking a valid window keeps it valid) while "exactly K" is not.
 *
 * Problems:
 *  1. Longest substring without repeating characters (A). Jump left straight past the last
 *     occurrence: left = max(left, last[c] + 1). The max() matters: last[c] may be left of the window.
 *  2. Find all anagrams of p in s (C). Compare two 26-counters, or keep a "matches" count for O(1).
 *  3. Max consecutive ones with at most k flips (A). Window is valid while zeros <= k.
 *  4. Subarrays with exactly K distinct integers (atMost trick on template A, counting right-left+1
 *     subarrays that end at right).
 *  5. Minimum size subarray with sum >= target (B). Only valid because all numbers are positive -
 *     with negatives the window is not monotone: use prefix sums + a monotonic deque instead.
 *
 * Senior follow-ups:
 *  - Why is it O(n)? Each index enters and leaves the window at most once (amortised).
 *  - Unicode / large alphabets: HashMap instead of int[26] / int[128].
 *  - Streaming input: the window state is all you keep, so it works on an unbounded stream.
 */
public class G_SlidingWindowTemplates {

	static int lengthOfLongestSubstring(String s) {
		Map<Character, Integer> last = new HashMap<>();
		int best = 0;
		for (int left = 0, right = 0; right < s.length(); right++) {
			char c = s.charAt(right);
			if (last.containsKey(c)) left = Math.max(left, last.get(c) + 1);
			last.put(c, right);
			best = Math.max(best, right - left + 1);
		}
		return best;
	}

	static List<Integer> findAnagrams(String s, String p) {
		List<Integer> out = new ArrayList<>();
		int k = p.length();
		if (k > s.length()) return out;
		int[] need = new int[26], window = new int[26];
		for (char c : p.toCharArray()) need[c - 'a']++;
		for (int right = 0; right < s.length(); right++) {
			window[s.charAt(right) - 'a']++;
			if (right >= k) window[s.charAt(right - k) - 'a']--; // drop the char that left the window
			if (right >= k - 1 && Arrays.equals(need, window)) out.add(right - k + 1);
		}
		return out;
	}

	static int longestOnes(int[] nums, int k) {
		int best = 0, zeros = 0;
		for (int left = 0, right = 0; right < nums.length; right++) {
			if (nums[right] == 0) zeros++;
			while (zeros > k) {
				if (nums[left++] == 0) zeros--;
			}
			best = Math.max(best, right - left + 1);
		}
		return best;
	}

	static int subarraysWithKDistinct(int[] nums, int k) {
		return atMostKDistinct(nums, k) - atMostKDistinct(nums, k - 1);
	}

	private static int atMostKDistinct(int[] nums, int k) {
		Map<Integer, Integer> count = new HashMap<>();
		int total = 0;
		for (int left = 0, right = 0; right < nums.length; right++) {
			count.merge(nums[right], 1, Integer::sum);
			while (count.size() > k) {
				if (count.merge(nums[left], -1, Integer::sum) == 0) count.remove(nums[left]);
				left++;
			}
			total += right - left + 1; // every subarray ending at right and starting in [left, right]
		}
		return total;
	}

	static int minSubArrayLen(int target, int[] nums) {
		int best = Integer.MAX_VALUE, sum = 0;
		for (int left = 0, right = 0; right < nums.length; right++) {
			sum += nums[right];
			while (sum >= target) {
				best = Math.min(best, right - left + 1);
				sum -= nums[left++];
			}
		}
		return best == Integer.MAX_VALUE ? 0 : best;
	}

	public static void main(String[] args) {
		Check.eq("no repeat abcabcbb", lengthOfLongestSubstring("abcabcbb"), 3);
		Check.eq("no repeat bbbbb", lengthOfLongestSubstring("bbbbb"), 1);
		Check.eq("no repeat pwwkew", lengthOfLongestSubstring("pwwkew"), 3);
		Check.eq("no repeat abba (needs the max)", lengthOfLongestSubstring("abba"), 2);
		Check.eq("no repeat empty", lengthOfLongestSubstring(""), 0);

		Check.eq("anagrams", findAnagrams("cbaebabacd", "abc"), List.of(0, 6));
		Check.eq("anagrams overlap", findAnagrams("abab", "ab"), List.of(0, 1, 2));

		Check.eq("longest ones k=2", longestOnes(new int[] { 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 }, 2), 6);
		Check.eq("longest ones k=3",
				longestOnes(new int[] { 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1 }, 3), 10);

		Check.eq("exactly 2 distinct", subarraysWithKDistinct(new int[] { 1, 2, 1, 2, 3 }, 2), 7);
		Check.eq("exactly 3 distinct", subarraysWithKDistinct(new int[] { 1, 2, 1, 3, 4 }, 3), 3);

		Check.eq("min len sum>=7", minSubArrayLen(7, new int[] { 2, 3, 1, 2, 4, 3 }), 2);
		Check.eq("min len sum>=4", minSubArrayLen(4, new int[] { 1, 4, 4 }), 1);
		Check.eq("min len impossible", minSubArrayLen(11, new int[] { 1, 1, 1, 1, 1, 1, 1, 1 }), 0);
	}
}
