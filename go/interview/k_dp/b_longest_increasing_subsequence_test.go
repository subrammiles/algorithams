package k_dp

import "testing"

func TestLIS(t *testing.T) {
	a := []int{10, 9, 2, 5, 3, 7, 101, 18}
	if LISQuadratic(a) != 4 || LISFast(a) != 4 || LISFast([]int{7, 7, 7}) != 1 {
		t.Error("lis")
	}
	if MaxEnvelopes([][2]int{{5, 4}, {6, 4}, {6, 7}, {2, 3}}) != 3 {
		t.Error("envelopes")
	}
}
