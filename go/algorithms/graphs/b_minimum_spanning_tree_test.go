package graphs

import "testing"

func TestMST(t *testing.T) {
	pts := [][2]int{{0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}}
	if MinCostPrim(pts) != 20 || MinCostKruskal(pts) != 20 {
		t.Error("example 1")
	}
	pts2 := [][2]int{{3, 12}, {-2, 5}, {-4, 1}}
	if MinCostPrim(pts2) != 18 || MinCostKruskal(pts2) != 18 || MinCostPrim([][2]int{{1, 1}}) != 0 {
		t.Error("example 2 / single point")
	}
}
