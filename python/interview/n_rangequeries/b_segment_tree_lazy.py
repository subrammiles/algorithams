"""Segment tree with lazy propagation: range add + range sum, both O(log n).

Static sums -> prefix sums. Point update + sums -> Fenwick. Range update / min / max / custom -> this.
lazy[node] = pending addition for every element in the node's range.
"""


class SegmentTreeLazy:
    def __init__(self, values):
        self.n = len(values)
        self.sum = [0] * (4 * self.n)
        self.lazy = [0] * (4 * self.n)
        self._build(1, 0, self.n - 1, values)

    def _build(self, node, l, r, v):
        if l == r:
            self.sum[node] = v[l]
            return
        mid = (l + r) // 2
        self._build(2 * node, l, mid, v)
        self._build(2 * node + 1, mid + 1, r, v)
        self.sum[node] = self.sum[2 * node] + self.sum[2 * node + 1]

    def _apply(self, node, l, r, delta):
        self.sum[node] += delta * (r - l + 1)
        self.lazy[node] += delta

    def _push(self, node, l, r):
        if self.lazy[node]:
            mid = (l + r) // 2
            self._apply(2 * node, l, mid, self.lazy[node])
            self._apply(2 * node + 1, mid + 1, r, self.lazy[node])
            self.lazy[node] = 0

    def range_add(self, ql, qr, delta, node=1, l=0, r=None):
        r = self.n - 1 if r is None else r
        if qr < l or r < ql:
            return
        if ql <= l and r <= qr:
            self._apply(node, l, r, delta)
            return
        self._push(node, l, r)
        mid = (l + r) // 2
        self.range_add(ql, qr, delta, 2 * node, l, mid)
        self.range_add(ql, qr, delta, 2 * node + 1, mid + 1, r)
        self.sum[node] = self.sum[2 * node] + self.sum[2 * node + 1]

    def range_sum(self, ql, qr, node=1, l=0, r=None):
        r = self.n - 1 if r is None else r
        if qr < l or r < ql:
            return 0
        if ql <= l and r <= qr:
            return self.sum[node]
        self._push(node, l, r)
        mid = (l + r) // 2
        return self.range_sum(ql, qr, 2 * node, l, mid) + self.range_sum(ql, qr, 2 * node + 1, mid + 1, r)


if __name__ == "__main__":
    st = SegmentTreeLazy([1, 2, 3, 4, 5])
    assert st.range_sum(0, 4) == 15
    st.range_add(1, 3, 10)
    assert st.range_sum(0, 4) == 45
    assert st.range_sum(2, 2) == 13
    assert st.range_sum(3, 4) == 19
    print("all checks passed")
