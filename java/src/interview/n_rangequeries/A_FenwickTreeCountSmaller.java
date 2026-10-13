package interview.n_rangequeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * Fenwick tree (Binary Indexed Tree): prefix sums with point updates, both O(log n),
 * in ~10 lines of code. i & -i is the size of the range node i covers.
 *
 * Problem: Count of Smaller Numbers After Self.
 *  - Coordinate-compress values to ranks 1..m.
 *  - Scan from the right; answer = prefixSum(rank - 1); then add 1 at rank.
 *  O(n log n). (Alternative: merge sort counting - also used for "count inversions".)
 */
public class A_FenwickTreeCountSmaller {

	static class Fenwick {
		private final int[] tree;

		Fenwick(int n) {
			tree = new int[n + 1]; // 1-indexed
		}

		void add(int i, int delta) {
			for (; i < tree.length; i += i & -i) tree[i] += delta;
		}

		int prefixSum(int i) {
			int s = 0;
			for (; i > 0; i -= i & -i) s += tree[i];
			return s;
		}

		int rangeSum(int l, int r) {
			return prefixSum(r) - prefixSum(l - 1);
		}
	}

	static List<Integer> countSmaller(int[] nums) {
		int[] sorted = Arrays.stream(nums).distinct().sorted().toArray();
		Fenwick bit = new Fenwick(sorted.length);
		Integer[] result = new Integer[nums.length];
		for (int i = nums.length - 1; i >= 0; i--) {
			int rank = Arrays.binarySearch(sorted, nums[i]) + 1;
			result[i] = bit.prefixSum(rank - 1);
			bit.add(rank, 1);
		}
		return new ArrayList<>(Arrays.asList(result));
	}

	public static void main(String[] args) {
		Fenwick f = new Fenwick(5);
		int[] values = { 1, 3, 5, 7, 9 };
		for (int i = 0; i < values.length; i++) f.add(i + 1, values[i]);
		Check.eq("range 2..4", f.rangeSum(2, 4), 15);
		f.add(3, 10);
		Check.eq("after update", f.rangeSum(2, 4), 25);
		Check.eq("count smaller", countSmaller(new int[] { 5, 2, 6, 1 }), List.of(2, 1, 1, 0));
		Check.eq("count smaller dup", countSmaller(new int[] { -1, -1 }), List.of(0, 0));
	}
}
