package interview.d_heaps;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import common.Check;

/*
 * Problem: Merge k sorted linked lists into one sorted list.
 *
 * Approach: Min-heap holding the current head of each list. Poll the smallest, append it,
 * push its successor. Time O(N log k) where N = total nodes, Space O(k).
 *
 * Alternative: divide and conquer pairwise merging, also O(N log k), O(1) extra space.
 *
 * Senior follow-up: this is the core of external merge sort (sorting data larger than RAM):
 * sort chunks that fit in memory, write them to disk, then k-way merge with a heap.
 */
public class B_MergeKSortedLists {

	static class ListNode {
		int val;
		ListNode next;

		ListNode(int val) {
			this.val = val;
		}
	}

	static ListNode mergeKLists(ListNode[] lists) {
		PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
		for (ListNode head : lists) if (head != null) heap.offer(head);
		ListNode dummy = new ListNode(0), tail = dummy;
		while (!heap.isEmpty()) {
			ListNode smallest = heap.poll();
			tail.next = smallest;
			tail = smallest;
			if (smallest.next != null) heap.offer(smallest.next);
		}
		return dummy.next;
	}

	static ListNode build(int... values) {
		ListNode dummy = new ListNode(0), tail = dummy;
		for (int v : values) tail = tail.next = new ListNode(v);
		return dummy.next;
	}

	static List<Integer> toList(ListNode head) {
		List<Integer> out = new ArrayList<>();
		for (ListNode n = head; n != null; n = n.next) out.add(n.val);
		return out;
	}

	public static void main(String[] args) {
		ListNode[] lists = { build(1, 4, 5), build(1, 3, 4), build(2, 6) };
		Check.eq("merge k", toList(mergeKLists(lists)), List.of(1, 1, 2, 3, 4, 4, 5, 6));
		Check.eq("empty", toList(mergeKLists(new ListNode[] { null })), List.of());
	}
}
