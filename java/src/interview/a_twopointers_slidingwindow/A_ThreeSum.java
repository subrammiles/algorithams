package interview.a_twopointers_slidingwindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * Problem: Return all unique triplets [a, b, c] in nums with a + b + c == 0.
 *
 * Approach: Sort, fix the first element i, then run the classic two-pointer search
 * on the remaining sorted suffix. Skip equal neighbours to avoid duplicate triplets.
 *
 * Time O(n^2), Space O(1) extra (ignoring output and sort).
 *
 * Senior follow-ups:
 *  - kSum: recurse down to 2Sum -> O(n^(k-1)).
 *  - 3Sum closest: same loop, track min |sum - target|.
 *  - Why not hashing? It also gives O(n^2) but de-duplication becomes messy.
 */
public class A_ThreeSum {

	static List<List<Integer>> threeSum(int[] nums) {
		int[] a = nums.clone();
		Arrays.sort(a);
		List<List<Integer>> result = new ArrayList<>();
		for (int i = 0; i < a.length - 2; i++) {
			if (i > 0 && a[i] == a[i - 1]) continue; // duplicate first element
			if (a[i] > 0) break; // sorted: nothing to the right can bring the sum back to 0
			int lo = i + 1, hi = a.length - 1;
			while (lo < hi) {
				int sum = a[i] + a[lo] + a[hi];
				if (sum < 0) {
					lo++;
				} else if (sum > 0) {
					hi--;
				} else {
					result.add(Arrays.asList(a[i], a[lo], a[hi]));
					while (lo < hi && a[lo] == a[lo + 1]) lo++;
					while (lo < hi && a[hi] == a[hi - 1]) hi--;
					lo++;
					hi--;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Check.eq("3sum", threeSum(new int[] { -1, 0, 1, 2, -1, -4 }),
				Arrays.asList(Arrays.asList(-1, -1, 2), Arrays.asList(-1, 0, 1)));
		Check.eq("3sum zeros", threeSum(new int[] { 0, 0, 0, 0 }), Arrays.asList(Arrays.asList(0, 0, 0)));
		Check.eq("3sum none", threeSum(new int[] { 1, 2, 3 }), new ArrayList<>());
	}
}
