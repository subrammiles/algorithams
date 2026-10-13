"""Job sequencing with deadlines: highest profit first, into the LATEST free slot <= deadline.
Union-find finds that slot in near O(1): after using slot s, link it to s - 1.
"""


def schedule(jobs):
    """jobs: list of (id, deadline, profit). Returns job ids in time order."""
    max_deadline = max(d for _, d, _ in jobs)
    parent = list(range(max_deadline + 1))  # slot 0 = no slot left

    def find(x):
        while parent[x] != x:
            parent[x] = parent[parent[x]]
            x = parent[x]
        return x

    slots = [None] * (max_deadline + 1)
    for job_id, deadline, _ in sorted(jobs, key=lambda j: -j[2]):
        slot = find(deadline)
        if slot > 0:
            slots[slot] = job_id
            parent[slot] = slot - 1
    return [j for j in slots if j is not None]


def total_profit(jobs, chosen):
    return sum(p for job_id, _, p in jobs if job_id in chosen)


if __name__ == "__main__":
    jobs = [("a", 2, 100), ("b", 1, 19), ("c", 2, 27), ("d", 1, 25), ("e", 3, 15)]
    order = schedule(jobs)
    assert order == ["c", "a", "e"] and total_profit(jobs, order) == 142
    jobs2 = [("x", 4, 20), ("y", 1, 10), ("z", 1, 40), ("w", 1, 30)]
    assert total_profit(jobs2, schedule(jobs2)) == 60
    print("all checks passed")
