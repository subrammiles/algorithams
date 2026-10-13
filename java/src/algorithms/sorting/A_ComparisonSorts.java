package algorithms.sorting;

import java.util.Arrays;
import java.util.Random;

import common.Check;

/*
 * All the comparison sorts in one place (the older one-file-per-sort programs are kept alongside).
 *
 * | Sort           | Best       | Average    | Worst      | Space    | Stable | Notes                          |
 * |----------------|------------|------------|------------|----------|--------|--------------------------------|
 * | Bubble         | n          | n^2        | n^2        | 1        | yes    | early exit when no swaps        |
 * | Selection      | n^2        | n^2        | n^2        | 1        | no     | fewest swaps (n)                |
 * | Insertion      | n          | n^2        | n^2        | 1        | yes    | great for small / nearly sorted |
 * | Shell          | n log n    | ~n^1.3     | n^2        | 1        | no     | insertion sort with gaps        |
 * | Merge          | n log n    | n log n    | n log n    | n        | yes    | linked lists, external sorting  |
 * | Quick (Lomuto) | n log n    | n log n    | n^2        | log n    | no     | random pivot avoids worst case  |
 * | Quick 3-way    | n          | n log n    | n^2        | log n    | no     | many duplicates (Dutch flag)    |
 * | Heap           | n log n    | n log n    | n log n    | 1        | no     | guaranteed n log n, in place    |
 *
 * Lower bound: any comparison sort needs Omega(n log n) comparisons (decision-tree argument).
 * Real libraries: Java uses dual-pivot quicksort for primitives and TimSort (merge + insertion,
 * stable) for objects; Python uses TimSort; Go uses pdqsort.
 */
public class A_ComparisonSorts {

	private static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	static int[] bubbleSort(int[] input) {
		int[] a = input.clone();
		for (int end = a.length - 1; end > 0; end--) {
			boolean swapped = false;
			for (int i = 0; i < end; i++) {
				if (a[i] > a[i + 1]) {
					swap(a, i, i + 1);
					swapped = true;
				}
			}
			if (!swapped) break; // already sorted
		}
		return a;
	}

	static int[] selectionSort(int[] input) {
		int[] a = input.clone();
		for (int i = 0; i < a.length - 1; i++) {
			int min = i;
			for (int j = i + 1; j < a.length; j++) if (a[j] < a[min]) min = j;
			swap(a, i, min);
		}
		return a;
	}

	static int[] insertionSort(int[] input) {
		int[] a = input.clone();
		for (int i = 1; i < a.length; i++) {
			int x = a[i], j = i - 1;
			while (j >= 0 && a[j] > x) a[j + 1] = a[j--]; // shift bigger elements right
			a[j + 1] = x;
		}
		return a;
	}

	static int[] shellSort(int[] input) {
		int[] a = input.clone();
		int gap = 1;
		while (gap < a.length / 3) gap = 3 * gap + 1; // Knuth's sequence 1, 4, 13, 40...
		for (; gap >= 1; gap /= 3) {
			for (int i = gap; i < a.length; i++) {
				int x = a[i], j = i;
				for (; j >= gap && a[j - gap] > x; j -= gap) a[j] = a[j - gap];
				a[j] = x;
			}
		}
		return a;
	}

	static int[] mergeSort(int[] input) {
		int[] a = input.clone();
		mergeSort(a, new int[a.length], 0, a.length - 1);
		return a;
	}

	private static void mergeSort(int[] a, int[] tmp, int lo, int hi) {
		if (lo >= hi) return;
		int mid = (lo + hi) >>> 1;
		mergeSort(a, tmp, lo, mid);
		mergeSort(a, tmp, mid + 1, hi);
		if (a[mid] <= a[mid + 1]) return; // halves already in order
		int i = lo, j = mid + 1, k = lo;
		while (i <= mid && j <= hi) tmp[k++] = a[i] <= a[j] ? a[i++] : a[j++]; // <= keeps it stable
		while (i <= mid) tmp[k++] = a[i++];
		while (j <= hi) tmp[k++] = a[j++];
		System.arraycopy(tmp, lo, a, lo, hi - lo + 1);
	}

	private static final Random RANDOM = new Random(7);

	static int[] quickSort(int[] input) {
		int[] a = input.clone();
		quickSort(a, 0, a.length - 1);
		return a;
	}

	private static void quickSort(int[] a, int lo, int hi) {
		if (lo >= hi) return;
		swap(a, lo + RANDOM.nextInt(hi - lo + 1), hi); // random pivot
		int pivot = a[hi], store = lo;
		for (int i = lo; i < hi; i++) if (a[i] < pivot) swap(a, i, store++);
		swap(a, store, hi);
		quickSort(a, lo, store - 1);
		quickSort(a, store + 1, hi);
	}

	static int[] quickSort3Way(int[] input) {
		int[] a = input.clone();
		quick3(a, 0, a.length - 1);
		return a;
	}

	private static void quick3(int[] a, int lo, int hi) {
		if (lo >= hi) return;
		int pivot = a[lo + RANDOM.nextInt(hi - lo + 1)];
		int lt = lo, i = lo, gt = hi; // a[lo..lt-1] < pivot, a[lt..i-1] == pivot, a[gt+1..hi] > pivot
		while (i <= gt) {
			if (a[i] < pivot) swap(a, lt++, i++);
			else if (a[i] > pivot) swap(a, i, gt--);
			else i++;
		}
		quick3(a, lo, lt - 1);
		quick3(a, gt + 1, hi);
	}

	static int[] heapSort(int[] input) {
		int[] a = input.clone();
		for (int i = a.length / 2 - 1; i >= 0; i--) siftDown(a, i, a.length);
		for (int end = a.length - 1; end > 0; end--) {
			swap(a, 0, end);
			siftDown(a, 0, end);
		}
		return a;
	}

	private static void siftDown(int[] a, int i, int n) {
		while (true) {
			int l = 2 * i + 1, r = l + 1, largest = i;
			if (l < n && a[l] > a[largest]) largest = l;
			if (r < n && a[r] > a[largest]) largest = r;
			if (largest == i) return;
			swap(a, i, largest);
			i = largest;
		}
	}

	public static void main(String[] args) {
		Random rnd = new Random(1);
		int[][] inputs = { {}, { 1 }, { 5, 4, 3, 2, 1 }, { 1, 2, 3, 4, 5 }, { 3, 1, 3, 1, 3, 1 }, rnd.ints(1000, -500, 500).toArray(), rnd.ints(1000, 0, 3).toArray() };
		String[] names = { "bubble", "selection", "insertion", "shell", "merge", "quick", "quick3", "heap" };
		for (int[] in : inputs) {
			int[] expected = in.clone();
			Arrays.sort(expected);
			int[][] results = { bubbleSort(in), selectionSort(in), insertionSort(in), shellSort(in), mergeSort(in), quickSort(in), quickSort3Way(in), heapSort(in) };
			boolean allOk = true;
			for (int s = 0; s < results.length; s++) {
				if (!Arrays.equals(results[s], expected)) {
					allOk = false;
					System.out.println(names[s] + " failed on " + Arrays.toString(in));
				}
			}
			Check.isTrue("all 8 sorts agree on input of length " + in.length, allOk);
		}
	}
}
