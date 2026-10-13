package dynamic_programming

import "testing"

func TestSubarrayAndSubsequence(t *testing.T) {
	if MaxSubarray([]int{-2, 1, -3, 4, -1, 2, 1, -5, 4}) != 6 {
		t.Error("kadane")
	}
	if MaxProduct([]int{2, 3, -2, 4}) != 6 || MaxProduct([]int{-2, 0, -1}) != 0 || MaxProduct([]int{-2, 3, -4}) != 24 {
		t.Error("max product")
	}
	if LongestCommonSubstring("abcdxyz", "xyzabcd") != 4 || MaxSumIncreasingSubsequence([]int{1, 101, 2, 3, 100, 4, 5}) != 106 {
		t.Error("common substring / increasing sum")
	}
	if LongestBitonic([]int{1, 11, 2, 10, 4, 5, 2, 1}) != 6 {
		t.Error("bitonic")
	}
}
