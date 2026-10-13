package r_greedy

import "testing"

func TestGreedy(t *testing.T) {
	if Jump([]int{2, 3, 1, 1, 4}) != 2 {
		t.Error("jump")
	}
	if CanCompleteCircuit([]int{1, 2, 3, 4, 5}, []int{3, 4, 5, 1, 2}) != 3 || CanCompleteCircuit([]int{2, 3, 4}, []int{3, 4, 3}) != -1 {
		t.Error("gas station")
	}
	if LeastInterval("AAABBB", 2) != 8 || LeastInterval("AAABBBCCDD", 2) != 10 {
		t.Error("task scheduler")
	}
	if Candy([]int{1, 0, 2}) != 5 {
		t.Error("candy")
	}
}
