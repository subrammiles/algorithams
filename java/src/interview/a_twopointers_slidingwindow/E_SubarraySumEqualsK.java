package interview.a_twopointers_slidingwindow;

import java.util.HashMap;
import java.util.Map;

import interview.Check;

/*
 * Problem: Count contiguous subarrays whose sum equals k (values may be negative).
 *
 * Why not sliding window? With negatives the window sum is not monotonic,
 * so shrinking/expanding decisions break. Use prefix sums instead.
 *
 * Approach: sum(i..j) = prefix[j] - prefix[i-1]. While scanning, count how many
 * earlier prefixes equal (current - k). Seed the map with {0:1} for subarrays starting at 0.
 *
 * Time O(n), Space O(n).
 *
 * Variants with the same trick: longest subarray with sum k (store first index),
 * equal 0s and 1s (map 0 -> -1), subarray sum divisible by k (store prefix mod k).
 */
public class E_SubarraySumEqualsK {

	static int subarraySum(int[] nums, int k) {
		Map<Integer, Integer> seen = new HashMap<>();
		seen.put(0, 1);
		int prefix = 0, count = 0;
		for (int x : nums) {
			prefix += x;
			count += seen.getOrDefault(prefix - k, 0);
			seen.merge(prefix, 1, Integer::sum);
		}
		return count;
	}

	public static void main(String[] args) {
		Check.eq("basic", subarraySum(new int[] { 1, 1, 1 }, 2), 2);
		Check.eq("negatives", subarraySum(new int[] { 3, 4, 7, 2, -3, 1, 4, 2 }, 7), 4);
		Check.eq("zero k", subarraySum(new int[] { 0, 0 }, 0), 3);
	}
}
