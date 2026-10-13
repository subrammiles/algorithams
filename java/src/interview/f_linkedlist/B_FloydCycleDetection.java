package interview.f_linkedlist;

import common.Check;

/*
 * Problem: Detect a cycle and return the node where it begins (Linked List Cycle II).
 *
 * Floyd's tortoise and hare:
 *  - slow moves 1, fast moves 2. If they meet there is a cycle.
 *  - Reset one pointer to head; move both 1 step. They meet at the cycle start.
 * Why: if head->start = a, start->meet = b, cycle length = c, then 2(a+b) = a+b+nc,
 *      so a = nc - b, i.e. walking a steps from the meeting point lands on the start.
 * Time O(n), Space O(1).
 *
 * Same trick: Find the Duplicate Number (treat i -> nums[i] as a linked list).
 */
public class B_FloydCycleDetection {

	static ListNode detectCycle(ListNode head) {
		ListNode slow = head, fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				ListNode p = head;
				while (p != slow) {
					p = p.next;
					slow = slow.next;
				}
				return p;
			}
		}
		return null;
	}

	static int findDuplicate(int[] nums) {
		int slow = nums[0], fast = nums[0];
		do {
			slow = nums[slow];
			fast = nums[nums[fast]];
		} while (slow != fast);
		slow = nums[0];
		while (slow != fast) {
			slow = nums[slow];
			fast = nums[fast];
		}
		return slow;
	}

	public static void main(String[] args) {
		ListNode head = ListNode.of(3, 2, 0, -4);
		head.next.next.next.next = head.next; // tail -> node with value 2
		Check.eq("cycle start", detectCycle(head).val, 2);
		Check.eq("no cycle", detectCycle(ListNode.of(1, 2)), null);
		Check.eq("find duplicate", findDuplicate(new int[] { 1, 3, 4, 2, 2 }), 2);
	}
}
