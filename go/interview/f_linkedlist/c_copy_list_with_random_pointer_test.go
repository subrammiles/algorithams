package f_linkedlist

import (
	"reflect"
	"testing"
)

func sampleRandomList() *RandomNode {
	nodes := make([]*RandomNode, 5)
	for i, v := range []int{7, 13, 11, 10, 1} {
		nodes[i] = &RandomNode{Val: v}
	}
	for i := 0; i < 4; i++ {
		nodes[i].Next = nodes[i+1]
	}
	nodes[1].Random, nodes[2].Random, nodes[3].Random, nodes[4].Random = nodes[0], nodes[4], nodes[2], nodes[0]
	return nodes[0]
}

func describe(head *RandomNode) [][2]int {
	index := map[*RandomNode]int{nil: -1}
	i := 0
	for n := head; n != nil; n = n.Next {
		index[n] = i
		i++
	}
	var out [][2]int
	for n := head; n != nil; n = n.Next {
		out = append(out, [2]int{n.Val, index[n.Random]})
	}
	return out
}

func TestCopyRandomList(t *testing.T) {
	original := sampleRandomList()
	want := describe(original)
	copied := CopyWithMap(original)
	if !reflect.DeepEqual(describe(copied), want) || copied == original {
		t.Fatal("map copy")
	}
	if !reflect.DeepEqual(describe(CopyInterleaved(original)), want) || !reflect.DeepEqual(describe(original), want) {
		t.Fatal("interleaved copy / original restored")
	}
}
