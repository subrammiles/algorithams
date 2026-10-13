package g_trees

import "testing"

func TestMaxPathSum(t *testing.T) {
	if MaxPathSum(Build(-10, 9, 20, Null, Null, 15, 7)) != 42 || MaxPathSum(Build(-3)) != -3 {
		t.Fatal("max path sum")
	}
	if Diameter(Build(1, 2, 3, 4, 5)) != 3 {
		t.Fatal("diameter")
	}
}
