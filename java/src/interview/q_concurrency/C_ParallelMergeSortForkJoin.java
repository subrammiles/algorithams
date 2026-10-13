package interview.q_concurrency;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import interview.Check;

/*
 * Divide and conquer in parallel with the Fork/Join framework (work-stealing pool).
 *
 * Key points to say in an interview:
 *  - Below a THRESHOLD, fall back to a sequential sort; task overhead dominates small inputs.
 *  - invokeAll(left, right) forks one half and computes the other in the current thread.
 *  - Speed-up is bounded by the sequential final merge (Amdahl's law) unless the merge is parallel too.
 *  - Arrays.parallelSort does exactly this in the JDK.
 */
public class C_ParallelMergeSortForkJoin {

	static class SortTask extends RecursiveAction {
		private static final long serialVersionUID = 1L;
		private static final int THRESHOLD = 1 << 13;
		private final int[] a, buffer;
		private final int lo, hi; // [lo, hi)

		SortTask(int[] a, int[] buffer, int lo, int hi) {
			this.a = a;
			this.buffer = buffer;
			this.lo = lo;
			this.hi = hi;
		}

		@Override
		protected void compute() {
			if (hi - lo <= THRESHOLD) {
				Arrays.sort(a, lo, hi);
				return;
			}
			int mid = (lo + hi) >>> 1;
			invokeAll(new SortTask(a, buffer, lo, mid), new SortTask(a, buffer, mid, hi));
			merge(mid);
		}

		private void merge(int mid) {
			System.arraycopy(a, lo, buffer, lo, hi - lo);
			int i = lo, j = mid, k = lo;
			while (i < mid && j < hi) a[k++] = buffer[i] <= buffer[j] ? buffer[i++] : buffer[j++];
			while (i < mid) a[k++] = buffer[i++];
			while (j < hi) a[k++] = buffer[j++];
		}
	}

	static void parallelSort(int[] a) {
		ForkJoinPool.commonPool().invoke(new SortTask(a, new int[a.length], 0, a.length));
	}

	public static void main(String[] args) {
		int[] data = new Random(1).ints(1_000_000).toArray();
		int[] expected = data.clone();
		Arrays.sort(expected);
		long start = System.nanoTime();
		parallelSort(data);
		System.out.println("Parallel sort of 1M ints took " + (System.nanoTime() - start) / 1_000_000 + " ms");
		Check.isTrue("sorted correctly", Arrays.equals(data, expected));
	}
}
