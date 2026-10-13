package h_graphs

import "testing"

func TestShortestPaths(t *testing.T) {
	if NetworkDelayTime([][3]int{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, 4, 2) != 2 || NetworkDelayTime([][3]int{{1, 2, 1}}, 2, 2) != -1 {
		t.Fatal("network delay")
	}
	flights := [][3]int{{0, 1, 100}, {1, 2, 100}, {2, 0, 100}, {1, 3, 600}, {2, 3, 200}}
	if FindCheapestPrice(4, flights, 0, 3, 1) != 700 || FindCheapestPrice(4, flights, 0, 3, 2) != 400 {
		t.Fatal("cheapest flights")
	}
}
