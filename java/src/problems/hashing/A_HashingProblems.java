package problems.hashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.Check;

/*
 * Hashing problems: trade O(n) memory for O(1) lookups.
 *
 *  - Two Sum: store value -> index; look up target - x before inserting x. O(n).
 *  - Group anagrams: key = sorted letters (or a 26-count signature, O(L) instead of O(L log L)).
 *  - Longest consecutive sequence in O(n): only start counting from x when x-1 is NOT in the set,
 *    so each run is walked once.
 *  - Contains nearby duplicate (|i - j| <= k): map value -> last index.
 *  - 4Sum II (count a+b+c+d = 0 over four arrays): count all a+b sums, then look up -(c+d).
 *    O(n^2) instead of O(n^4) - "meet in the middle".
 */
public class A_HashingProblems {

	static int[] twoSum(int[] nums, int target) {
		Map<Integer, Integer> seen = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			Integer j = seen.get(target - nums[i]);
			if (j != null) return new int[] { j, i };
			seen.put(nums[i], i);
		}
		return new int[0];
	}

	static List<List<String>> groupAnagrams(String[] words) {
		Map<String, List<String>> groups = new HashMap<>();
		for (String w : words) {
			char[] c = w.toCharArray();
			Arrays.sort(c);
			groups.computeIfAbsent(new String(c), k -> new ArrayList<>()).add(w);
		}
		List<List<String>> out = new ArrayList<>();
		for (List<String> g : groups.values()) {
			g.sort(null);
			out.add(g);
		}
		out.sort((a, b) -> a.get(0).compareTo(b.get(0)));
		return out;
	}

	static int longestConsecutive(int[] nums) {
		Set<Integer> set = new HashSet<>();
		for (int x : nums) set.add(x);
		int best = 0;
		for (int x : set) {
			if (set.contains(x - 1)) continue; // not the start of a run
			int len = 1;
			while (set.contains(x + len)) len++;
			best = Math.max(best, len);
		}
		return best;
	}

	static boolean containsNearbyDuplicate(int[] nums, int k) {
		Map<Integer, Integer> last = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			Integer j = last.put(nums[i], i);
			if (j != null && i - j <= k) return true;
		}
		return false;
	}

	static int fourSumCount(int[] a, int[] b, int[] c, int[] d) {
		Map<Integer, Integer> sums = new HashMap<>();
		for (int x : a) for (int y : b) sums.merge(x + y, 1, Integer::sum);
		int count = 0;
		for (int x : c) for (int y : d) count += sums.getOrDefault(-(x + y), 0);
		return count;
	}

	public static void main(String[] args) {
		Check.eq("two sum", twoSum(new int[] { 2, 7, 11, 15 }, 9), new int[] { 0, 1 });
		Check.eq("anagrams", groupAnagrams(new String[] { "eat", "tea", "tan", "ate", "nat", "bat" }),
				List.of(List.of("ate", "eat", "tea"), List.of("bat"), List.of("nat", "tan")));
		Check.eq("consecutive", longestConsecutive(new int[] { 100, 4, 200, 1, 3, 2 }), 4);
		Check.eq("consecutive 2", longestConsecutive(new int[] { 0, 3, 7, 2, 5, 8, 4, 6, 0, 1 }), 9);
		Check.isTrue("nearby duplicate", containsNearbyDuplicate(new int[] { 1, 2, 3, 1 }, 3));
		Check.eq("nearby too far", containsNearbyDuplicate(new int[] { 1, 2, 3, 1, 2, 3 }, 2), false);
		Check.eq("4sum II", fourSumCount(new int[] { 1, 2 }, new int[] { -2, -1 }, new int[] { -1, 2 }, new int[] { 0, 2 }), 2);
	}
}
