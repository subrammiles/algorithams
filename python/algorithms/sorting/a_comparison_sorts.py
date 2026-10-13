"""All comparison sorts (see the Java file for the full complexity / stability table).
bubble, selection, insertion, shell, merge (stable), quick (random pivot), 3-way quick, heap.
Python's sorted() is TimSort: stable, O(n log n), O(n) on already-sorted runs.
"""
import random


def bubble_sort(a):
    a = list(a)
    for end in range(len(a) - 1, 0, -1):
        swapped = False
        for i in range(end):
            if a[i] > a[i + 1]:
                a[i], a[i + 1] = a[i + 1], a[i]
                swapped = True
        if not swapped:
            break
    return a


def selection_sort(a):
    a = list(a)
    for i in range(len(a) - 1):
        m = min(range(i, len(a)), key=a.__getitem__)
        a[i], a[m] = a[m], a[i]
    return a


def insertion_sort(a):
    a = list(a)
    for i in range(1, len(a)):
        x, j = a[i], i - 1
        while j >= 0 and a[j] > x:
            a[j + 1] = a[j]
            j -= 1
        a[j + 1] = x
    return a


def shell_sort(a):
    a = list(a)
    gap = 1
    while gap < len(a) // 3:
        gap = 3 * gap + 1
    while gap:
        for i in range(gap, len(a)):
            x, j = a[i], i
            while j >= gap and a[j - gap] > x:
                a[j] = a[j - gap]
                j -= gap
            a[j] = x
        gap //= 3
    return a


def merge_sort(a):
    if len(a) <= 1:
        return list(a)
    mid = len(a) // 2
    left, right = merge_sort(a[:mid]), merge_sort(a[mid:])
    out, i, j = [], 0, 0
    while i < len(left) and j < len(right):
        if left[i] <= right[j]:  # <= keeps it stable
            out.append(left[i])
            i += 1
        else:
            out.append(right[j])
            j += 1
    return out + left[i:] + right[j:]


_rnd = random.Random(7)


def quick_sort(a):
    a = list(a)

    def sort(lo, hi):
        if lo >= hi:
            return
        p = _rnd.randint(lo, hi)
        a[p], a[hi] = a[hi], a[p]
        store = lo
        for i in range(lo, hi):
            if a[i] < a[hi]:
                a[i], a[store] = a[store], a[i]
                store += 1
        a[store], a[hi] = a[hi], a[store]
        sort(lo, store - 1)
        sort(store + 1, hi)

    sort(0, len(a) - 1)
    return a


def quick_sort_3way(a):
    a = list(a)

    def sort(lo, hi):
        if lo >= hi:
            return
        pivot = a[_rnd.randint(lo, hi)]
        lt, i, gt = lo, lo, hi
        while i <= gt:
            if a[i] < pivot:
                a[lt], a[i] = a[i], a[lt]
                lt, i = lt + 1, i + 1
            elif a[i] > pivot:
                a[i], a[gt] = a[gt], a[i]
                gt -= 1
            else:
                i += 1
        sort(lo, lt - 1)
        sort(gt + 1, hi)

    sort(0, len(a) - 1)
    return a


def heap_sort(a):
    a = list(a)

    def sift_down(i, n):
        while True:
            l, r, largest = 2 * i + 1, 2 * i + 2, i
            if l < n and a[l] > a[largest]:
                largest = l
            if r < n and a[r] > a[largest]:
                largest = r
            if largest == i:
                return
            a[i], a[largest] = a[largest], a[i]
            i = largest

    for i in range(len(a) // 2 - 1, -1, -1):
        sift_down(i, len(a))
    for end in range(len(a) - 1, 0, -1):
        a[0], a[end] = a[end], a[0]
        sift_down(0, end)
    return a


if __name__ == "__main__":
    rnd = random.Random(1)
    inputs = [[], [1], [5, 4, 3, 2, 1], [1, 2, 3, 4, 5], [3, 1, 3, 1, 3, 1],
              [rnd.randint(-500, 500) for _ in range(1000)], [rnd.randint(0, 2) for _ in range(1000)]]
    sorts = [bubble_sort, selection_sort, insertion_sort, shell_sort, merge_sort, quick_sort, quick_sort_3way, heap_sort]
    for data in inputs:
        for sort in sorts:
            assert sort(data) == sorted(data), sort.__name__
    print("all checks passed")
