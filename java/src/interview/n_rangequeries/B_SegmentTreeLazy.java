package interview.n_rangequeries;

import common.Check;

/*
 * Segment tree with lazy propagation: range add + range sum, both O(log n).
 *
 * When to pick what:
 *   - Static array, range sum            -> prefix sums, O(1) query
 *   - Point update + prefix/range sum    -> Fenwick tree (simpler)
 *   - Range update + range query, or min/max/gcd/custom merge -> segment tree
 *   - Static range min, many queries     -> sparse table, O(1) query
 *
 * lazy[node] = pending addition for every element in node's range, not yet pushed to children.
 */
public class B_SegmentTreeLazy {

	private final long[] sum, lazy;
	private final int n;

	B_SegmentTreeLazy(int[] values) {
		n = values.length;
		sum = new long[4 * n];
		lazy = new long[4 * n];
		build(1, 0, n - 1, values);
	}

	private void build(int node, int l, int r, int[] v) {
		if (l == r) {
			sum[node] = v[l];
			return;
		}
		int mid = (l + r) / 2;
		build(2 * node, l, mid, v);
		build(2 * node + 1, mid + 1, r, v);
		sum[node] = sum[2 * node] + sum[2 * node + 1];
	}

	void rangeAdd(int ql, int qr, long delta) {
		update(1, 0, n - 1, ql, qr, delta);
	}

	long rangeSum(int ql, int qr) {
		return query(1, 0, n - 1, ql, qr);
	}

	private void apply(int node, int l, int r, long delta) {
		sum[node] += delta * (r - l + 1);
		lazy[node] += delta;
	}

	private void push(int node, int l, int r) {
		if (lazy[node] != 0) {
			int mid = (l + r) / 2;
			apply(2 * node, l, mid, lazy[node]);
			apply(2 * node + 1, mid + 1, r, lazy[node]);
			lazy[node] = 0;
		}
	}

	private void update(int node, int l, int r, int ql, int qr, long delta) {
		if (qr < l || r < ql) return;
		if (ql <= l && r <= qr) {
			apply(node, l, r, delta);
			return;
		}
		push(node, l, r);
		int mid = (l + r) / 2;
		update(2 * node, l, mid, ql, qr, delta);
		update(2 * node + 1, mid + 1, r, ql, qr, delta);
		sum[node] = sum[2 * node] + sum[2 * node + 1];
	}

	private long query(int node, int l, int r, int ql, int qr) {
		if (qr < l || r < ql) return 0;
		if (ql <= l && r <= qr) return sum[node];
		push(node, l, r);
		int mid = (l + r) / 2;
		return query(2 * node, l, mid, ql, qr) + query(2 * node + 1, mid + 1, r, ql, qr);
	}

	public static void main(String[] args) {
		B_SegmentTreeLazy st = new B_SegmentTreeLazy(new int[] { 1, 2, 3, 4, 5 });
		Check.eq("sum all", st.rangeSum(0, 4), 15L);
		st.rangeAdd(1, 3, 10);
		Check.eq("sum 0..4 after add", st.rangeSum(0, 4), 45L);
		Check.eq("sum 2..2", st.rangeSum(2, 2), 13L);
		Check.eq("sum 3..4", st.rangeSum(3, 4), 19L);
	}
}
