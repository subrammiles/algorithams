package h_graphs

import (
	"reflect"
	"testing"
)

func TestCourseSchedule(t *testing.T) {
	pre := [][2]int{{1, 0}, {2, 0}, {3, 1}, {3, 2}}
	if got := FindOrderKahn(4, pre); !reflect.DeepEqual(got, []int{0, 1, 2, 3}) {
		t.Errorf("order %v", got)
	}
	if FindOrderKahn(2, [][2]int{{0, 1}, {1, 0}}) != nil || HasCycleDFS(4, pre) || !HasCycleDFS(3, [][2]int{{1, 0}, {2, 1}, {0, 2}}) {
		t.Error("cycle detection")
	}
}
