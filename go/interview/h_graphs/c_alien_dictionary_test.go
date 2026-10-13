package h_graphs

import "testing"

func TestAlienOrder(t *testing.T) {
	if got := AlienOrder([]string{"wrt", "wrf", "er", "ett", "rftt"}); got != "wertf" {
		t.Errorf("got %q", got)
	}
	if AlienOrder([]string{"z", "x", "z"}) != "" || AlienOrder([]string{"abc", "ab"}) != "" {
		t.Error("invalid inputs should return empty")
	}
}
