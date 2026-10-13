package trees

import (
	"reflect"
	"testing"
)

func TestTreeProperties(t *testing.T) {
	if !IsSymmetric(Build(1, 2, 2, 3, 4, 4, 3)) || IsSymmetric(Build(1, 2, 2, Null, 3, Null, 3)) {
		t.Error("symmetric")
	}
	if !IsSubtree(Build(3, 4, 5, 1, 2), Build(4, 1, 2)) || IsSubtree(Build(3, 4, 5, 1, 2, Null, Null, Null, Null, 0), Build(4, 1, 2)) {
		t.Error("subtree")
	}
	tree := Build(1, 2, 5, 3, 4, Null, 6)
	Flatten(tree)
	var chain []int
	for n := tree; n != nil; n = n.Right {
		if n.Left != nil {
			t.Fatal("left pointer not cleared")
		}
		chain = append(chain, n.Val)
	}
	if !reflect.DeepEqual(chain, []int{1, 2, 3, 4, 5, 6}) {
		t.Errorf("flatten %v", chain)
	}
	if CountCompleteNodes(Build(1, 2, 3, 4, 5, 6)) != 6 || CountCompleteNodes(nil) != 0 {
		t.Error("count complete")
	}
}
