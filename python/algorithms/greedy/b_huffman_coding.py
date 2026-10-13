"""Huffman coding: repeatedly merge the two least frequent trees (min-heap). O(n log n).
Optimal prefix-free code; total bits = sum(freq * code length). Test the cost, not exact codes.
"""
import heapq
from collections import Counter
from itertools import count


def build_tree(freq):
    order = count()  # tie-breaker so heapq never compares trees
    heap = [(f, next(order), sym) for sym, f in sorted(freq.items())]
    heapq.heapify(heap)
    while len(heap) > 1:
        f1, _, a = heapq.heappop(heap)
        f2, _, b = heapq.heappop(heap)
        heapq.heappush(heap, (f1 + f2, next(order), (a, b)))  # internal node = (left, right)
    return heap[0][2]


def codes(tree):
    out = {}

    def assign(node, prefix):
        if isinstance(node, tuple):
            assign(node[0], prefix + "0")
            assign(node[1], prefix + "1")
        else:
            out[node] = prefix or "0"

    assign(tree, "")
    return out


def decode(bits, tree):
    out, node = [], tree
    for b in bits:
        node = node[0] if b == "0" else node[1]
        if not isinstance(node, tuple):
            out.append(node)
            node = tree
    return "".join(out)


if __name__ == "__main__":
    freq = dict(zip("abcdef", [5, 9, 12, 13, 16, 45]))
    c = codes(build_tree(freq))
    assert sum(freq[s] * len(code) for s, code in c.items()) == 224
    assert len(c["f"]) == 1
    assert not any(x != y and y.startswith(x) for x in c.values() for y in c.values())  # prefix free
    text = "abracadabra"
    tree = build_tree(Counter(text))
    bits = "".join(codes(tree)[ch] for ch in text)
    assert decode(bits, tree) == text and len(bits) < 8 * len(text)
    print("all checks passed")
