package i_unionfind

import (
	"reflect"
	"testing"
)

func TestUnionFindProblems(t *testing.T) {
	if FindRedundantConnection([][2]int{{1, 2}, {1, 3}, {2, 3}}) != [2]int{2, 3} {
		t.Error("redundant connection")
	}
	d := NewDSU(5)
	d.Union(0, 1)
	d.Union(3, 4)
	if d.Components != 3 {
		t.Error("components")
	}
	got := AccountsMerge([][]string{
		{"John", "johnsmith@mail.com", "john_newyork@mail.com"},
		{"John", "johnsmith@mail.com", "john00@mail.com"},
		{"Mary", "mary@mail.com"},
		{"John", "johnnybravo@mail.com"},
	})
	want := [][]string{
		{"John", "john00@mail.com", "john_newyork@mail.com", "johnsmith@mail.com"},
		{"John", "johnnybravo@mail.com"},
		{"Mary", "mary@mail.com"},
	}
	if !reflect.DeepEqual(got, want) {
		t.Errorf("accounts merge %v", got)
	}
}
