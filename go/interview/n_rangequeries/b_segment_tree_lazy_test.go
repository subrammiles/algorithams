package n_rangequeries

import "testing"

func TestSegmentTreeLazy(t *testing.T) {
	st := NewSegmentTreeLazy([]int{1, 2, 3, 4, 5})
	if st.RangeSum(0, 4) != 15 {
		t.Fatal("initial sum")
	}
	st.RangeAdd(1, 3, 10)
	if st.RangeSum(0, 4) != 45 || st.RangeSum(2, 2) != 13 || st.RangeSum(3, 4) != 19 {
		t.Fatal("after range add")
	}
}
