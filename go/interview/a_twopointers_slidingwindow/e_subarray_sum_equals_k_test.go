package a_twopointers_slidingwindow

import "testing"

func TestSubarraySum(t *testing.T) {
	if SubarraySum([]int{1, 1, 1}, 2) != 2 || SubarraySum([]int{3, 4, 7, 2, -3, 1, 4, 2}, 7) != 4 || SubarraySum([]int{0, 0}, 0) != 3 {
		t.Fatal("subarray sum")
	}
}
