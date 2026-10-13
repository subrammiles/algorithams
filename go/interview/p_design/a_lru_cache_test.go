package p_design

import "testing"

func TestLRUCache(t *testing.T) {
	c := NewLRUCache(2)
	c.Put(1, 1)
	c.Put(2, 2)
	if c.Get(1) != 1 {
		t.Fatal("get 1")
	}
	c.Put(3, 3) // evicts 2
	if c.Get(2) != -1 {
		t.Fatal("2 should be evicted")
	}
	c.Put(4, 4) // evicts 1
	if c.Get(1) != -1 || c.Get(3) != 3 || c.Get(4) != 4 {
		t.Fatal("after evicting 1")
	}
}
