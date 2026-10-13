package k_dp

import "testing"

func TestHarderDP(t *testing.T) {
	if !CanPartition([]int{1, 5, 11, 5}) || CanPartition([]int{1, 2, 3, 5}) {
		t.Error("partition")
	}
	if MaxCoins([]int{3, 1, 5, 8}) != 167 {
		t.Error("burst balloons")
	}
	if TSP([][]int{{0, 10, 15, 20}, {10, 0, 35, 25}, {15, 35, 0, 30}, {20, 25, 30, 0}}) != 80 {
		t.Error("tsp")
	}
}
