"""Read-write lock and a fixed worker pool (full explanations in the Java version).

ReadWriteLock   threading.Condition; writer preference (readers wait while a writer is waiting),
                so a stream of readers cannot starve writers. Always wait in a while loop.
WorkerPool      N threads loop on queue.Queue.get(); shutdown puts one sentinel per worker and joins.
                A task that raises must not kill its worker.
Production: concurrent.futures.ThreadPoolExecutor (threads, IO-bound) / ProcessPoolExecutor (CPU-bound,
because of the GIL). Python has no RW lock in the stdlib, so this one is worth knowing.
"""
import queue
import threading

_STOP = object()


class ReadWriteLock:
    def __init__(self):
        self._cond = threading.Condition()
        self._readers = 0
        self._waiting_writers = 0
        self._writing = False

    def lock_read(self):
        with self._cond:
            while self._writing or self._waiting_writers:
                self._cond.wait()
            self._readers += 1

    def unlock_read(self):
        with self._cond:
            self._readers -= 1
            if self._readers == 0:
                self._cond.notify_all()

    def lock_write(self):
        with self._cond:
            self._waiting_writers += 1
            try:
                while self._writing or self._readers:
                    self._cond.wait()
            finally:
                self._waiting_writers -= 1
            self._writing = True

    def unlock_write(self):
        with self._cond:
            self._writing = False
            self._cond.notify_all()


class WorkerPool:
    def __init__(self, threads):
        self._tasks = queue.Queue()
        self._shutdown = False
        self._workers = [threading.Thread(target=self._loop) for _ in range(threads)]
        for w in self._workers:
            w.start()

    def _loop(self):
        while True:
            task = self._tasks.get()
            if task is _STOP:
                return
            try:
                task()
            except Exception:  # a failing task must not kill the worker
                pass

    def submit(self, task):
        if self._shutdown:
            raise RuntimeError("pool is shut down")
        self._tasks.put(task)

    def shutdown_and_wait(self):
        self._shutdown = True
        for _ in self._workers:
            self._tasks.put(_STOP)  # FIFO: sentinels go after the real tasks
        for w in self._workers:
            w.join()


if __name__ == "__main__":
    rw = ReadWriteLock()
    state = {"readers": 0, "writers": 0, "violations": 0, "counter": 0, "writes": 0}
    guard = threading.Lock()  # only protects the test's bookkeeping counters

    def worker():
        for i in range(1000):
            if i % 10 == 0:
                rw.lock_write()
                with guard:
                    state["writers"] += 1
                    if state["writers"] != 1 or state["readers"]:
                        state["violations"] += 1
                state["counter"] += 1
                state["writes"] += 1
                with guard:
                    state["writers"] -= 1
                rw.unlock_write()
            else:
                rw.lock_read()
                with guard:
                    state["readers"] += 1
                    if state["writers"]:
                        state["violations"] += 1
                with guard:
                    state["readers"] -= 1
                rw.unlock_read()

    threads = [threading.Thread(target=worker) for _ in range(8)]
    for t in threads:
        t.start()
    for t in threads:
        t.join()
    assert state["violations"] == 0
    assert state["counter"] == state["writes"] == 800

    pool = WorkerPool(4)
    total = [0]
    running = [0, 0]  # current, max
    lock = threading.Lock()

    def make_task(v):
        def task():
            with lock:
                running[0] += 1
                running[1] = max(running[1], running[0])
                total[0] += v
                running[0] -= 1
        return task

    for v in range(1, 1001):
        pool.submit(make_task(v))

    def boom():
        raise ValueError("boom")

    pool.submit(boom)
    pool.submit(make_task(1))
    pool.shutdown_and_wait()
    assert total[0] == 500_500 + 1
    assert running[1] <= 4
    try:
        pool.submit(make_task(1))
        raise AssertionError("submit after shutdown should fail")
    except RuntimeError:
        pass
    print("all checks passed")
