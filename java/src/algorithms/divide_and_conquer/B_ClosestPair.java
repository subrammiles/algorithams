package algorithms.divide_and_conquer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import common.Check;

/*
 * Closest pair of points - the classic divide-and-conquer geometry problem
 * (older version: ClosestPair.java in this folder).
 *
 * Brute force compares all pairs: O(n^2).
 * Divide and conquer, O(n log n):
 *   1. Sort by x once. Split at the middle x into left and right halves.
 *   2. d = min(closest(left), closest(right)).
 *   3. The only pairs left to check straddle the split line, and both points must lie in the
 *      vertical STRIP |x - midX| < d.
 *   4. Sort the strip by y. For each point, only the next points with y - p.y < d can be closer,
 *      and a packing argument (d x 2d box) shows that is at most 7 points -> the strip costs O(n).
 *   To keep each level O(n) instead of O(n log n), merge the halves by y on the way back up
 *   (exactly like merge sort), so the strip is already sorted.
 *   T(n) = 2T(n/2) + O(n) = O(n log n).
 *
 * Interview notes: the "why only 7 neighbours" argument is the part interviewers probe.
 * Practical alternative: a uniform grid / hashing with cell size d gives expected O(n).
 */
public class B_ClosestPair {

	static double closestPair(double[][] points) {
		double[][] p = points.clone();
		Arrays.sort(p, Comparator.comparingDouble(a -> a[0]));
		return closest(p, 0, p.length, new double[p.length][]);
	}

	// closest distance in p[lo, hi); on return p[lo, hi) is sorted by y
	private static double closest(double[][] p, int lo, int hi, double[][] aux) {
		if (hi - lo <= 3) {
			double best = Double.POSITIVE_INFINITY;
			for (int i = lo; i < hi; i++) for (int j = i + 1; j < hi; j++) best = Math.min(best, dist(p[i], p[j]));
			Arrays.sort(p, lo, hi, Comparator.comparingDouble(a -> a[1]));
			return best;
		}
		int mid = (lo + hi) >>> 1;
		double midX = p[mid][0];
		double d = Math.min(closest(p, lo, mid, aux), closest(p, mid, hi, aux));

		// merge the two y-sorted halves
		int i = lo, j = mid, k = lo;
		while (i < mid || j < hi) {
			if (j >= hi || (i < mid && p[i][1] <= p[j][1])) aux[k++] = p[i++];
			else aux[k++] = p[j++];
		}
		System.arraycopy(aux, lo, p, lo, hi - lo);

		// check the strip, already in y order
		double[][] strip = new double[hi - lo][];
		int size = 0;
		for (int t = lo; t < hi; t++) if (Math.abs(p[t][0] - midX) < d) strip[size++] = p[t];
		for (int a = 0; a < size; a++) {
			for (int b = a + 1; b < size && strip[b][1] - strip[a][1] < d; b++) d = Math.min(d, dist(strip[a], strip[b]));
		}
		return d;
	}

	static double bruteForce(double[][] p) {
		double best = Double.POSITIVE_INFINITY;
		for (int i = 0; i < p.length; i++) for (int j = i + 1; j < p.length; j++) best = Math.min(best, dist(p[i], p[j]));
		return best;
	}

	private static double dist(double[] a, double[] b) {
		return Math.hypot(a[0] - b[0], a[1] - b[1]);
	}

	public static void main(String[] args) {
		double[][] pts = { { 2, 3 }, { 12, 30 }, { 40, 50 }, { 5, 1 }, { 12, 10 }, { 3, 4 } };
		Check.near("classic example", closestPair(pts), Math.sqrt(2));
		Check.near("duplicate points", closestPair(new double[][] { { 1, 1 }, { 5, 5 }, { 1, 1 } }), 0);
		Check.near("two points", closestPair(new double[][] { { 0, 0 }, { 3, 4 } }), 5);

		Random random = new Random(3);
		for (int trial = 0; trial < 20; trial++) {
			double[][] p = new double[300][];
			for (int i = 0; i < p.length; i++) p[i] = new double[] { random.nextDouble() * 1000, random.nextDouble() * 1000 };
			if (Math.abs(closestPair(p) - bruteForce(p)) > 1e-9) throw new AssertionError("FAIL random trial " + trial);
		}
		System.out.println("PASS 20 random trials match brute force");
	}
}
