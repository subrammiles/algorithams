package backtracking

import (
	"reflect"
	"testing"
)

func TestNQueens(t *testing.T) {
	if got := SolveNQueens(4); !reflect.DeepEqual(got, [][]string{{".Q..", "...Q", "Q...", "..Q."}, {"..Q.", "Q...", "...Q", ".Q.."}}) {
		t.Errorf("4 queens %v", got)
	}
	if TotalNQueens(8) != 92 || len(SolveNQueens(8)) != 92 || TotalNQueens(12) != 14200 || TotalNQueens(3) != 0 {
		t.Error("counts")
	}
}
