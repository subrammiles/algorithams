package algorithms.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * More choose / explore / un-choose variants (subsets II, permutations II and combination sum
 * are in interview/l_backtracking).
 *
 *  - Combination Sum II: each number used once, input has duplicates -> sort, skip equal
 *    neighbours at the same depth, recurse with i + 1.
 *  - Letter case permutation: at each letter branch into lower/upper; digits have one branch. O(2^L).
 *  - Combinations C(n, k): prune when not enough numbers remain (i <= n - (k - size) + 1).
 *  - Permutations of distinct numbers with a used[] array. O(n * n!).
 *  - Subsets via bitmask: every mask 0..2^n-1 is one subset - no recursion needed.
 */
public class C_CombinationVariants {

	static List<List<Integer>> combinationSum2(int[] candidates, int target) {
		int[] a = candidates.clone();
		Arrays.sort(a);
		List<List<Integer>> out = new ArrayList<>();
		comb2(a, target, 0, new ArrayList<>(), out);
		return out;
	}

	private static void comb2(int[] a, int remaining, int start, List<Integer> cur, List<List<Integer>> out) {
		if (remaining == 0) {
			out.add(new ArrayList<>(cur));
			return;
		}
		for (int i = start; i < a.length && a[i] <= remaining; i++) {
			if (i > start && a[i] == a[i - 1]) continue;
			cur.add(a[i]);
			comb2(a, remaining - a[i], i + 1, cur, out);
			cur.remove(cur.size() - 1);
		}
	}

	static List<String> letterCasePermutation(String s) {
		List<String> out = new ArrayList<>();
		casePerm(s.toCharArray(), 0, out);
		out.sort(null);
		return out;
	}

	private static void casePerm(char[] c, int i, List<String> out) {
		if (i == c.length) {
			out.add(new String(c));
			return;
		}
		casePerm(c, i + 1, out);
		if (Character.isLetter(c[i])) {
			c[i] ^= 32; // flips the case of an ASCII letter
			casePerm(c, i + 1, out);
			c[i] ^= 32;
		}
	}

	static List<List<Integer>> combine(int n, int k) {
		List<List<Integer>> out = new ArrayList<>();
		combine(n, k, 1, new ArrayList<>(), out);
		return out;
	}

	private static void combine(int n, int k, int start, List<Integer> cur, List<List<Integer>> out) {
		if (cur.size() == k) {
			out.add(new ArrayList<>(cur));
			return;
		}
		for (int i = start; i <= n - (k - cur.size()) + 1; i++) {
			cur.add(i);
			combine(n, k, i + 1, cur, out);
			cur.remove(cur.size() - 1);
		}
	}

	static List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> out = new ArrayList<>();
		permute(nums, new boolean[nums.length], new ArrayList<>(), out);
		return out;
	}

	private static void permute(int[] nums, boolean[] used, List<Integer> cur, List<List<Integer>> out) {
		if (cur.size() == nums.length) {
			out.add(new ArrayList<>(cur));
			return;
		}
		for (int i = 0; i < nums.length; i++) {
			if (used[i]) continue;
			used[i] = true;
			cur.add(nums[i]);
			permute(nums, used, cur, out);
			cur.remove(cur.size() - 1);
			used[i] = false;
		}
	}

	static List<List<Integer>> subsetsBitmask(int[] nums) {
		List<List<Integer>> out = new ArrayList<>();
		for (int mask = 0; mask < 1 << nums.length; mask++) {
			List<Integer> subset = new ArrayList<>();
			for (int i = 0; i < nums.length; i++) if ((mask >> i & 1) == 1) subset.add(nums[i]);
			out.add(subset);
		}
		return out;
	}

	public static void main(String[] args) {
		Check.eq("combination sum II", combinationSum2(new int[] { 10, 1, 2, 7, 6, 1, 5 }, 8),
				List.of(List.of(1, 1, 6), List.of(1, 2, 5), List.of(1, 7), List.of(2, 6)));
		Check.eq("letter case", letterCasePermutation("a1b2"), List.of("A1B2", "A1b2", "a1B2", "a1b2"));
		Check.eq("combine 4 choose 2", combine(4, 2), List.of(List.of(1, 2), List.of(1, 3), List.of(1, 4), List.of(2, 3), List.of(2, 4), List.of(3, 4)));
		Check.eq("permute", permute(new int[] { 1, 2, 3 }),
				List.of(List.of(1, 2, 3), List.of(1, 3, 2), List.of(2, 1, 3), List.of(2, 3, 1), List.of(3, 1, 2), List.of(3, 2, 1)));
		Check.eq("subsets bitmask", subsetsBitmask(new int[] { 1, 2, 3 }),
				List.of(List.of(), List.of(1), List.of(2), List.of(1, 2), List.of(3), List.of(1, 3), List.of(2, 3), List.of(1, 2, 3)));
	}
}
