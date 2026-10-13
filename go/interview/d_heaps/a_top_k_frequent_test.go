package d_heaps

import (
	"reflect"
	"testing"
)

func TestTopK(t *testing.T) {
	nums := []int{1, 1, 1, 2, 2, 3}
	if got := TopKHeap(nums, 2); !reflect.DeepEqual(got, []int{1, 2}) {
		t.Errorf("heap %v", got)
	}
	if got := TopKBucket(nums, 2); !reflect.DeepEqual(got, []int{1, 2}) {
		t.Errorf("bucket %v", got)
	}
	if got := KthLargest([]int{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4); got != 4 {
		t.Errorf("kth largest %d", got)
	}
}
