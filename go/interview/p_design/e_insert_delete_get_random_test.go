package p_design

import "testing"

func TestRandomizedSet(t *testing.T) {
	s := NewRandomizedSet(7)
	if !s.Insert(1) || s.Remove(2) || !s.Insert(2) || !s.Insert(3) || s.Insert(2) || !s.Remove(1) {
		t.Fatal("insert/remove")
	}
	seen := map[int]int{}
	for i := 0; i < 1000; i++ {
		seen[s.GetRandom()]++
	}
	if seen[1] != 0 || seen[2] < 300 || seen[3] < 300 {
		t.Fatalf("distribution %v", seen)
	}
}
