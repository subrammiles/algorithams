package divide_and_conquer

import "testing"

func TestDivideAndConquer(t *testing.T) {
	if CountInversions([]int{2, 4, 1, 3, 5}) != 3 || CountInversions([]int{5, 4, 3, 2, 1}) != 10 || CountInversions([]int{1, 2, 3}) != 0 {
		t.Error("inversions")
	}
	if MaxSubarray([]int{-2, 1, -3, 4, -1, 2, 1, -5, 4}) != 6 || MaxSubarray([]int{-3, -1, -2}) != -1 {
		t.Error("max subarray")
	}
	if MajorityElement([]int{2, 2, 1, 1, 1, 2, 2}) != 2 {
		t.Error("majority")
	}
}
