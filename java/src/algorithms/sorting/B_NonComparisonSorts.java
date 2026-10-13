package algorithms.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import common.Check;

/*
 * Non-comparison sorts beat the n log n bound by using the KEYS themselves as indexes.
 *
 *  - Counting sort: count each value, prefix-sum the counts to get final positions, place items
 *    walking backwards (keeps it stable). O(n + k) for value range k. Offset by min for negatives.
 *  - Radix sort (LSD): stable counting sort on each digit, least significant first.
 *    O(d * (n + base)). Works because each pass is stable.
 *  - Bucket sort: for uniformly distributed values in [0, 1): n buckets, sort each (tiny) bucket,
 *    concatenate. O(n) on average, O(n^2) if everything lands in one bucket.
 */
public class B_NonComparisonSorts {

	static int[] countingSort(int[] a) {
		if (a.length == 0) return a.clone();
		int min = Arrays.stream(a).min().getAsInt(), max = Arrays.stream(a).max().getAsInt();
		int[] count = new int[max - min + 1];
		for (int x : a) count[x - min]++;
		for (int i = 1; i < count.length; i++) count[i] += count[i - 1]; // count[v] = end position of v
		int[] out = new int[a.length];
		for (int i = a.length - 1; i >= 0; i--) out[--count[a[i] - min]] = a[i]; // backwards = stable
		return out;
	}

	/* Non-negative integers. */
	static int[] radixSort(int[] input) {
		int[] a = input.clone(), out = new int[a.length];
		int max = Arrays.stream(a).max().orElse(0);
		for (long exp = 1; max / exp > 0; exp *= 10) {
			int[] count = new int[10];
			for (int x : a) count[(int) (x / exp % 10)]++;
			for (int d = 1; d < 10; d++) count[d] += count[d - 1];
			for (int i = a.length - 1; i >= 0; i--) out[--count[(int) (a[i] / exp % 10)]] = a[i];
			int[] t = a;
			a = out;
			out = t;
		}
		return a;
	}

	/* Values in [0, 1). */
	static double[] bucketSort(double[] a) {
		int n = a.length;
		List<List<Double>> buckets = new ArrayList<>();
		for (int i = 0; i < n; i++) buckets.add(new ArrayList<>());
		for (double x : a) buckets.get((int) (x * n)).add(x);
		double[] out = new double[n];
		int k = 0;
		for (List<Double> b : buckets) {
			b.sort(null); // each bucket is small on average
			for (double x : b) out[k++] = x;
		}
		return out;
	}

	public static void main(String[] args) {
		Check.eq("counting with negatives", countingSort(new int[] { 4, -2, 2, 8, 3, 3, -2, 1 }), new int[] { -2, -2, 1, 2, 3, 3, 4, 8 });
		Check.eq("counting empty", countingSort(new int[] {}), new int[] {});
		Check.eq("radix", radixSort(new int[] { 170, 45, 75, 90, 802, 24, 2, 66 }), new int[] { 2, 24, 45, 66, 75, 90, 170, 802 });
		int[] big = new Random(3).ints(2000, 0, Integer.MAX_VALUE).toArray();
		int[] expected = big.clone();
		Arrays.sort(expected);
		Check.isTrue("radix large values", Arrays.equals(radixSort(big), expected));
		double[] d = { 0.897, 0.565, 0.656, 0.1234, 0.665, 0.3434 };
		double[] sortedD = d.clone();
		Arrays.sort(sortedD);
		Check.eq("bucket", bucketSort(d), sortedD);
	}
}
