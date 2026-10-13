package graphs

import (
	"reflect"
	"testing"
)

func TestTarjan(t *testing.T) {
	edges := [][2]int{{1, 0}, {0, 2}, {2, 1}, {0, 3}, {3, 4}}
	if got := ArticulationPoints(5, edges); !reflect.DeepEqual(got, []int{0, 3}) {
		t.Errorf("articulation points %v", got)
	}
	if got := ArticulationPoints(3, [][2]int{{0, 1}, {1, 2}, {2, 0}}); len(got) != 0 {
		t.Errorf("cycle has none: %v", got)
	}
	if got := TarjanSCC(5, edges); !reflect.DeepEqual(got, [][]int{{0, 1, 2}, {3}, {4}}) {
		t.Errorf("scc %v", got)
	}
}
