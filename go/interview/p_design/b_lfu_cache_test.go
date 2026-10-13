package p_design

import "testing"

func TestLFUCache(t *testing.T) {
	c := NewLFUCache(2)
	c.Put(1, 1)
	c.Put(2, 2)
	if c.Get(1) != 1 {
		t.Fatal("get 1")
	}
	c.Put(3, 3) // evicts 2
	if c.Get(2) != -1 || c.Get(3) != 3 {
		t.Fatal("2 evicted, 3 present")
	}
	c.Put(4, 4) // 1 and 3 tie at count 2 -> evict LRU = 1
	if c.Get(1) != -1 || c.Get(3) != 3 || c.Get(4) != 4 {
		t.Fatal("tie broken by recency")
	}
}
