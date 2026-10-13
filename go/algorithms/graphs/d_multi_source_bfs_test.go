package graphs

import (
	"reflect"
	"testing"
)

func TestMultiSourceBFS(t *testing.T) {
	if got := UpdateMatrix([][]int{{0, 0, 0}, {0, 1, 0}, {1, 1, 1}}); !reflect.DeepEqual(got, [][]int{{0, 0, 0}, {0, 1, 0}, {1, 2, 1}}) {
		t.Errorf("01 matrix %v", got)
	}
	rooms := [][]int{{Inf, -1, 0, Inf}, {Inf, Inf, Inf, -1}, {Inf, -1, Inf, -1}, {0, -1, Inf, Inf}}
	WallsAndGates(rooms)
	if !reflect.DeepEqual(rooms, [][]int{{3, -1, 0, 1}, {2, 2, 1, -1}, {1, -1, 2, -1}, {0, -1, 3, 4}}) {
		t.Errorf("walls and gates %v", rooms)
	}
	ring := [][]int{{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}
	if ShortestBridge([][]int{{0, 1}, {1, 0}}) != 1 || ShortestBridge([][]int{{0, 1, 0}, {0, 0, 0}, {0, 0, 1}}) != 2 || ShortestBridge(ring) != 1 {
		t.Error("shortest bridge")
	}
}
