package a_twopointers_slidingwindow

import (
	"reflect"
	"testing"
)

func TestSlidingWindowTemplates(t *testing.T) {
	for s, want := range map[string]int{"abcabcbb": 3, "bbbbb": 1, "pwwkew": 3, "abba": 2, "": 0} {
		if got := LengthOfLongestSubstring(s); got != want {
			t.Errorf("no repeat %q = %d, want %d", s, got, want)
		}
	}
	if got := FindAnagrams("cbaebabacd", "abc"); !reflect.DeepEqual(got, []int{0, 6}) {
		t.Errorf("anagrams %v", got)
	}
	if got := FindAnagrams("abab", "ab"); !reflect.DeepEqual(got, []int{0, 1, 2}) {
		t.Errorf("anagrams overlap %v", got)
	}
	if got := LongestOnes([]int{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2); got != 6 {
		t.Errorf("longest ones %d", got)
	}
	if got := LongestOnes([]int{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3); got != 10 {
		t.Errorf("longest ones k=3 %d", got)
	}
	if got := SubarraysWithKDistinct([]int{1, 2, 1, 2, 3}, 2); got != 7 {
		t.Errorf("exactly 2 distinct %d", got)
	}
	if got := SubarraysWithKDistinct([]int{1, 2, 1, 3, 4}, 3); got != 3 {
		t.Errorf("exactly 3 distinct %d", got)
	}
	if MinSubArrayLen(7, []int{2, 3, 1, 2, 4, 3}) != 2 || MinSubArrayLen(4, []int{1, 4, 4}) != 1 ||
		MinSubArrayLen(11, []int{1, 1, 1, 1, 1, 1, 1, 1}) != 0 {
		t.Error("min subarray len")
	}
}
