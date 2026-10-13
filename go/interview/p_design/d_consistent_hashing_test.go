package p_design

import (
	"strconv"
	"testing"
)

func TestHashRing(t *testing.T) {
	ring := NewHashRing(100)
	for _, n := range []string{"cache-A", "cache-B", "cache-C"} {
		ring.AddNode(n)
	}
	before, load := map[string]string{}, map[string]int{}
	for i := 0; i < 10000; i++ {
		k := "user:" + strconv.Itoa(i)
		before[k] = ring.NodeFor(k)
		load[before[k]]++
	}
	for node, c := range load {
		if c < 2000 || c > 5000 {
			t.Fatalf("unbalanced: %s has %d keys", node, c)
		}
	}
	ring.AddNode("cache-D")
	moved := 0
	for k, old := range before {
		if now := ring.NodeFor(k); now != old {
			moved++
			if now != "cache-D" {
				t.Fatalf("%s moved to %s, not the new node", k, now)
			}
		}
	}
	if moved > 5000 {
		t.Fatalf("too many keys moved: %d", moved)
	}
}
