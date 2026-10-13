package a_twopointers_slidingwindow

import "testing"

func TestTrap(t *testing.T) {
	h := []int{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}
	if TrapPrefix(h) != 6 || TrapTwoPointers(h) != 6 {
		t.Fatal("example 1")
	}
	if TrapTwoPointers([]int{4, 2, 0, 3, 2, 5}) != 9 || TrapTwoPointers(nil) != 0 {
		t.Fatal("example 2 / empty")
	}
}
