package g_trees

import (
	"reflect"
	"testing"
)

func TestValidateAndTraverse(t *testing.T) {
	if !IsValidBST(Build(2, 1, 3)) || IsValidBST(Build(5, 4, 6, Null, Null, 3, 7)) {
		t.Fatal("validate")
	}
	tree := Build(4, 2, 6, 1, 3, 5, 7)
	want := []int{1, 2, 3, 4, 5, 6, 7}
	if !reflect.DeepEqual(InorderIterative(tree), want) || !reflect.DeepEqual(InorderMorris(tree), want) ||
		!reflect.DeepEqual(InorderIterative(tree), want) {
		t.Fatal("inorder")
	}
}
