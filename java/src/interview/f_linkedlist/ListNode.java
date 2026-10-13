package interview.f_linkedlist;

import java.util.ArrayList;
import java.util.List;

/* Shared singly linked list node + helpers for the linked list problems. */
public class ListNode {
	public int val;
	public ListNode next;

	public ListNode(int val) {
		this.val = val;
	}

	public static ListNode of(int... values) {
		ListNode dummy = new ListNode(0), tail = dummy;
		for (int v : values) tail = tail.next = new ListNode(v);
		return dummy.next;
	}

	public static List<Integer> toList(ListNode head) {
		List<Integer> out = new ArrayList<>();
		for (ListNode n = head; n != null; n = n.next) out.add(n.val);
		return out;
	}
}
