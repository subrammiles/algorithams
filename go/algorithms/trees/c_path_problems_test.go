package trees

import (
	"reflect"
	"testing"
)

func TestPathProblems(t *testing.T) {
	tree := Build(5, 4, 8, 11, Null, 13, 4, 7, 2, Null, Null, 5, 1)
	if got := PathSum(tree, 22); !reflect.DeepEqual(got, [][]int{{5, 4, 11, 2}, {5, 8, 4, 5}}) {
		t.Errorf("path sum II %v", got)
	}
	if PathSumAnyDownward(Build(10, 5, -3, 3, 2, Null, 11, 3, -2, Null, 1), 8) != 3 {
		t.Error("path sum III")
	}
	if SumNumbers(Build(1, 2, 3)) != 25 || SumNumbers(Build(4, 9, 0, 5, 1)) != 1026 {
		t.Error("sum numbers")
	}
	r := Build(3, 5, 1, 6, 2, 0, 8, Null, Null, 7, 4)
	if got := DistanceK(r, Find(r, 5), 2); !reflect.DeepEqual(got, []int{1, 4, 7}) {
		t.Errorf("distance k %v", got)
	}
}
