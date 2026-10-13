package c_monotonicstack

import "testing"

func TestLargestRectangle(t *testing.T) {
	if LargestRectangle([]int{2, 1, 5, 6, 2, 3}) != 10 || LargestRectangle([]int{2, 4}) != 4 {
		t.Fatal("histogram")
	}
	if MaximalRectangle([]string{"10100", "10111", "11111", "10010"}) != 6 {
		t.Fatal("maximal rectangle")
	}
}
