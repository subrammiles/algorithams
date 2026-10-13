package a_twopointers_slidingwindow

// MaxSlidingWindow keeps a deque of indices with decreasing values. O(n).
func MaxSlidingWindow(nums []int, k int) []int {
	var result []int
	dq := []int{} // slice used as a deque
	for i, x := range nums {
		if len(dq) > 0 && dq[0] <= i-k {
			dq = dq[1:]
		}
		for len(dq) > 0 && nums[dq[len(dq)-1]] <= x {
			dq = dq[:len(dq)-1]
		}
		dq = append(dq, i)
		if i >= k-1 {
			result = append(result, nums[dq[0]])
		}
	}
	return result
}
