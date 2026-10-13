package backtracking

import (
	"reflect"
	"testing"
)

func TestCombinationVariants(t *testing.T) {
	if got := CombinationSum2([]int{10, 1, 2, 7, 6, 1, 5}, 8); !reflect.DeepEqual(got, [][]int{{1, 1, 6}, {1, 2, 5}, {1, 7}, {2, 6}}) {
		t.Errorf("combination sum II %v", got)
	}
	if got := LetterCasePermutation("a1b2"); !reflect.DeepEqual(got, []string{"A1B2", "A1b2", "a1B2", "a1b2"}) {
		t.Errorf("letter case %v", got)
	}
	if got := Combine(4, 2); !reflect.DeepEqual(got, [][]int{{1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4}}) {
		t.Errorf("combine %v", got)
	}
	if got := Permute([]int{1, 2, 3}); !reflect.DeepEqual(got, [][]int{{1, 2, 3}, {1, 3, 2}, {2, 1, 3}, {2, 3, 1}, {3, 1, 2}, {3, 2, 1}}) {
		t.Errorf("permute %v", got)
	}
	if got := SubsetsBitmask([]int{1, 2, 3}); !reflect.DeepEqual(got, [][]int{{}, {1}, {2}, {1, 2}, {3}, {1, 3}, {2, 3}, {1, 2, 3}}) {
		t.Errorf("subsets %v", got)
	}
}
