package f_linkedlist

func Reverse(head *ListNode) *ListNode {
	var prev *ListNode
	for head != nil {
		head.Next, prev, head = prev, head, head.Next
	}
	return prev
}

// ReverseKGroup reverses k nodes at a time; a short final group stays. O(n), O(1) space.
func ReverseKGroup(head *ListNode, k int) *ListNode {
	dummy := &ListNode{Next: head}
	groupPrev := dummy
	for {
		kth := groupPrev
		for i := 0; i < k && kth != nil; i++ {
			kth = kth.Next
		}
		if kth == nil {
			return dummy.Next
		}
		groupNext := kth.Next
		prev, cur := groupNext, groupPrev.Next
		for cur != groupNext {
			cur.Next, prev, cur = prev, cur, cur.Next
		}
		oldFirst := groupPrev.Next
		groupPrev.Next = kth
		groupPrev = oldFirst
	}
}
