package interview.t_arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * In-place array techniques that show up constantly and are easy to fumble under pressure.
 *
 * 1. Product of array except self (no division)            prefix * suffix pass, O(n), O(1) extra
 *    out[i] = (product of everything left of i) * (product of everything right of i).
 *    First pass writes left products into out, second pass multiplies a running right product.
 *    Trap: "just divide by nums[i]" breaks on zeros - say so before coding.
 *
 * 2. First missing positive                               cyclic sort, O(n), O(1) extra
 *    The answer is in 1..n+1. Put every value v in 1..n at index v-1 by swapping until the slot
 *    is right (or v is out of range / a duplicate). Then the first index i with nums[i] != i+1
 *    gives i+1. Each swap fixes one value for good, so the while loop is O(n) in total.
 *
 * 3. Find all duplicates (values in 1..n, each appears once or twice)   index marking, O(n), O(1)
 *    Use the sign of nums[v-1] as a "seen v" bit. Seeing a negative there means v is a duplicate.
 *
 * 4. Sort colours (Dutch national flag)                    three pointers, one pass
 *    [0, low) = 0s, [low, mid) = 1s, (high, n-1] = 2s, [mid, high] unknown.
 *    Trap: after swapping with high do NOT advance mid - the swapped-in value is unchecked.
 *
 * 5. Next permutation                                      O(n), O(1)
 *    From the right, find the first i with a[i] < a[i+1] (the "pivot"). Swap it with the rightmost
 *    element bigger than it, then reverse the suffix after i (it was descending, now ascending).
 *    No pivot means the array is the last permutation: reverse everything.
 *
 * 6. Rotate right by k                                     three reversals, O(n), O(1)
 *    reverse(all), reverse(first k), reverse(rest). Remember k %= n.
 *
 * 7. Difference array (range updates, then read once)      O(n + updates)
 *    Add v to [l, r]: diff[l] += v, diff[r+1] -= v. A prefix sum at the end rebuilds the values.
 *    Example: corporate flight bookings, car pooling, "range addition".
 *    Many updates mixed with queries -> Fenwick / segment tree instead (pattern n).
 *
 * 8. Majority element II (> n/3)                            Boyer-Moore with two candidates
 *    At most two values can appear more than n/3 times. Keep two candidates with counters, cancel
 *    triples of distinct values, then VERIFY with a second pass (candidates are only "maybe").
 *
 * Senior follow-ups:
 *  - Product except self with overflow: use long / BigInteger, or work with logs if only signs and
 *    magnitudes matter.
 *  - First missing positive when you may not modify the input: O(n) extra bitset, or O(n log n) sort.
 *  - Next permutation with duplicates: the same algorithm works (use strict comparisons as below).
 *  - Majority > n/k: generalised Misra-Gries with k-1 counters - also a streaming heavy-hitters sketch.
 */
public class A_ArrayTechniques {

	static int[] productExceptSelf(int[] nums) {
		int n = nums.length;
		int[] out = new int[n];
		int left = 1;
		for (int i = 0; i < n; i++) {
			out[i] = left;
			left *= nums[i];
		}
		int right = 1;
		for (int i = n - 1; i >= 0; i--) {
			out[i] *= right;
			right *= nums[i];
		}
		return out;
	}

	static int firstMissingPositive(int[] input) {
		int[] nums = input.clone();
		int n = nums.length;
		for (int i = 0; i < n; i++) {
			// keep swapping nums[i] to its home slot until the value here is out of range or already home
			while (nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
				swap(nums, i, nums[i] - 1);
			}
		}
		for (int i = 0; i < n; i++) {
			if (nums[i] != i + 1) return i + 1;
		}
		return n + 1;
	}

