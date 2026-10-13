// Package linked_list: pointer-manipulation interview problems.
package linked_list

type ListNode struct {
	Val  int
	Next *ListNode
}

func Of(values ...int) *ListNode {
	var head *ListNode
	for i := len(values) - 1; i >= 0; i-- {
		head = &ListNode{values[i], head}
	}
	return head
}

func Values(head *ListNode) []int {
	out := []int{}
	for ; head != nil; head = head.Next {
		out = append(out, head.Val)
	}
	return out
}

func MergeTwoLists(a, b *ListNode) *ListNode {
	dummy := &ListNode{}
	tail := dummy
	for a != nil && b != nil {
		if a.Val <= b.Val {
			tail.Next, a = a, a.Next
		} else {
			tail.Next, b = b, b.Next
		}
		tail = tail.Next
	}
	if a != nil {
		tail.Next = a
	} else {
		tail.Next = b
	}
	return dummy.Next
}

func RemoveNthFromEnd(head *ListNode, n int) *ListNode {
	dummy := &ListNode{Next: head}
	fast, slow := dummy, dummy
	for i := 0; i <= n; i++ {
		fast = fast.Next
	}
	for fast != nil {
		fast, slow = fast.Next, slow.Next
	}
	slow.Next = slow.Next.Next
	return dummy.Next
}

func Reverse(head *ListNode) *ListNode {
	var prev *ListNode
	for head != nil {
		head.Next, prev, head = prev, head, head.Next
	}
	return prev
}

func middle(head *ListNode) *ListNode {
	slow, fast := head, head
	for fast.Next != nil && fast.Next.Next != nil {
		slow, fast = slow.Next, fast.Next.Next
	}
	return slow
}

// IsPalindrome reverses the second half, compares, then restores it. O(1) space.
func IsPalindrome(head *ListNode) bool {
	if head == nil {
		return true
	}
	mid := middle(head)
	second := Reverse(mid.Next)
	ok := true
	for p, q := head, second; q != nil; p, q = p.Next, q.Next {
		if p.Val != q.Val {
			ok = false
		}
	}
	mid.Next = Reverse(second)
	return ok
}

func AddTwoNumbers(a, b *ListNode) *ListNode {
	dummy := &ListNode{}
	tail, carry := dummy, 0
	for a != nil || b != nil || carry != 0 {
		sum := carry
		if a != nil {
			sum, a = sum+a.Val, a.Next
		}
		if b != nil {
			sum, b = sum+b.Val, b.Next
		}
		tail.Next = &ListNode{Val: sum % 10}
		tail, carry = tail.Next, sum/10
	}
	return dummy.Next
}

// GetIntersectionNode: both pointers walk a + b + c steps and meet at the junction (or nil).
func GetIntersectionNode(a, b *ListNode) *ListNode {
	p, q := a, b
	for p != q {
		if p == nil {
			p = b
		} else {
			p = p.Next
		}
		if q == nil {
			q = a
		} else {
			q = q.Next
		}
	}
	return p
}

func ReorderList(head *ListNode) {
	if head == nil {
		return
	}
	mid := middle(head)
	second := Reverse(mid.Next)
	mid.Next = nil
	for first := head; second != nil; {
		n1, n2 := first.Next, second.Next
		first.Next, second.Next = second, n1
		first, second = n1, n2
	}
}

func SortList(head *ListNode) *ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	mid := middle(head)
	right := mid.Next
	mid.Next = nil
	return MergeTwoLists(SortList(head), SortList(right))
}
