package h_graphs

import "testing"

func TestIslandsAndOranges(t *testing.T) {
	if NumIslands([]string{"11000", "11000", "00100", "00011"}) != 3 {
		t.Fatal("islands")
	}
	if OrangesRotting([][]int{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}}) != 4 || OrangesRotting([][]int{{2, 1, 1}, {0, 1, 1}, {1, 0, 1}}) != -1 {
		t.Fatal("oranges")
	}
}
