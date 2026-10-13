package dynamic_programming

import "testing"

func TestGridDP(t *testing.T) {
	if UniquePaths(3, 7) != 28 {
		t.Error("unique paths")
	}
	if UniquePathsWithObstacles([][]int{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}) != 2 || UniquePathsWithObstacles([][]int{{1}}) != 0 {
		t.Error("obstacles")
	}
	if MinPathSum([][]int{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}) != 7 {
		t.Error("min path sum")
	}
	if MaximalSquare([]string{"10100", "10111", "11111", "10010"}) != 4 || MaximalSquare([]string{"0"}) != 0 {
		t.Error("maximal square")
	}
}
