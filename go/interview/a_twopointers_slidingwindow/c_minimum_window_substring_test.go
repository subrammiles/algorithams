package a_twopointers_slidingwindow

import "testing"

func TestMinWindow(t *testing.T) {
	for _, c := range [][3]string{{"ADOBECODEBANC", "ABC", "BANC"}, {"a", "a", "a"}, {"a", "aa", ""}} {
		if got := MinWindow(c[0], c[1]); got != c[2] {
			t.Errorf("MinWindow(%q, %q) = %q, want %q", c[0], c[1], got, c[2])
		}
	}
}
