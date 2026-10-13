package graphs

import "testing"

func TestMaxFlowAndMatching(t *testing.T) {
	capacity := [][]int{{0, 16, 13, 0, 0, 0}, {0, 0, 10, 12, 0, 0}, {0, 4, 0, 0, 14, 0}, {0, 0, 9, 0, 0, 20}, {0, 0, 0, 7, 0, 4}, {0, 0, 0, 0, 0, 0}}
	if MaxFlow(capacity, 0, 5) != 23 {
		t.Error("max flow")
	}
	if MaxBipartiteMatching([][]int{{0, 1}, {0}, {0}}, 2) != 2 || MaxBipartiteMatching([][]int{{0, 1}, {0, 2}, {1}}, 3) != 3 {
		t.Error("matching")
	}
}
