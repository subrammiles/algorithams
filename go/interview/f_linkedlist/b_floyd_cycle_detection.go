package f_linkedlist

// DetectCycle returns the node where the cycle begins (Floyd), or nil.
func DetectCycle(head *ListNode) *ListNode {
	slow, fast := head, head
	for fast != nil && fast.Next != nil {
		slow, fast = slow.Next, fast.Next.Next
		if slow == fast {
			p := head
			for p != slow {
				p, slow = p.Next, slow.Next
			}
			return p
		}
	}
	return nil
}

// FindDuplicate treats i -> nums[i] as a linked list with a cycle.
func FindDuplicate(nums []int) int {
	slow, fast := nums[0], nums[nums[0]]
	for slow != fast {
		slow, fast = nums[slow], nums[nums[fast]]
	}
	slow = 0
	for slow != fast {
		slow, fast = nums[slow], nums[fast]
	}
	return slow
}
