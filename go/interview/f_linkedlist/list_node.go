// Package f_linkedlist: in-place pointer manipulation problems.
package f_linkedlist

type ListNode struct {
	Val  int
	Next *ListNode
}

func Build(values ...int) *ListNode {
	dummy := &ListNode{}
	tail := dummy
	for _, v := range values {
		tail.Next = &ListNode{Val: v}
		tail = tail.Next
	}
	return dummy.Next
}

func Values(head *ListNode) []int {
	out := []int{}
	for ; head != nil; head = head.Next {
		out = append(out, head.Val)
	}
	return out
}
