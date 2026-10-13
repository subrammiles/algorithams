package dynamic_programming

import "testing"

func TestKnapsackFamily(t *testing.T) {
	wt, val := []int{1, 3, 4, 5}, []int{1, 4, 5, 7}
	if Knapsack01Table(wt, val, 7) != 9 || Knapsack01(wt, val, 7) != 9 {
		t.Error("0/1 knapsack")
	}
	if UnboundedKnapsack([]int{1, 3, 4}, []int{15, 50, 60}, 8) != 130 || RodCutting([]int{1, 5, 8, 9, 10, 17, 17, 20}, 8) != 22 {
		t.Error("unbounded / rod cutting")
	}
	if TargetSumWays([]int{1, 1, 1, 1, 1}, 3) != 5 || TargetSumWays([]int{1}, 2) != 0 || MinSubsetSumDifference([]int{1, 6, 11, 5}) != 1 {
		t.Error("target sum / min difference")
	}
}
