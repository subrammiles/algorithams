package interview.b_binarysearch;

import common.Check;

/*
 * Problem: Median of two sorted arrays in O(log(min(m, n))).
 *
 * Approach: Binary search a cut i in the smaller array A, which forces cut j in B so that
 * the left side holds half of all elements. The cut is correct when
 *     A[i-1] <= B[j]  and  B[j-1] <= A[i].
 * Use -inf/+inf sentinels for empty sides.
 *
 * This is a classic "hard" question; explain the partition idea before coding.
 */
public class C_MedianOfTwoSortedArrays {

	static double findMedian(int[] a, int[] b) {
		if (a.length > b.length) return findMedian(b, a);
		int m = a.length, n = b.length, half = (m + n + 1) / 2;
		int lo = 0, hi = m;
		while (lo <= hi) {
			int i = (lo + hi) / 2;
			int j = half - i;
			int aLeft = i == 0 ? Integer.MIN_VALUE : a[i - 1];
			int aRight = i == m ? Integer.MAX_VALUE : a[i];
			int bLeft = j == 0 ? Integer.MIN_VALUE : b[j - 1];
			int bRight = j == n ? Integer.MAX_VALUE : b[j];
			if (aLeft <= bRight && bLeft <= aRight) {
				int leftMax = Math.max(aLeft, bLeft);
				if ((m + n) % 2 == 1) return leftMax;
				return (leftMax + (double) Math.min(aRight, bRight)) / 2.0;
			} else if (aLeft > bRight) {
				hi = i - 1;
			} else {
				lo = i + 1;
			}
		}
		throw new IllegalArgumentException("Input arrays are not sorted");
	}

	public static void main(String[] args) {
		Check.near("odd", findMedian(new int[] { 1, 3 }, new int[] { 2 }), 2.0);
		Check.near("even", findMedian(new int[] { 1, 2 }, new int[] { 3, 4 }), 2.5);
		Check.near("one empty", findMedian(new int[] {}, new int[] { 1 }), 1.0);
	}
}
