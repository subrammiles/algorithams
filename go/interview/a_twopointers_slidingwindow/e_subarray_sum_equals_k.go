package a_twopointers_slidingwindow

// SubarraySum counts subarrays summing to k (negatives allowed) via prefix sums. O(n).
func SubarraySum(nums []int, k int) int {
	seen := map[int]int{0: 1}
	prefix, count := 0, 0
	for _, x := range nums {
		prefix += x
		count += seen[prefix-k]
		seen[prefix]++
	}
	return count
}
