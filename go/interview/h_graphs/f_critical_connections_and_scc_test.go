package h_graphs

import (
	"reflect"
	"testing"
)

func TestBridgesAndSCC(t *testing.T) {
	if got := CriticalConnections(4, [][2]int{{0, 1}, {1, 2}, {2, 0}, {1, 3}}); !reflect.DeepEqual(got, [][2]int{{1, 3}}) {
		t.Errorf("bridges %v", got)
	}
	if CountSCC(5, [][2]int{{1, 0}, {0, 2}, {2, 1}, {0, 3}, {3, 4}}) != 3 {
		t.Error("scc")
	}
}
