"""Parallel merge sort with a process pool.

Threads don't speed up CPU-bound Python (GIL), so use processes: sort chunks in parallel,
then k-way merge with heapq.merge. Below a threshold, sequential is faster (task overhead).
Speed-up is bounded by the final sequential merge (Amdahl's law).
"""
import heapq
import random
import time
from concurrent.futures import ProcessPoolExecutor


def parallel_sort(data, workers=4):
    if len(data) < 50_000:
        return sorted(data)
    size = (len(data) + workers - 1) // workers
    chunks = [data[i:i + size] for i in range(0, len(data), size)]
    with ProcessPoolExecutor(max_workers=workers) as pool:
        sorted_chunks = list(pool.map(sorted, chunks))
    return list(heapq.merge(*sorted_chunks))


if __name__ == "__main__":
    rnd = random.Random(1)
    data = [rnd.randint(-10**9, 10**9) for _ in range(400_000)]
    start = time.perf_counter()
    result = parallel_sort(data)
    print(f"parallel sort of {len(data)} ints took {(time.perf_counter() - start) * 1000:.0f} ms")
    assert result == sorted(data)
    print("all checks passed")
