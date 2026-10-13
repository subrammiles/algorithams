package interview.b_binarysearch;

import common.Check;

/*
 * Problem: Sorted array rotated at an unknown pivot (distinct values). Find target index or -1.
 *
 * Approach: At every mid, at least one half [lo..mid] or [mid..hi] is sorted.
 * Check whether the target lies inside the sorted half; if so go there, else go to the other half.
 *
 * Time O(log n).
 *
 * Senior follow-ups:
 *  - With duplicates worst case degrades to O(n) (e.g. 1,1,1,1,0,1). Handle a[lo]==a[mid]==a[hi] by lo++, hi--.
 *  - Find the rotation point (min element) with the same idea: compare a[mid] with a[hi].
 */
public class A_SearchInRotatedSortedArray {

	static int search(int[] a, int target) {
		int lo = 0, hi = a.length - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2; // avoids int overflow of (lo + hi)
			if (a[mid] == target) return mid;
			if (a[lo] <= a[mid]) { // left half sorted
				if (a[lo] <= target && target < a[mid]) hi = mid - 1;
				else lo = mid + 1;
			} else { // right half sorted
				if (a[mid] < target && target <= a[hi]) lo = mid + 1;
				else hi = mid - 1;
			}
		}
		return -1;
	}

	static int findMinIndex(int[] a) {
		int lo = 0, hi = a.length - 1;
		while (lo < hi) {
			int mid = lo + (hi - lo) / 2;
			if (a[mid] > a[hi]) lo = mid + 1;
			else hi = mid;
		}
		return lo;
	}

	public static void main(String[] args) {
		int[] a = { 4, 5, 6, 7, 0, 1, 2 };
		Check.eq("found", search(a, 0), 4);
		Check.eq("missing", search(a, 3), -1);
		Check.eq("not rotated", search(new int[] { 1, 3 }, 3), 1);
		Check.eq("rotation point", findMinIndex(a), 4);
	}
}
