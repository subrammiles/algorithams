package graphs

import (
	"reflect"
	"testing"
)

func TestGraphTraversalProblems(t *testing.T) {
	nodes := []*Node{{Val: 1}, {Val: 2}, {Val: 3}, {Val: 4}}
	for i, n := range nodes {
		n.Neighbors = []*Node{nodes[(i+1)%4], nodes[(i+3)%4]}
	}
	c := CloneGraph(nodes[0])
	if c == nodes[0] || c.Val != 1 || c.Neighbors[0].Val != 2 || c.Neighbors[1].Val != 4 || c.Neighbors[0].Neighbors[0].Neighbors[0].Neighbors[0] != c {
		t.Error("clone graph")
	}
	if !ValidTree(5, [][2]int{{0, 1}, {0, 2}, {0, 3}, {1, 4}}) || ValidTree(5, [][2]int{{0, 1}, {1, 2}, {2, 3}, {1, 3}, {1, 4}}) {
		t.Error("valid tree")
	}
	if CountComponents(5, [][2]int{{0, 1}, {1, 2}, {3, 4}}) != 2 {
		t.Error("components")
	}
	if got := AllPathsSourceTarget([][]int{{1, 2}, {3}, {3}, {}}); !reflect.DeepEqual(got, [][]int{{0, 1, 3}, {0, 2, 3}}) {
		t.Errorf("all paths %v", got)
	}
}
