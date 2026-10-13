package problems.linked_list;

import java.util.List;

import common.Check;

/*
 * Linked list problems (reverse-k-group, Floyd, random-pointer copy are in interview/f_linkedlist).
 * Tools: a dummy head, slow/fast pointers, in-place reversal.
 *
 *  - Merge two sorted lists: dummy head + tail pointer.
 *  - Remove n-th from end in one pass: move fast n steps ahead, then move both.
 *  - Palindrome list in O(1) space: find the middle, reverse the second half, compare.
 *  - Add two numbers (digits stored in reverse): add with carry; don't forget the final carry.
 *  - Intersection of two lists: walk A then B and B then A - both pointers travel a + b + c and
 *    meet at the intersection (or at null). O(1) space.
 *  - Reorder list L0 Ln L1 Ln-1 ...: middle + reverse second half + interleave.
 *  - Sort list in O(n log n): merge sort (split with slow/fast, merge sorted halves).
 */
public class A_LinkedListProblems {

	static ListNode mergeTwoLists(ListNode a, ListNode b) {
		ListNode dummy = new ListNode(0), tail = dummy;
		while (a != null && b != null) {
			if (a.val <= b.val) {
				tail.next = a;
				a = a.next;
			} else {
				tail.next = b;
				b = b.next;
			}
			tail = tail.next;
		}
		tail.next = a != null ? a : b;
		return dummy.next;
	}

	static ListNode removeNthFromEnd(ListNode head, int n) {
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode fast = dummy, slow = dummy;
		for (int i = 0; i <= n; i++) fast = fast.next;
		while (fast != null) {
			fast = fast.next;
			slow = slow.next;
		}
		slow.next = slow.next.next;
		return dummy.next;
	}

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

	private static ListNode middle(ListNode head) { // first middle for even lengths
		ListNode slow = head, fast = head;
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	static boolean isPalindrome(ListNode head) {
		if (head == null) return true;
		ListNode mid = middle(head);
		ListNode second = reverse(mid.next);
		boolean ok = true;
		for (ListNode p = head, q = second; q != null; p = p.next, q = q.next) if (p.val != q.val) ok = false;
		mid.next = reverse(second); // restore the input
		return ok;
	}

	static ListNode addTwoNumbers(ListNode a, ListNode b) {
		ListNode dummy = new ListNode(0), tail = dummy;
		int carry = 0;
		while (a != null || b != null || carry != 0) {
			int sum = carry + (a != null ? a.val : 0) + (b != null ? b.val : 0);
			tail = tail.next = new ListNode(sum % 10);
			carry = sum / 10;
			if (a != null) a = a.next;
			if (b != null) b = b.next;
		}
		return dummy.next;
	}

	static ListNode getIntersectionNode(ListNode a, ListNode b) {
		ListNode p = a, q = b;
		while (p != q) {
			p = p == null ? b : p.next;
			q = q == null ? a : q.next;
		}
		return p;
	}

	static void reorderList(ListNode head) {
		if (head == null) return;
		ListNode mid = middle(head);
		ListNode second = reverse(mid.next);
		mid.next = null;
		for (ListNode first = head; second != null;) {
			ListNode n1 = first.next, n2 = second.next;
			first.next = second;
			second.next = n1;
			first = n1;
			second = n2;
		}
	}

	static ListNode sortList(ListNode head) {
		if (head == null || head.next == null) return head;
		ListNode mid = middle(head), right = mid.next;
		mid.next = null;
		return mergeTwoLists(sortList(head), sortList(right));
	}

	public static void main(String[] args) {
		Check.eq("merge", ListNode.toList(mergeTwoLists(ListNode.of(1, 2, 4), ListNode.of(1, 3, 4))), List.of(1, 1, 2, 3, 4, 4));
		Check.eq("remove nth", ListNode.toList(removeNthFromEnd(ListNode.of(1, 2, 3, 4, 5), 2)), List.of(1, 2, 3, 5));
		Check.eq("remove only", ListNode.toList(removeNthFromEnd(ListNode.of(1), 1)), List.of());
		ListNode pal = ListNode.of(1, 2, 2, 1);
		Check.isTrue("palindrome", isPalindrome(pal));
		Check.eq("palindrome restores list", ListNode.toList(pal), List.of(1, 2, 2, 1));
		Check.eq("not palindrome", isPalindrome(ListNode.of(1, 2)), false);
		Check.eq("add", ListNode.toList(addTwoNumbers(ListNode.of(2, 4, 3), ListNode.of(5, 6, 4))), List.of(7, 0, 8));
		Check.eq("add carry", ListNode.toList(addTwoNumbers(ListNode.of(9, 9, 9, 9, 9, 9, 9), ListNode.of(9, 9, 9, 9))), List.of(8, 9, 9, 9, 0, 0, 0, 1));
		ListNode shared = ListNode.of(8, 4, 5);
		ListNode a = ListNode.of(4, 1), b = ListNode.of(5, 6, 1);
		a.next.next = shared;
		b.next.next.next = shared;
		Check.isTrue("intersection", getIntersectionNode(a, b) == shared);
		Check.eq("no intersection", getIntersectionNode(ListNode.of(1), ListNode.of(2)), null);
		ListNode r = ListNode.of(1, 2, 3, 4, 5);
		reorderList(r);
		Check.eq("reorder", ListNode.toList(r), List.of(1, 5, 2, 4, 3));
		Check.eq("sort", ListNode.toList(sortList(ListNode.of(4, 2, 1, 3))), List.of(1, 2, 3, 4));
		Check.eq("sort 2", ListNode.toList(sortList(ListNode.of(-1, 5, 3, 4, 0))), List.of(-1, 0, 3, 4, 5));
	}
}
