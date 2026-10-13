package dynamic_programming

import "testing"

func TestStockProblems(t *testing.T) {
	prices := []int{7, 1, 5, 3, 6, 4}
	if MaxProfitOnce(prices) != 5 || MaxProfitUnlimited(prices, 0) != 7 || MaxProfitOnce([]int{7, 6, 4, 3, 1}) != 0 {
		t.Error("once / unlimited")
	}
	if MaxProfitK(2, []int{3, 3, 5, 0, 0, 3, 1, 4}) != 6 || MaxProfitK(2, []int{3, 2, 6, 5, 0, 3}) != 7 || MaxProfitK(100, prices) != 7 {
		t.Error("k transactions")
	}
	if MaxProfitCooldown([]int{1, 2, 3, 0, 2}) != 3 || MaxProfitUnlimited([]int{1, 3, 2, 8, 4, 9}, 2) != 8 {
		t.Error("cooldown / fee")
	}
}
