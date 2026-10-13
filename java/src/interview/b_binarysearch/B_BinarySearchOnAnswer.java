package interview.b_binarysearch;

import common.Check;

/*
 * Pattern: "Binary search on the answer".
 * When the question is "minimise the maximum" / "minimum speed/capacity such that ..." and
 * feasible(x) is monotonic (false false ... true true), binary search for the first true.
 *
 * Example 1 - Koko Eating Bananas: minimum eating speed to finish all piles within h hours.
 * Example 2 - Split Array Largest Sum: split into m parts minimising the largest part sum
 *             (same as "ship packages within D days" / "allocate books").
 *
 * Time O(n log(range)).
 */
public class B_BinarySearchOnAnswer {

	static int minEatingSpeed(int[] piles, int h) {
		int lo = 1, hi = 0;
		for (int p : piles) hi = Math.max(hi, p);
		while (lo < hi) { // find first speed that is feasible
			int mid = lo + (hi - lo) / 2;
			long hours = 0;
			for (int p : piles) hours += (p + mid - 1) / mid; // ceil(p / mid)
			if (hours <= h) hi = mid;
			else lo = mid + 1;
		}
		return lo;
	}

	static int splitArray(int[] nums, int m) {
		long lo = 0, hi = 0;
		for (int x : nums) {
			lo = Math.max(lo, x); // a part must hold the largest element
			hi += x; // one part holds everything
		}
		while (lo < hi) {
			long mid = lo + (hi - lo) / 2;
			if (partsNeeded(nums, mid) <= m) hi = mid;
			else lo = mid + 1;
		}
		return (int) lo;
	}

	private static int partsNeeded(int[] nums, long maxSum) {
		int parts = 1;
		long running = 0;
		for (int x : nums) {
			if (running + x > maxSum) {
				parts++;
				running = 0;
			}
			running += x;
		}
		return parts;
	}

	public static void main(String[] args) {
		Check.eq("koko", minEatingSpeed(new int[] { 3, 6, 7, 11 }, 8), 4);
		Check.eq("koko 2", minEatingSpeed(new int[] { 30, 11, 23, 4, 20 }, 5), 30);
		Check.eq("split", splitArray(new int[] { 7, 2, 5, 10, 8 }, 2), 18);
		Check.eq("split 2", splitArray(new int[] { 1, 2, 3, 4, 5 }, 2), 9);
	}
}
