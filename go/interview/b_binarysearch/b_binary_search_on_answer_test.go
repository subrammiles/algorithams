package b_binarysearch

import "testing"

func TestBinarySearchOnAnswer(t *testing.T) {
	if MinEatingSpeed([]int{3, 6, 7, 11}, 8) != 4 || MinEatingSpeed([]int{30, 11, 23, 4, 20}, 5) != 30 {
		t.Fatal("koko")
	}
	if SplitArray([]int{7, 2, 5, 10, 8}, 2) != 18 || SplitArray([]int{1, 2, 3, 4, 5}, 2) != 9 {
		t.Fatal("split array")
	}
}
