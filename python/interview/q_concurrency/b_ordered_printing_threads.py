"""Zero-Even-Odd: three threads print 0 1 0 2 0 3 ... 0 n in order, coordinated with semaphores.
Semaphore = permission to run, handed from one thread to the next.
(Event = one-shot signal, Barrier = all N threads meet, Condition = general state machine.)
"""
import threading


def run(n):
    out = []
    zero_turn, odd_turn, even_turn = threading.Semaphore(1), threading.Semaphore(0), threading.Semaphore(0)

    def zero():
        for i in range(1, n + 1):
            zero_turn.acquire()
            out.append("0")
            (odd_turn if i % 2 else even_turn).release()

    def odd():
        for i in range(1, n + 1, 2):
            odd_turn.acquire()
            out.append(str(i))
            zero_turn.release()

    def even():
        for i in range(2, n + 1, 2):
            even_turn.acquire()
            out.append(str(i))
            zero_turn.release()

    threads = [threading.Thread(target=f) for f in (zero, odd, even)]
    for t in threads:
        t.start()
    for t in threads:
        t.join()
    return "".join(out)


if __name__ == "__main__":
    for _ in range(50):  # repeat to shake out races
        assert run(5) == "0102030405"
    assert run(2) == "0102"
    print("all checks passed")
