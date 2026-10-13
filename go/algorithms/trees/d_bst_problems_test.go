package trees

import (
	"reflect"
	"testing"
)

func TestBSTProblems(t *testing.T) {
	a := Build(1, 3, Null, Null, 2)
	RecoverTree(a)
	if got := ToLevelOrder(a); !reflect.DeepEqual(got, []int{3, 1, Null, Null, 2}) {
		t.Errorf("recover %v", got)
	}
	b := Build(3, 1, 4, Null, Null, 2)
	RecoverTree(b)
	if !reflect.DeepEqual(Inorder(b), []int{1, 2, 3, 4}) {
		t.Error("recover distant")
	}
	bst := Build(5, 3, 6, 2, 4, Null, Null, 1)
	if v, _ := InorderSuccessor(bst, 4); v != 5 {
		t.Error("successor 4")
	}
	if v, _ := InorderSuccessor(bst, 1); v != 2 {
		t.Error("successor 1")
	}
	if _, ok := InorderSuccessor(bst, 6); ok {
		t.Error("successor of max")
	}
	if got := ToLevelOrder(TrimBST(Build(3, 0, 4, Null, 2, Null, Null, 1), 1, 3)); !reflect.DeepEqual(got, []int{3, 2, Null, 1}) {
		t.Errorf("trim %v", got)
	}
	if got := ToLevelOrder(BSTFromPreorder([]int{8, 5, 1, 7, 10, 12})); !reflect.DeepEqual(got, []int{8, 5, 10, 1, 7, Null, 12}) {
		t.Errorf("from preorder %v", got)
	}
}
