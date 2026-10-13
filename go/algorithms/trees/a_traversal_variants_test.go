package trees

import (
	"reflect"
	"testing"
)

func TestTraversalVariants(t *testing.T) {
	tree := Build(3, 9, 20, Null, Null, 15, 7)
	if got := ZigzagLevelOrder(tree); !reflect.DeepEqual(got, [][]int{{3}, {20, 9}, {15, 7}}) {
		t.Errorf("zigzag %v", got)
	}
	if got := RightSideView(Build(1, 2, 3, Null, 5, Null, 4)); !reflect.DeepEqual(got, []int{1, 3, 4}) {
		t.Errorf("right side %v", got)
	}
	if got := VerticalOrder(tree); !reflect.DeepEqual(got, [][]int{{9}, {3, 15}, {20}, {7}}) {
		t.Errorf("vertical %v", got)
	}
	if got := VerticalOrder(Build(1, 2, 3, 4, 5, 6, 7)); !reflect.DeepEqual(got, [][]int{{4}, {2}, {1, 5, 6}, {3}, {7}}) {
		t.Errorf("vertical ties %v", got)
	}
	if got := Boundary(Build(1, 2, 3, 4, 5, 6, Null, Null, Null, 7, 8, 9, 10)); !reflect.DeepEqual(got, []int{1, 2, 4, 7, 8, 9, 10, 6, 3}) {
		t.Errorf("boundary %v", got)
	}
}
