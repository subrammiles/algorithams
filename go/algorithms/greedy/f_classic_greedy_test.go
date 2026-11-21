package greedy

import "testing"

func TestClassicGreedy(t *testing.T) {
	if got := FractionalKnapsack([]int{60, 100, 120}, []int{10, 20, 30}, 50); got != 240 {
		t.Errorf("fractional knapsack %v", got)
	}
	if got := FractionalKnapsack([]int{10, 20}, []int{1, 2}, 10); got != 30 {
		t.Errorf("everything fits %v", got)
	}
	if AssignMiceToHoles([]int{4, -4, 2}, []int{4, 0, 5}) != 4 ||
		AssignMiceToHoles([]int{-10, -79, -79, 67, 93, -85, -28, -94}, []int{-2, 9, 69, 25, -31, 23, 50, 78}) != 102 {
		t.Error("mice to holes")
	}
	if PolicemenCatchThieves("PTTPT", 1) != 2 || PolicemenCatchThieves("TTPPTP", 2) != 3 || PolicemenCatchThieves("PTPTTP", 3) != 3 {
		t.Error("policemen")
	}
}
