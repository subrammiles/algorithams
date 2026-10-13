package e_intervals

import (
	"reflect"
	"testing"
)

func TestIntervals(t *testing.T) {
	if got := Merge([][2]int{{1, 3}, {8, 10}, {2, 6}, {15, 18}}); !reflect.DeepEqual(got, [][2]int{{1, 6}, {8, 10}, {15, 18}}) {
		t.Errorf("merge %v", got)
	}
	if got := Merge([][2]int{{1, 4}, {4, 5}}); !reflect.DeepEqual(got, [][2]int{{1, 5}}) {
		t.Errorf("merge touching %v", got)
	}
	got := Insert([][2]int{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, [2]int{4, 8})
	if !reflect.DeepEqual(got, [][2]int{{1, 2}, {3, 10}, {12, 16}}) {
		t.Errorf("insert %v", got)
	}
	if EraseOverlapIntervals([][2]int{{1, 2}, {2, 3}, {3, 4}, {1, 3}}) != 1 {
		t.Error("erase overlap")
	}
}
