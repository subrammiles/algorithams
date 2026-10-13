package c_monotonicstack

import (
	"reflect"
	"testing"
)

func TestMonotonicStack(t *testing.T) {
	if got := NextGreaterCircular([]int{1, 2, 1}); !reflect.DeepEqual(got, []int{2, -1, 2}) {
		t.Errorf("next greater %v", got)
	}
	if got := DailyTemperatures([]int{73, 74, 75, 71, 69, 72, 76, 73}); !reflect.DeepEqual(got, []int{1, 1, 4, 2, 1, 1, 0, 0}) {
		t.Errorf("daily temps %v", got)
	}
	if got := StockSpan([]int{100, 80, 60, 70, 60, 75, 85}); !reflect.DeepEqual(got, []int{1, 1, 1, 2, 1, 4, 6}) {
		t.Errorf("stock span %v", got)
	}
}
