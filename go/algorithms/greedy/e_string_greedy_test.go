package greedy

import (
	"sort"
	"testing"
)

func TestStringGreedy(t *testing.T) {
	if RemoveKdigits("1432219", 3) != "1219" || RemoveKdigits("10200", 1) != "200" || RemoveKdigits("10", 2) != "0" {
		t.Error("remove k digits")
	}
	r := ReorganizeString("aaabbc")
	b := []byte(r)
	sort.Slice(b, func(i, j int) bool { return b[i] < b[j] })
	if string(b) != "aaabbc" {
		t.Fatalf("reorganize %q", r)
	}
	for i := 1; i < len(r); i++ {
		if r[i] == r[i-1] {
			t.Fatalf("equal neighbours in %q", r)
		}
	}
	if ReorganizeString("aaab") != "" {
		t.Error("impossible case")
	}
	if LargestNumber([]int{3, 30, 34, 5, 9}) != "9534330" || LargestNumber([]int{0, 0}) != "0" {
		t.Error("largest number")
	}
}
