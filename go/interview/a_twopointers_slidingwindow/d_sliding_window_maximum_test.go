package a_twopointers_slidingwindow

import (
	"reflect"
	"testing"
)

func TestMaxSlidingWindow(t *testing.T) {
	if got := MaxSlidingWindow([]int{1, 3, -1, -3, 5, 3, 6, 7}, 3); !reflect.DeepEqual(got, []int{3, 3, 5, 5, 6, 7}) {
		t.Fatalf("got %v", got)
	}
	if got := MaxSlidingWindow([]int{4, 2}, 1); !reflect.DeepEqual(got, []int{4, 2}) {
		t.Fatalf("got %v", got)
	}
}
