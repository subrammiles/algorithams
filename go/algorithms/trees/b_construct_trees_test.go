package trees

import (
	"reflect"
	"testing"
)

func TestConstructTrees(t *testing.T) {
	want := []int{3, 9, 20, Null, Null, 15, 7}
	if got := ToLevelOrder(BuildFromPreIn([]int{3, 9, 20, 15, 7}, []int{9, 3, 15, 20, 7})); !reflect.DeepEqual(got, want) {
		t.Errorf("pre+in %v", got)
	}
	if got := ToLevelOrder(BuildFromInPost([]int{9, 3, 15, 20, 7}, []int{9, 15, 7, 20, 3})); !reflect.DeepEqual(got, want) {
		t.Errorf("in+post %v", got)
	}
	bst := SortedArrayToBST([]int{-10, -3, 0, 5, 9})
	if !reflect.DeepEqual(Inorder(bst), []int{-10, -3, 0, 5, 9}) || height(bst) != 3 {
		t.Error("sorted array to bst")
	}
	it := NewBSTIterator(Build(7, 3, 15, Null, Null, 9, 20))
	var out []int
	for it.HasNext() {
		out = append(out, it.Next())
	}
	if !reflect.DeepEqual(out, []int{3, 7, 9, 15, 20}) {
		t.Errorf("iterator %v", out)
	}
}
