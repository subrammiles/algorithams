package b_binarysearch

import "testing"

func TestFindMedian(t *testing.T) {
	if FindMedian([]int{1, 3}, []int{2}) != 2 || FindMedian([]int{1, 2}, []int{3, 4}) != 2.5 || FindMedian(nil, []int{1}) != 1 {
		t.Fatal("median")
	}
}
