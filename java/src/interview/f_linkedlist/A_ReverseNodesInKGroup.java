package interview.f_linkedlist;

import java.util.List;

import common.Check;

/*
 * Problem: Reverse the list k nodes at a time; a final group shorter than k stays as is.
 *
 * Approach: Dummy head + "groupPrev" pointer. For each group:
 *   1. Walk k nodes to find kth; stop if fewer than k remain.
 *   2. Reverse the group in place, pointing the first node at groupNext.
 *   3. Stitch groupPrev to the new head; the old first node becomes the next groupPrev.
 * Time O(n), Space O(1).
 *
 * Warm-ups to master first: reverse whole list (iterative + recursive), reverse between m and n.
 */
public class A_ReverseNodesInKGroup {

	static ListNode reverse(ListNode head) {
		ListNode prev = null;
		while (head != null) {
			ListNode next = head.next;
			head.next = prev;
			prev = head;
			head = next;
		}
		return prev;
	}

	static ListNode reverseKGroup(ListNode head, int k) {
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode groupPrev = dummy;
		while (true) {
			ListNode kth = groupPrev;
			for (int i = 0; i < k && kth != null; i++) kth = kth.next;
			if (kth == null) break;
			ListNode groupNext = kth.next;
			ListNode prev = groupNext, cur = groupPrev.next;
			while (cur != groupNext) {
				ListNode next = cur.next;
				cur.next = prev;
				prev = cur;
				cur = next;
			}
			ListNode oldFirst = groupPrev.next;
			groupPrev.next = kth;
			groupPrev = oldFirst;
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		Check.eq("reverse", ListNode.toList(reverse(ListNode.of(1, 2, 3))), List.of(3, 2, 1));
		Check.eq("k=2", ListNode.toList(reverseKGroup(ListNode.of(1, 2, 3, 4, 5), 2)), List.of(2, 1, 4, 3, 5));
		Check.eq("k=3", ListNode.toList(reverseKGroup(ListNode.of(1, 2, 3, 4, 5), 3)), List.of(3, 2, 1, 4, 5));
	}
}
