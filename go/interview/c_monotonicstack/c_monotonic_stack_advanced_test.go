package c_monotonicstack

import "testing"

func TestMonotonicStackAdvanced(t *testing.T) {
	if got := SumSubarrayMins([]int{3, 1, 2, 4}); got != 17 {
		t.Errorf("sum of mins %d", got)
	}
	if got := SumSubarrayMins([]int{11, 81, 94, 43, 3}); got != 444 {
		t.Errorf("sum of mins 2: %d", got)
	}
	if got := SumSubarrayMins([]int{2, 2, 2}); got != 12 {
		t.Errorf("sum of mins with ties %d", got)
	}
	if got := RemoveDuplicateLetters("bcabc"); got != "abc" {
		t.Errorf("remove dup letters %q", got)
	}
	if got := RemoveDuplicateLetters("cbacdcbc"); got != "acdb" {
		t.Errorf("remove dup letters %q", got)
	}
	if !Find132Pattern([]int{3, 1, 4, 2}) || Find132Pattern([]int{1, 2, 3, 4}) || !Find132Pattern([]int{-1, 3, 2, 0}) {
		t.Error("132 pattern")
	}
}
