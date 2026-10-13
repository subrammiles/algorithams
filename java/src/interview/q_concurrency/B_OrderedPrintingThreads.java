package interview.q_concurrency;

import java.util.concurrent.Semaphore;

import interview.Check;

/*
 * Problem (Print FooBar / ZeroEvenOdd family): coordinate threads so output is in a fixed order.
 *
 * Tool choice:
 *  - Semaphore: "permission to run" handed from one thread to the next. Cleanest for strict turns.
 *  - CountDownLatch: one-shot "wait until X happened" (e.g. Print in Order).
 *  - CyclicBarrier: all N threads meet each round.
 *  - Lock + Condition / wait-notify: general state machine.
 *
 * Here: three threads print 0 / odd / even so the output is 0 1 0 2 0 3 ... 0 n.
 */
public class B_OrderedPrintingThreads {

	private final int n;
	private final StringBuffer out = new StringBuffer(); // thread-safe builder
	private final Semaphore zeroTurn = new Semaphore(1), oddTurn = new Semaphore(0), evenTurn = new Semaphore(0);

	B_OrderedPrintingThreads(int n) {
		this.n = n;
	}

	void zero() throws InterruptedException {
		for (int i = 1; i <= n; i++) {
			zeroTurn.acquire();
			out.append(0);
			if (i % 2 == 1) oddTurn.release();
			else evenTurn.release();
		}
	}

	void odd() throws InterruptedException {
		for (int i = 1; i <= n; i += 2) {
			oddTurn.acquire();
			out.append(i);
			zeroTurn.release();
		}
	}

	void even() throws InterruptedException {
		for (int i = 2; i <= n; i += 2) {
			evenTurn.acquire();
			out.append(i);
			zeroTurn.release();
		}
	}

	static String run(int n) throws InterruptedException {
		B_OrderedPrintingThreads p = new B_OrderedPrintingThreads(n);
		Thread[] threads = { new Thread(wrap(p::zero)), new Thread(wrap(p::odd)), new Thread(wrap(p::even)) };
		for (Thread t : threads) t.start();
		for (Thread t : threads) t.join();
		return p.out.toString();
	}

	interface Step {
		void run() throws InterruptedException;
	}

	private static Runnable wrap(Step s) {
		return () -> {
			try {
				s.run();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		};
	}

	public static void main(String[] args) throws Exception {
		for (int attempt = 0; attempt < 50; attempt++) { // repeat to shake out races
			if (!run(5).equals("0102030405")) throw new AssertionError("FAIL wrong order");
		}
		Check.eq("zero-odd-even n=5", run(5), "0102030405");
		Check.eq("zero-odd-even n=2", run(2), "0102");
	}
}
