package dynamic_programming

import "testing"

func TestIntervalAndPartitionDP(t *testing.T) {
	if MatrixChainOrder([]int{40, 20, 30, 10, 30}) != 26000 || MatrixChainOrder([]int{10, 20, 30, 40, 30}) != 30000 {
		t.Error("matrix chain")
	}
	if MinCutPalindromePartition("aab") != 1 || MinCutPalindromePartition("a") != 0 || MinCutPalindromePartition("ab") != 1 {
		t.Error("min cut")
	}
	if EggDrop(2, 100) != 14 || EggDrop(1, 10) != 10 || EggDrop(3, 14) != 4 {
		t.Error("egg drop")
	}
}