	static List<Integer> findDuplicates(int[] input) {
		int[] nums = input.clone();
		List<Integer> out = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			int v = Math.abs(nums[i]);
			if (nums[v - 1] < 0) out.add(v);
			else nums[v - 1] = -nums[v - 1];
		}
		return out;
	}

	static void sortColors(int[] a) {
		int low = 0, mid = 0, high = a.length - 1;
		while (mid <= high) {
			if (a[mid] == 0) swap(a, low++, mid++);
			else if (a[mid] == 1) mid++;
			else swap(a, mid, high--); // mid stays: the value that came from high is unchecked
		}
	}

	static void nextPermutation(int[] a) {
		int i = a.length - 2;
		while (i >= 0 && a[i] >= a[i + 1]) i--;
		if (i >= 0) {
			int j = a.length - 1;
			while (a[j] <= a[i]) j--;
			swap(a, i, j);
		}
		reverse(a, i + 1, a.length - 1);
	}

	static void rotate(int[] a, int k) {
		int n = a.length;
		if (n == 0) return;
		k %= n;
		reverse(a, 0, n - 1);
		reverse(a, 0, k - 1);
		reverse(a, k, n - 1);
	}

	// bookings[i] = {first, last, seats} on flights numbered 1..n
	static int[] corpFlightBookings(int[][] bookings, int n) {
		int[] diff = new int[n + 1];
		for (int[] b : bookings) {
			diff[b[0] - 1] += b[2];
			diff[b[1]] -= b[2];
		}
		int[] out = new int[n];
		int running = 0;
		for (int i = 0; i < n; i++) {
			running += diff[i];
			out[i] = running;
		}
		return out;
	}

	static List<Integer> majorityElementII(int[] nums) {
		int c1 = 0, c2 = 1, n1 = 0, n2 = 0; // distinct initial candidates
		for (int x : nums) {
			if (x == c1) n1++;
			else if (x == c2) n2++;
			else if (n1 == 0) {
				c1 = x;
				n1 = 1;
			} else if (n2 == 0) {
				c2 = x;
				n2 = 1;
			} else {
				n1--;
				n2--;
			}
		}
		n1 = 0;
		n2 = 0;
		for (int x : nums) {
			if (x == c1) n1++;
			else if (x == c2) n2++;
		}
		List<Integer> out = new ArrayList<>();
		if (n1 > nums.length / 3) out.add(c1);
		if (n2 > nums.length / 3) out.add(c2);
		out.sort(null);
		return out;
	}

	private static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	private static void reverse(int[] a, int i, int j) {
		while (i < j) swap(a, i++, j--);
	}

	public static void main(String[] args) {
		Check.eq("product except self", productExceptSelf(new int[] { 1, 2, 3, 4 }), new int[] { 24, 12, 8, 6 });
		Check.eq("product with a zero", productExceptSelf(new int[] { -1, 1, 0, -3, 3 }), new int[] { 0, 0, 9, 0, 0 });

		Check.eq("first missing [3,4,-1,1]", firstMissingPositive(new int[] { 3, 4, -1, 1 }), 2);
		Check.eq("first missing [7,8,9,11,12]", firstMissingPositive(new int[] { 7, 8, 9, 11, 12 }), 1);
		Check.eq("first missing [1,2,0]", firstMissingPositive(new int[] { 1, 2, 0 }), 3);
		Check.eq("first missing duplicates", firstMissingPositive(new int[] { 1, 1, 2, 2 }), 3);

		Check.eq("duplicates", findDuplicates(new int[] { 4, 3, 2, 7, 8, 2, 3, 1 }), List.of(2, 3));

		int[] colors = { 2, 0, 2, 1, 1, 0 };
		sortColors(colors);
		Check.eq("sort colors", colors, new int[] { 0, 0, 1, 1, 2, 2 });

		int[][] perms = { { 1, 2, 3 }, { 3, 2, 1 }, { 1, 1, 5 }, { 1, 3, 2 } };
		int[][] next = { { 1, 3, 2 }, { 1, 2, 3 }, { 1, 5, 1 }, { 2, 1, 3 } };
		for (int i = 0; i < perms.length; i++) {
			String label = "next permutation of " + Arrays.toString(perms[i]);
			nextPermutation(perms[i]);
			Check.eq(label, perms[i], next[i]);
		}

		int[] r = { 1, 2, 3, 4, 5, 6, 7 };
		rotate(r, 3);
		Check.eq("rotate by 3", r, new int[] { 5, 6, 7, 1, 2, 3, 4 });
		int[] r2 = { -1, -100, 3, 99 };
		rotate(r2, 6); // 6 % 4 == 2
		Check.eq("rotate by k > n", r2, new int[] { 3, 99, -1, -100 });

		Check.eq("flight bookings", corpFlightBookings(new int[][] { { 1, 2, 10 }, { 2, 3, 20 }, { 2, 5, 25 } }, 5),
				new int[] { 10, 55, 45, 25, 25 });

		Check.eq("majority II [3,2,3]", majorityElementII(new int[] { 3, 2, 3 }), List.of(3));
		Check.eq("majority II [1,2]", majorityElementII(new int[] { 1, 2 }), List.of(1, 2));
		Check.eq("majority II mixed", majorityElementII(new int[] { 1, 1, 1, 3, 3, 2, 2, 2 }), List.of(1, 2));
	}
}
