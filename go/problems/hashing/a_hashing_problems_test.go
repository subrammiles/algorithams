package hashing

import (
	"reflect"
	"testing"
)

func TestHashingProblems(t *testing.T) {
	if !reflect.DeepEqual(TwoSum([]int{2, 7, 11, 15}, 9), []int{0, 1}) {
		t.Error("two sum")
	}
	want := [][]string{{"ate", "eat", "tea"}, {"bat"}, {"nat", "tan"}}
	if got := GroupAnagrams([]string{"eat", "tea", "tan", "ate", "nat", "bat"}); !reflect.DeepEqual(got, want) {
		t.Errorf("anagrams %v", got)
	}
	if LongestConsecutive([]int{100, 4, 200, 1, 3, 2}) != 4 || LongestConsecutive([]int{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}) != 9 {
		t.Error("consecutive")
	}
	if !ContainsNearbyDuplicate([]int{1, 2, 3, 1}, 3) || ContainsNearbyDuplicate([]int{1, 2, 3, 1, 2, 3}, 2) {
		t.Error("nearby duplicate")
	}
	if FourSumCount([]int{1, 2}, []int{-2, -1}, []int{-1, 2}, []int{0, 2}) != 2 {
		t.Error("4sum II")
	}
}
