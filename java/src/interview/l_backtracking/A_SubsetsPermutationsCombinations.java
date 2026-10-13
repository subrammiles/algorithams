package interview.l_backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import interview.Check;

/*
 * One backtracking template covers subsets, permutations and combinations:
 *
 *   void backtrack(state, start):
 *       if goal reached: record a copy of state
 *       for choice in choices(start):
 *           if invalid: continue          // prune
 *           make choice; backtrack(...); undo choice
 *
 * Handling duplicates: sort first, then skip nums[i] == nums[i-1] at the same depth (i > start).
 *
 * Output sizes (state these!): subsets 2^n, permutations n!, combinations C(n, k).
 */
public class A_SubsetsPermutationsCombinations {

	static List<List<Integer>> subsetsWithDup(int[] nums) {
		int[] a = nums.clone();
		Arrays.sort(a);
		List<List<Integer>> out = new ArrayList<>();
		subsets(a, 0, new ArrayList<>(), out);
		return out;
	}

	private static void subsets(int[] a, int start, List<Integer> cur, List<List<Integer>> out) {
		out.add(new ArrayList<>(cur));
		for (int i = start; i < a.length; i++) {
			if (i > start && a[i] == a[i - 1]) continue;
			cur.add(a[i]);
			subsets(a, i + 1, cur, out);
			cur.remove(cur.size() - 1);
		}
	}

	static List<List<Integer>> permuteUnique(int[] nums) {
		int[] a = nums.clone();
		Arrays.sort(a);
		List<List<Integer>> out = new ArrayList<>();
		permute(a, new boolean[a.length], new ArrayList<>(), out);
		return out;
	}

	private static void permute(int[] a, boolean[] used, List<Integer> cur, List<List<Integer>> out) {
		if (cur.size() == a.length) {
			out.add(new ArrayList<>(cur));
			return;
		}
		for (int i = 0; i < a.length; i++) {
			if (used[i]) continue;
			if (i > 0 && a[i] == a[i - 1] && !used[i - 1]) continue; // use equal values left-to-right only
			used[i] = true;
			cur.add(a[i]);
			permute(a, used, cur, out);
			cur.remove(cur.size() - 1);
			used[i] = false;
		}
	}

	static List<List<Integer>> combinationSum(int[] candidates, int target) {
		int[] a = candidates.clone();
		Arrays.sort(a);
		List<List<Integer>> out = new ArrayList<>();
		combine(a, target, 0, new ArrayList<>(), out);
		return out;
	}

	private static void combine(int[] a, int remaining, int start, List<Integer> cur, List<List<Integer>> out) {
		if (remaining == 0) {
			out.add(new ArrayList<>(cur));
			return;
		}
		for (int i = start; i < a.length && a[i] <= remaining; i++) { // sorted -> prune early
			cur.add(a[i]);
			combine(a, remaining - a[i], i, cur, out); // i, not i+1: reuse allowed
			cur.remove(cur.size() - 1);
		}
	}

	public static void main(String[] args) {
		Check.eq("subsets with dup", subsetsWithDup(new int[] { 1, 2, 2 }), Arrays.asList(Arrays.asList(),
				Arrays.asList(1), Arrays.asList(1, 2), Arrays.asList(1, 2, 2), Arrays.asList(2), Arrays.asList(2, 2)));
		Check.eq("permute unique", permuteUnique(new int[] { 1, 1, 2 }),
				Arrays.asList(Arrays.asList(1, 1, 2), Arrays.asList(1, 2, 1), Arrays.asList(2, 1, 1)));
		Check.eq("combination sum", combinationSum(new int[] { 2, 3, 6, 7 }, 7),
				Arrays.asList(Arrays.asList(2, 2, 3), Arrays.asList(7)));
	}
}
