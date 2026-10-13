package graphs

import (
	"math"
	"testing"
)

func TestShortestPathVariants(t *testing.T) {
	if MinimumObstacles([][]int{{0, 1, 1}, {1, 1, 0}, {1, 1, 0}}) != 2 || MinimumObstacles([][]int{{0, 1, 0, 0, 0}, {0, 1, 0, 1, 0}, {0, 0, 0, 1, 0}}) != 0 {
		t.Error("0-1 bfs")
	}
	edges := [][2]int{{0, 1}, {1, 2}, {0, 2}}
	if math.Abs(MaxProbability(3, edges, []float64{0.5, 0.5, 0.2}, 0, 2)-0.25) > 1e-9 || math.Abs(MaxProbability(3, edges, []float64{0.5, 0.5, 0.3}, 0, 2)-0.3) > 1e-9 {
		t.Error("max probability")
	}
	spiral := [][]int{{0, 1, 2, 3, 4}, {24, 23, 22, 21, 5}, {12, 13, 14, 15, 16}, {11, 17, 18, 19, 20}, {10, 9, 8, 7, 6}}
	if SwimInWater([][]int{{0, 2}, {1, 3}}) != 3 || SwimInWater(spiral) != 16 {
		t.Error("swim")
	}
	if AStar([]string{".....", ".###.", "...#.", "##.#.", "....."}) != 8 || AStar([]string{".....", "####.", ".....", ".####", "....."}) != 16 || AStar([]string{".#", "#."}) != -1 {
		t.Error("a*")
	}
}
