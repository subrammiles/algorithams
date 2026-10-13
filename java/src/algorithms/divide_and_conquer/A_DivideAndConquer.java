package algorithms.divide_and_conquer;

import common.Check;

/*
 * Divide and conquer: split the problem, solve the halves recursively, COMBINE the answers.
 * Master theorem for T(n) = aT(n/b) + f(n): two halves + linear combine -> O(n log n).
 *
 *  - Count inversions (pairs i < j with a[i] > a[j]): merge sort; while merging, when an element
 *    from the right half is taken, every remaining left element forms an inversion. O(n log n).
 *    Measures how "unsorted" data is (e.g. ranking similarity).
 *  - Maximum subarray: best is entirely left, entirely right, or crossing the middle. O(n log n).
 *    (Kadane is O(n) - mention both; D&C generalises to segment trees.)
 *  - Majority element: if x is the majority of the whole array it is the majority of a half;
 *    count both candidates in the combine step. O(n log n). (Boyer-Moore is O(n), O(1).)
 */
public class A_DivideAndConquer {

	static long countInversions(int[] nums) {
		int[] a = nums.clone();
		return sortCount(a, new int[a.length], 0, a.length - 1);
	}

	private static long sortCount(int[] a, int[] tmp, int lo, int hi) {
		if (lo >= hi) return 0;
		int mid = (lo + hi) >>> 1;
		long count = sortCount(a, tmp, lo, mid) + sortCount(a, tmp, mid + 1, hi);
		int i = lo, j = mid + 1, k = lo;
		while (i <= mid && j <= hi) {
			if (a[i] <= a[j]) {
				tmp[k++] = a[i++];
			} else {
				count += mid - i + 1; // a[i..mid] are all greater than a[j]
				tmp[k++] = a[j++];
			}
		}
		while (i <= mid) tmp[k++] = a[i++];
		while (j <= hi) tmp[k++] = a[j++];
		System.arraycopy(tmp, lo, a, lo, hi - lo + 1);
		return count;
	}

	static int maxSubarray(int[] a) {
		return maxSub(a, 0, a.length - 1);
	}

	private static int maxSub(int[] a, int lo, int hi) {
		if (lo == hi) return a[lo];
		int mid = (lo + hi) >>> 1;
		int leftBest = Integer.MIN_VALUE, sum = 0;
		for (int i = mid; i >= lo; i--) leftBest = Math.max(leftBest, sum += a[i]);
		int rightBest = Integer.MIN_VALUE;
		sum = 0;
		for (int i = mid + 1; i <= hi; i++) rightBest = Math.max(rightBest, sum += a[i]);
		int crossing = leftBest + rightBest;
		return Math.max(crossing, Math.max(maxSub(a, lo, mid), maxSub(a, mid + 1, hi)));
	}

	static int majorityElement(int[] a) {
		return majority(a, 0, a.length - 1);
	}

	private static int majority(int[] a, int lo, int hi) {
		if (lo == hi) return a[lo];
		int mid = (lo + hi) >>> 1;
		int left = majority(a, lo, mid), right = majority(a, mid + 1, hi);
		if (left == right) return left;
		return count(a, left, lo, hi) > count(a, right, lo, hi) ? left : right;
	}

	private static int count(int[] a, int x, int lo, int hi) {
		int c = 0;
		for (int i = lo; i <= hi; i++) if (a[i] == x) c++;
		return c;
	}

	public static void main(String[] args) {
		Check.eq("inversions", countInversions(new int[] { 2, 4, 1, 3, 5 }), 3L);
		Check.eq("inversions reversed", countInversions(new int[] { 5, 4, 3, 2, 1 }), 10L);
		Check.eq("inversions sorted", countInversions(new int[] { 1, 2, 3 }), 0L);
		Check.eq("max subarray", maxSubarray(new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 }), 6);
		Check.eq("max subarray all negative", maxSubarray(new int[] { -3, -1, -2 }), -1);
		Check.eq("majority", majorityElement(new int[] { 2, 2, 1, 1, 1, 2, 2 }), 2);
	}
}
