package a_twopointers_slidingwindow

import (
	"reflect"
	"testing"
)

func TestThreeSum(t *testing.T) {
	cases := []struct {
		in   []int
		want [][]int
	}{
		{[]int{-1, 0, 1, 2, -1, -4}, [][]int{{-1, -1, 2}, {-1, 0, 1}}},
		{[]int{0, 0, 0, 0}, [][]int{{0, 0, 0}}},
		{[]int{1, 2, 3}, [][]int{}},
	}
	for _, c := range cases {
		if got := ThreeSum(c.in); !reflect.DeepEqual(got, c.want) {
			t.Errorf("ThreeSum(%v) = %v, want %v", c.in, got, c.want)
		}
	}
}
