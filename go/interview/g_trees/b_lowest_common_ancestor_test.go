package g_trees

import "testing"

func TestLCA(t *testing.T) {
	root := Build(3, 5, 1, 6, 2, 0, 8, Null, Null, 7, 4)
	if LCA(root, Find(root, 5), Find(root, 1)).Val != 3 || LCA(root, Find(root, 5), Find(root, 4)).Val != 5 {
		t.Fatal("binary tree LCA")
	}
	bst := Build(6, 2, 8, 0, 4, 7, 9, Null, Null, 3, 5)
	if LCABST(bst, 2, 8).Val != 6 || LCABST(bst, 3, 5).Val != 4 {
		t.Fatal("BST LCA")
	}
}
