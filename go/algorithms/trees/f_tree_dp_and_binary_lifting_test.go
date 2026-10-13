package trees

import "testing"

func TestTreeDPAndBinaryLifting(t *testing.T) {
	if Rob(Build(3, 2, 3, Null, 3, Null, 1)) != 7 || Rob(Build(3, 4, 5, 1, 3, Null, 1)) != 9 {
		t.Error("rob")
	}
	if MinCameraCover(Build(0, 0, Null, 0, 0)) != 1 || MinCameraCover(Build(0, 0, Null, 0, Null, 0, Null, Null, 0)) != 2 {
		t.Error("cameras")
	}
	bl := NewBinaryLifting([]int{-1, 0, 0, 1, 1, 2, 2})
	if bl.KthAncestor(3, 1) != 1 || bl.KthAncestor(5, 2) != 0 || bl.KthAncestor(6, 3) != -1 {
		t.Error("kth ancestor")
	}
	if bl.LCA(3, 4) != 1 || bl.LCA(3, 5) != 0 || bl.LCA(5, 2) != 2 || bl.LCA(4, 4) != 4 {
		t.Error("lca")
	}
}
