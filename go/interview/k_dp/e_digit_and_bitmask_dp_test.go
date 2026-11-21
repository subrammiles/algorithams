package k_dp

import "testing"

func TestDigitAndBitmaskDP(t *testing.T) {
	for n, want := range map[int]int{20: 19, 5: 5, 135: 110} {
		if got := CountSpecialNumbers(n); got != want {
			t.Errorf("special numbers <= %d = %d, want %d", n, got, want)
		}
	}
	for s, want := range map[string]int{"(()": 2, ")()())": 4, "()(())": 6, "": 0} {
		if got := LongestValidParentheses(s); got != want {
			t.Errorf("valid parens %q = %d, want %d", s, got, want)
		}
	}
	if !CanPartitionKSubsets([]int{4, 3, 2, 3, 5, 2, 1}, 4) || CanPartitionKSubsets([]int{1, 2, 3, 4}, 3) {
		t.Error("k subsets")
	}
}
