package backtracking

import (
	"reflect"
	"testing"
)

func TestClassicBacktracking(t *testing.T) {
	maze := [][]int{{1, 0, 0, 0}, {1, 1, 0, 1}, {1, 1, 0, 0}, {0, 1, 1, 1}}
	if got := RatInMaze(maze); !reflect.DeepEqual(got, []string{"DDRDRR", "DRDDRR"}) {
		t.Errorf("rat in maze %v", got)
	}
	if got := RatInMaze([][]int{{1, 0}, {0, 1}}); len(got) != 0 {
		t.Errorf("rat blocked %v", got)
	}

	graph := [][2]int{{0, 1}, {1, 2}, {2, 3}, {3, 0}, {0, 2}}
	if got := MColoring(4, graph, 3); !reflect.DeepEqual(got, []int{1, 2, 3, 2}) {
		t.Errorf("3-colouring %v", got)
	}
	if got := MColoring(4, graph, 2); got != nil {
		t.Errorf("2-colouring should fail, got %v", got)
	}

	g1 := [][2]int{{0, 1}, {0, 3}, {1, 2}, {1, 3}, {1, 4}, {2, 4}, {3, 4}}
	if got := HamiltonianCycle(5, g1); !reflect.DeepEqual(got, []int{0, 1, 2, 4, 3, 0}) {
		t.Errorf("hamiltonian %v", got)
	}
	if got := HamiltonianCycle(5, g1[:6]); got != nil {
		t.Errorf("no hamiltonian cycle expected, got %v", got)
	}

	if !IsValidTour(KnightsTour(5)) || !IsValidTour(KnightsTour(8)) {
		t.Error("knight's tour")
	}
	if KnightsTour(3) != nil || KnightsTour(4) != nil {
		t.Error("3x3 / 4x4 have no tour")
	}
}
