package l_backtracking

import (
	"reflect"
	"testing"
)

func TestBacktracking(t *testing.T) {
	if got := SubsetsWithDup([]int{1, 2, 2}); !reflect.DeepEqual(got, [][]int{{}, {1}, {1, 2}, {1, 2, 2}, {2}, {2, 2}}) {
		t.Errorf("subsets %v", got)
	}
	if got := PermuteUnique([]int{1, 1, 2}); !reflect.DeepEqual(got, [][]int{{1, 1, 2}, {1, 2, 1}, {2, 1, 1}}) {
		t.Errorf("permutations %v", got)
	}
	if got := CombinationSum([]int{2, 3, 6, 7}, 7); !reflect.DeepEqual(got, [][]int{{2, 2, 3}, {7}}) {
		t.Errorf("combination sum %v", got)
	}
}
