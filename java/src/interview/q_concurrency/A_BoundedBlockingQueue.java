package interview.q_concurrency;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import interview.Check;

/*
 * Problem: Implement a thread-safe bounded blocking queue (producer-consumer).
 *
 * Approach: one ReentrantLock + two Conditions (notFull, notEmpty).
 *  - ALWAYS wait in a while loop: spurious wake-ups happen, and another thread may have
 *    taken the slot between signal and wake-up.
 *  - Signal the OTHER condition after changing state.
 * (Equivalent with synchronized/wait/notifyAll, but then you must notifyAll since there is one wait-set.)
 *
 * In production use java.util.concurrent.ArrayBlockingQueue / LinkedBlockingQueue.
 * Discussion: fairness, timeouts (offer/poll with timeout), back-pressure, lock-free ring buffers (Disruptor).
 */
public class A_BoundedBlockingQueue<T> {

	private final Deque<T> items = new ArrayDeque<>();
	private final int capacity;
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition notFull = lock.newCondition();
	private final Condition notEmpty = lock.newCondition();

	A_BoundedBlockingQueue(int capacity) {
		this.capacity = capacity;
	}

	void put(T item) throws InterruptedException {
		lock.lock();
		try {
			while (items.size() == capacity) notFull.await();
			items.addLast(item);
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	T take() throws InterruptedException {
		lock.lock();
		try {
			while (items.isEmpty()) notEmpty.await();
			T item = items.removeFirst();
			notFull.signal();
			return item;
		} finally {
			lock.unlock();
		}
	}

	int size() {
		lock.lock();
		try {
			return items.size();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) throws Exception {
		A_BoundedBlockingQueue<Integer> q = new A_BoundedBlockingQueue<>(5);
		int producers = 4, consumers = 4, perProducer = 10_000;
		AtomicLong consumedSum = new AtomicLong();
		ExecutorService pool = Executors.newFixedThreadPool(producers + consumers);
		for (int p = 0; p < producers; p++) {
			pool.submit(() -> {
				for (int i = 1; i <= perProducer; i++) q.put(i);
				return null;
			});
		}
		for (int c = 0; c < consumers; c++) {
			pool.submit(() -> {
				for (int i = 0; i < perProducer; i++) consumedSum.addAndGet(q.take());
				return null;
			});
		}
		pool.shutdown();
		Check.isTrue("finished without deadlock", pool.awaitTermination(30, TimeUnit.SECONDS));
		long expected = (long) producers * perProducer * (perProducer + 1) / 2;
		Check.eq("every item consumed exactly once", consumedSum.get(), expected);
		Check.eq("queue drained", q.size(), 0);
	}
}
