package heap

import (
	"reflect"
	"testing"
)

func TestHeapProblems(t *testing.T) {
	if got := KClosest([][2]int{{1, 3}, {-2, 2}}, 1); !reflect.DeepEqual(got, [][2]int{{-2, 2}}) {
		t.Errorf("k closest %v", got)
	}
	if got := KClosest([][2]int{{3, 3}, {5, -1}, {-2, 4}}, 2); !reflect.DeepEqual(got, [][2]int{{-2, 4}, {3, 3}}) {
		t.Errorf("k closest 2 %v", got)
	}
	kth := NewKthLargest(3, []int{4, 5, 8, 2})
	for _, c := range [][2]int{{3, 4}, {5, 5}, {10, 5}, {9, 8}, {4, 8}} {
		if got := kth.Add(c[0]); got != c[1] {
			t.Errorf("add %d -> %d, want %d", c[0], got, c[1])
		}
	}
	if NthUglyNumber(10) != 12 || NthUglyNumber(1) != 1 || LastStoneWeight([]int{2, 7, 4, 1, 8, 1}) != 1 {
		t.Error("ugly / stones")
	}
}
