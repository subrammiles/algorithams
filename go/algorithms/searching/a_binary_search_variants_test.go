package searching

import "testing"

func TestBinarySearchVariants(t *testing.T) {
	a := []int{1, 2, 2, 2, 3}
	if LowerBound(a, 2) != 1 || UpperBound(a, 2) != 4 || LowerBound(a, 9) != 5 {
		t.Error("bounds")
	}
	if SearchRange([]int{5, 7, 7, 8, 8, 10}, 8) != [2]int{3, 4} || SearchRange([]int{5, 7, 7, 8, 8, 10}, 6) != [2]int{-1, -1} {
		t.Error("range")
	}
	peaks := []int{1, 2, 1, 3, 5, 6, 4}
	p := FindPeakElement(peaks)
	if (p > 0 && peaks[p] < peaks[p-1]) || (p < len(peaks)-1 && peaks[p] < peaks[p+1]) {
		t.Errorf("not a peak: %d", p)
	}
	if MySqrt(8) != 2 || MySqrt(2147395599) != 46339 || MySqrt(1<<31-1) != 46340 {
		t.Error("sqrt")
	}
	m := [][]int{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}
	if !SearchMatrix(m, 3) || SearchMatrix(m, 13) {
		t.Error("matrix")
	}
	if TernarySearchMax([]int{1, 3, 7, 9, 8, 4, 2}) != 3 {
		t.Error("ternary")
	}
}
