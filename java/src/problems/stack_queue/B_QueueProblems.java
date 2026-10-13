package problems.stack_queue;

import java.util.ArrayDeque;
import java.util.Deque;

import common.Check;

/*
 * Queue problems.
 *
 *  - Stack using ONE queue: after pushing x, rotate the previous size elements behind it so x is
 *    at the front. push O(n), pop O(1). (Queue from two stacks is in data_structures_tutorials.)
 *  - Moving average of the last k values: queue + running sum. O(1) per call.
 *  - Number of recent calls (pings in the last 3000 ms): evict from the front while too old.
 *    This is the sliding-window-log rate limiter in miniature.
 */
public class B_QueueProblems {

	static class StackUsingQueue {
		private final Deque<Integer> q = new ArrayDeque<>();

		void push(int x) {
			q.addLast(x);
			for (int i = 1; i < q.size(); i++) q.addLast(q.pollFirst()); // rotate x to the front
		}

		int pop() {
			return q.pollFirst();
		}

		int top() {
			return q.peekFirst();
		}

		boolean empty() {
			return q.isEmpty();
		}
	}

	static class MovingAverage {
		private final Deque<Integer> window = new ArrayDeque<>();
		private final int size;
		private double sum;

		MovingAverage(int size) {
			this.size = size;
		}

		double next(int val) {
			window.addLast(val);
			sum += val;
			if (window.size() > size) sum -= window.pollFirst();
			return sum / window.size();
		}
	}

	static class RecentCounter {
		private final Deque<Integer> pings = new ArrayDeque<>();

		int ping(int t) {
			pings.addLast(t);
			while (pings.peekFirst() < t - 3000) pings.pollFirst();
			return pings.size();
		}
	}

	public static void main(String[] args) {
		StackUsingQueue s = new StackUsingQueue();
		s.push(1);
		s.push(2);
		s.push(3);
		Check.eq("stack top", s.top(), 3);
		Check.eq("stack pop", s.pop(), 3);
		Check.eq("stack pop 2", s.pop(), 2);
		Check.eq("stack not empty", s.empty(), false);
		MovingAverage m = new MovingAverage(3);
		Check.near("avg 1", m.next(1), 1);
		Check.near("avg 2", m.next(10), 5.5);
		Check.near("avg 3", m.next(3), 14 / 3.0);
		Check.near("avg 4 (window slides)", m.next(5), 6);
		RecentCounter rc = new RecentCounter();
		Check.eq("ping 1", rc.ping(1), 1);
		Check.eq("ping 100", rc.ping(100), 2);
		Check.eq("ping 3001", rc.ping(3001), 3);
		Check.eq("ping 3002", rc.ping(3002), 3);
	}
}
