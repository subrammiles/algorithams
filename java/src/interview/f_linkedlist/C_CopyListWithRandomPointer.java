package interview.f_linkedlist;

import java.util.HashMap;
import java.util.Map;

import interview.Check;

/*
 * Problem: Deep-copy a list where each node also has a random pointer.
 *
 * Approach 1 - HashMap old -> new: two passes. O(n) time, O(n) space.
 * Approach 2 - interleave: A -> A' -> B -> B' ...; set A'.random = A.random.next; then unweave.
 *   O(n) time, O(1) extra space. Interviewers often ask for this after approach 1.
 */
public class C_CopyListWithRandomPointer {

	static class Node {
		int val;
		Node next, random;

		Node(int val) {
			this.val = val;
		}
	}

	static Node copyWithMap(Node head) {
		Map<Node, Node> copy = new HashMap<>();
		for (Node n = head; n != null; n = n.next) copy.put(n, new Node(n.val));
		for (Node n = head; n != null; n = n.next) {
			copy.get(n).next = copy.get(n.next);
			copy.get(n).random = copy.get(n.random);
		}
		return copy.get(head);
	}

	static Node copyInterleaved(Node head) {
		for (Node n = head; n != null; n = n.next.next) {
			Node c = new Node(n.val);
			c.next = n.next;
			n.next = c;
		}
		for (Node n = head; n != null; n = n.next.next) {
			if (n.random != null) n.next.random = n.random.next;
		}
		Node dummy = new Node(0), tail = dummy;
		for (Node n = head; n != null; n = n.next) {
			tail = tail.next = n.next;
			n.next = n.next.next; // restore original list
		}
		return dummy.next;
	}

	private static Node sample() {
		Node a = new Node(7), b = new Node(13), c = new Node(11), d = new Node(10), e = new Node(1);
		a.next = b; b.next = c; c.next = d; d.next = e;
		b.random = a; c.random = e; d.random = c; e.random = a;
		return a;
	}

	private static String describe(Node head) {
		Map<Node, Integer> index = new HashMap<>();
		int i = 0;
		for (Node n = head; n != null; n = n.next) index.put(n, i++);
		StringBuilder sb = new StringBuilder();
		for (Node n = head; n != null; n = n.next) sb.append("[").append(n.val).append(",").append(index.get(n.random)).append("]");
		return sb.toString();
	}

	public static void main(String[] args) {
		Node original = sample();
		String expected = describe(original);
		Node viaMap = copyWithMap(original);
		Check.eq("map copy", describe(viaMap), expected);
		Check.isTrue("map copy is new nodes", viaMap != original);
		Check.eq("interleaved copy", describe(copyInterleaved(original)), expected);
		Check.eq("original untouched", describe(original), expected);
	}
}
