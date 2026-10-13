package f_linkedlist

type RandomNode struct {
	Val          int
	Next, Random *RandomNode
}

// CopyWithMap: two passes with a map old -> new. O(n) space.
func CopyWithMap(head *RandomNode) *RandomNode {
	copies := map[*RandomNode]*RandomNode{}
	for n := head; n != nil; n = n.Next {
		copies[n] = &RandomNode{Val: n.Val}
	}
	for n := head; n != nil; n = n.Next {
		copies[n].Next, copies[n].Random = copies[n.Next], copies[n.Random]
	}
	return copies[head]
}

// CopyInterleaved: A -> A' -> B -> B' ..., set randoms, unweave. O(1) extra space.
func CopyInterleaved(head *RandomNode) *RandomNode {
	for n := head; n != nil; n = n.Next.Next {
		n.Next = &RandomNode{Val: n.Val, Next: n.Next}
	}
	for n := head; n != nil; n = n.Next.Next {
		if n.Random != nil {
			n.Next.Random = n.Random.Next
		}
	}
	dummy := &RandomNode{}
	tail := dummy
	for n := head; n != nil; n = n.Next {
		tail.Next = n.Next
		tail = tail.Next
		n.Next = n.Next.Next // restore the original
	}
	return dummy.Next
}
