package b_binarysearch

import "testing"

func TestSearchRotated(t *testing.T) {
	a := []int{4, 5, 6, 7, 0, 1, 2}
	if Search(a, 0) != 4 || Search(a, 3) != -1 || Search([]int{1, 3}, 3) != 1 || FindMinIndex(a) != 4 {
		t.Fatal("rotated search")
	}
}
