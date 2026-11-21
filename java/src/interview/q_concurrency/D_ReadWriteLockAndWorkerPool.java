package interview.q_concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import common.Check;

/*
 * Two "build it yourself" concurrency questions that come up in senior rounds.
 *
 * 1. Read-write lock (many readers OR one writer).
 *    State: readers (active count), writing (bool), waitingWriters (count).
 *      lockRead:   wait while writing || waitingWriters > 0     <- writer preference
 *      lockWrite:  waitingWriters++; wait while writing || readers > 0; waitingWriters--; writing = true
 *      unlock*:    update state, notifyAll
 *    Why writer preference? With a steady stream of readers, readers-preference never lets a
 *    writer in (writer starvation). The cost is that readers can starve under constant writes;
 *    a FIFO ticket queue (see the JS version) is fair to both.
 *    Always wait in a while loop (spurious wake-ups, and the condition may change before you run).
 *    Production: java.util.concurrent.locks.ReentrantReadWriteLock, or StampedLock (optimistic reads).
 *
 * 2. Fixed-size worker pool (thread pool).
 *    N threads loop: take a task from a blocking queue, run it. Shutdown puts one "poison pill" per
 *    worker in the queue, then joins the threads - tasks submitted before shutdown still run.
 *    A task that throws must not kill its worker (catch and keep looping).
 *    Production: Executors.newFixedThreadPool / ThreadPoolExecutor. Discuss: bounded queue +
 *    rejection policy (back-pressure), sizing (CPU-bound ~ #cores, IO-bound more), graceful vs
 *    immediate shutdown (shutdown() vs shutdownNow() + interrupts).
 *
 * Senior follow-ups:
 *  - Upgrade read -> write lock? Deadlocks if two readers try it at once; ReentrantReadWriteLock
 *    forbids it (downgrade write -> read is allowed).
 *  - Reentrancy: track owner thread + hold count.
 *  - How do you TEST concurrent code? Check invariants under load (as below), repeat many times,
 *    use stress tools (jcstress), and keep the critical sections tiny.
 */
public class D_ReadWriteLockAndWorkerPool {

	static final class ReadWriteLock {
		private int readers = 0, waitingWriters = 0;
		private boolean writing = false;

		synchronized void lockRead() throws InterruptedException {
			while (writing || waitingWriters > 0) wait();
			readers++;
		}

		synchronized void unlockRead() {
			if (--readers == 0) notifyAll(); // a waiting writer may go now
		}

		synchronized void lockWrite() throws InterruptedException {
			waitingWriters++;
			try {
				while (writing || readers > 0) wait();
			} finally {
				waitingWriters--;
			}
			writing = true;
		}

		synchronized void unlockWrite() {
			writing = false;
			notifyAll(); // wake readers AND writers; whoever's condition holds proceeds
		}
	}

	static final class WorkerPool {
		private static final Runnable POISON = () -> {
		};
		private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
		private final List<Thread> workers = new ArrayList<>();
		private volatile boolean shutdown = false;

		WorkerPool(int threads) {
			for (int i = 0; i < threads; i++) {
				Thread t = new Thread(this::workLoop, "worker-" + i);
				workers.add(t);
				t.start();
			}
		}

		private void workLoop() {
			try {
				while (true) {
					Runnable task = queue.take();
					if (task == POISON) return;
					try {
						task.run();
					} catch (RuntimeException e) {
						// a failing task must not kill the worker; real pools log it / pass it to a handler
					}
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		void submit(Runnable task) {
			if (shutdown) throw new IllegalStateException("pool is shut down");
			queue.add(task);
		}

		void shutdownAndWait() throws InterruptedException {
			shutdown = true;
			for (int i = 0; i < workers.size(); i++) queue.add(POISON); // FIFO: pills go after the real tasks
			for (Thread t : workers) t.join();
		}
	}

	private static int sharedCounter = 0; // protected by the write lock only

	public static void main(String[] args) throws Exception {
		ReadWriteLock rw = new ReadWriteLock();
		AtomicInteger activeReaders = new AtomicInteger(), activeWriters = new AtomicInteger();
		AtomicInteger violations = new AtomicInteger(), writes = new AtomicInteger();
		List<Thread> threads = new ArrayList<>();
		for (int t = 0; t < 8; t++) {
			Thread th = new Thread(() -> {
				try {
					for (int i = 0; i < 2000; i++) {
						if (i % 10 == 0) {
							rw.lockWrite();
							if (activeWriters.incrementAndGet() != 1 || activeReaders.get() != 0) violations.incrementAndGet();
							sharedCounter++; // not atomic: only safe because writers are exclusive
							writes.incrementAndGet();
							activeWriters.decrementAndGet();
							rw.unlockWrite();
						} else {
							rw.lockRead();
							activeReaders.incrementAndGet();
							if (activeWriters.get() != 0) violations.incrementAndGet();
							activeReaders.decrementAndGet();
							rw.unlockRead();
						}
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			});
			threads.add(th);
			th.start();
		}
		for (Thread th : threads) th.join();
		Check.eq("rw lock: no reader/writer overlap", violations.get(), 0);
		Check.eq("rw lock: every write counted", sharedCounter, writes.get());

		WorkerPool pool = new WorkerPool(4);
		AtomicLong sum = new AtomicLong();
		AtomicInteger running = new AtomicInteger(), maxRunning = new AtomicInteger();
		for (int i = 1; i <= 1000; i++) {
			final int v = i;
			pool.submit(() -> {
				int now = running.incrementAndGet();
				maxRunning.accumulateAndGet(now, Math::max);
				sum.addAndGet(v);
				running.decrementAndGet();
			});
		}
		pool.submit(() -> {
			throw new RuntimeException("boom"); // must not kill a worker
		});
		pool.submit(() -> sum.addAndGet(1));
		pool.shutdownAndWait();
		Check.eq("pool ran every task", sum.get(), 500_500L + 1);
		Check.isTrue("pool never ran more than 4 at once", maxRunning.get() <= 4);
		boolean rejected = false;
		try {
			pool.submit(() -> {
			});
		} catch (IllegalStateException e) {
			rejected = true;
		}
		Check.isTrue("submit after shutdown is rejected", rejected);
	}
}
