package f_linkedlist

import (
	"reflect"
	"testing"
)

func TestReverseKGroup(t *testing.T) {
	if got := Values(Reverse(Build(1, 2, 3))); !reflect.DeepEqual(got, []int{3, 2, 1}) {
		t.Errorf("reverse %v", got)
	}
	if got := Values(ReverseKGroup(Build(1, 2, 3, 4, 5), 2)); !reflect.DeepEqual(got, []int{2, 1, 4, 3, 5}) {
		t.Errorf("k=2 %v", got)
	}
	if got := Values(ReverseKGroup(Build(1, 2, 3, 4, 5), 3)); !reflect.DeepEqual(got, []int{3, 2, 1, 4, 5}) {
		t.Errorf("k=3 %v", got)
	}
}
