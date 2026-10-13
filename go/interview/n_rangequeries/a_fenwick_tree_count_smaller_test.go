package n_rangequeries

import (
	"reflect"
	"testing"
)

func TestFenwick(t *testing.T) {
	f := NewFenwick(5)
	for i, v := range []int{1, 3, 5, 7, 9} {
		f.Add(i+1, v)
	}
	if f.RangeSum(2, 4) != 15 {
		t.Error("range sum")
	}
	f.Add(3, 10)
	if f.RangeSum(2, 4) != 25 {
		t.Error("after update")
	}
	if !reflect.DeepEqual(CountSmaller([]int{5, 2, 6, 1}), []int{2, 1, 1, 0}) || !reflect.DeepEqual(CountSmaller([]int{-1, -1}), []int{0, 0}) {
		t.Error("count smaller")
	}
}
