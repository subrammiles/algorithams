package matrix

import (
	"reflect"
	"testing"
)

func TestMatrixProblems(t *testing.T) {
	a := [][]int{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}}
	SetZeroes(a)
	if !reflect.DeepEqual(a, [][]int{{1, 0, 1}, {0, 0, 0}, {1, 0, 1}}) {
		t.Errorf("set zeroes %v", a)
	}
	b := [][]int{{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}}
	SetZeroes(b)
	if !reflect.DeepEqual(b, [][]int{{0, 0, 0, 0}, {0, 4, 5, 0}, {0, 3, 1, 0}}) {
		t.Errorf("set zeroes first row %v", b)
	}
	m := [][]int{{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}}
	if !SearchMatrixII(m, 5) || SearchMatrixII(m, 20) {
		t.Error("staircase")
	}
	life := [][]int{{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}}
	GameOfLife(life)
	if !reflect.DeepEqual(life, [][]int{{0, 0, 0}, {1, 0, 1}, {0, 1, 1}, {0, 1, 0}}) {
		t.Errorf("game of life %v", life)
	}
}
