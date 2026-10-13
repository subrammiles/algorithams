package algorithms.searching;

import common.Check;

/*
 * Binary search variants. Most bugs come from the loop condition and the update; one template
 * avoids them: search for the FIRST index where a monotonic condition becomes true, on [lo, hi).
 *
 *     lo = 0, hi = n
 *     while lo < hi: mid = lo + (hi - lo) / 2; if cond(mid) hi = mid else lo = mid + 1
 *     return lo        // n if the condition is never true
 *
 *  - lowerBound(x): first index with a[i] >= x.     upperBound(x): first index with a[i] > x.
 *  - first/last position of x: [lowerBound(x), upperBound(x) - 1].
 *  - Peak element: compare a[mid] with a[mid+1] and climb uphill. O(log n), any peak.
 *  - Integer square root: last x with x*x <= n (use long to avoid overflow).
 *  - Search a matrix whose rows continue each other: treat it as one sorted array of r*c items.
 *  - Ternary search: maximum of a unimodal array/function.
 */
public class A_BinarySearchVariants {

	static int lowerBound(int[] a, int x) {
		int lo = 0, hi = a.length;
		while (lo < hi) {
			int mid = lo + (hi - lo) / 2;
			if (a[mid] >= x) hi = mid;
			else lo = mid + 1;
		}
		return lo;
	}

	static int upperBound(int[] a, int x) {
		int lo = 0, hi = a.length;
		while (lo < hi) {
			int mid = lo + (hi - lo) / 2;
			if (a[mid] > x) hi = mid;
			else lo = mid + 1;
		}
		return lo;
	}

	static int[] searchRange(int[] a, int x) {
		int first = lowerBound(a, x);
		if (first == a.length || a[first] != x) return new int[] { -1, -1 };
		return new int[] { first, upperBound(a, x) - 1 };
	}

	static int findPeakElement(int[] a) {
		int lo = 0, hi = a.length - 1;
		while (lo < hi) {
			int mid = lo + (hi - lo) / 2;
			if (a[mid] < a[mid + 1]) lo = mid + 1; // uphill to the right
			else hi = mid;
		}
		return lo;
	}

	static int mySqrt(int n) {
		long lo = 0, hi = (long) n + 1; // first x with x*x > n, minus one
		while (lo < hi) {
			long mid = (lo + hi) / 2;
			if (mid * mid > n) hi = mid;
			else lo = mid + 1;
		}
		return (int) (lo - 1);
	}

	static boolean searchMatrix(int[][] m, int target) {
		int rows = m.length, cols = m[0].length, lo = 0, hi = rows * cols - 1;
		while (lo <= hi) {
			int mid = (lo + hi) >>> 1;
			int v = m[mid / cols][mid % cols];
			if (v == target) return true;
			if (v < target) lo = mid + 1;
			else hi = mid - 1;
		}
		return false;
	}

	static int ternarySearchMax(int[] unimodal) {
		int lo = 0, hi = unimodal.length - 1;
		while (hi - lo > 2) {
			int m1 = lo + (hi - lo) / 3, m2 = hi - (hi - lo) / 3;
			if (unimodal[m1] < unimodal[m2]) lo = m1 + 1;
			else hi = m2 - 1;
		}
		int best = lo;
		for (int i = lo; i <= hi; i++) if (unimodal[i] > unimodal[best]) best = i;
		return best;
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, 2, 2, 3 };
		Check.eq("lower bound", lowerBound(a, 2), 1);
		Check.eq("upper bound", upperBound(a, 2), 4);
		Check.eq("lower bound past end", lowerBound(a, 9), 5);
		Check.eq("range", searchRange(new int[] { 5, 7, 7, 8, 8, 10 }, 8), new int[] { 3, 4 });
		Check.eq("range missing", searchRange(new int[] { 5, 7, 7, 8, 8, 10 }, 6), new int[] { -1, -1 });
		int[] peaks = { 1, 2, 1, 3, 5, 6, 4 };
		int p = findPeakElement(peaks);
		Check.isTrue("peak at " + p, (p == 0 || peaks[p] > peaks[p - 1]) && (p == peaks.length - 1 || peaks[p] > peaks[p + 1]));
		Check.eq("sqrt 8", mySqrt(8), 2);
		Check.eq("sqrt large", mySqrt(2147395599), 46339);
		Check.eq("sqrt max int", mySqrt(Integer.MAX_VALUE), 46340);
		int[][] m = { { 1, 3, 5, 7 }, { 10, 11, 16, 20 }, { 23, 30, 34, 60 } };
		Check.isTrue("matrix 3", searchMatrix(m, 3));
		Check.eq("matrix 13", searchMatrix(m, 13), false);
		Check.eq("ternary max", ternarySearchMax(new int[] { 1, 3, 7, 9, 8, 4, 2 }), 3);
	}
}
