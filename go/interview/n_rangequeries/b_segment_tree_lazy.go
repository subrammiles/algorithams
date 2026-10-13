package n_rangequeries

// SegmentTreeLazy supports range add and range sum in O(log n).
type SegmentTreeLazy struct {
	sum, lazy []int
	n         int
}

func NewSegmentTreeLazy(values []int) *SegmentTreeLazy {
	st := &SegmentTreeLazy{sum: make([]int, 4*len(values)), lazy: make([]int, 4*len(values)), n: len(values)}
	var build func(node, l, r int)
	build = func(node, l, r int) {
		if l == r {
			st.sum[node] = values[l]
			return
		}
		mid := (l + r) / 2
		build(2*node, l, mid)
		build(2*node+1, mid+1, r)
		st.sum[node] = st.sum[2*node] + st.sum[2*node+1]
	}
	build(1, 0, st.n-1)
	return st
}

func (s *SegmentTreeLazy) apply(node, l, r, delta int) {
	s.sum[node] += delta * (r - l + 1)
	s.lazy[node] += delta
}

func (s *SegmentTreeLazy) push(node, l, r int) {
	if s.lazy[node] != 0 {
		mid := (l + r) / 2
		s.apply(2*node, l, mid, s.lazy[node])
		s.apply(2*node+1, mid+1, r, s.lazy[node])
		s.lazy[node] = 0
	}
}

func (s *SegmentTreeLazy) RangeAdd(ql, qr, delta int) { s.update(1, 0, s.n-1, ql, qr, delta) }
func (s *SegmentTreeLazy) RangeSum(ql, qr int) int    { return s.query(1, 0, s.n-1, ql, qr) }

func (s *SegmentTreeLazy) update(node, l, r, ql, qr, delta int) {
	if qr < l || r < ql {
		return
	}
	if ql <= l && r <= qr {
		s.apply(node, l, r, delta)
		return
	}
	s.push(node, l, r)
	mid := (l + r) / 2
	s.update(2*node, l, mid, ql, qr, delta)
	s.update(2*node+1, mid+1, r, ql, qr, delta)
	s.sum[node] = s.sum[2*node] + s.sum[2*node+1]
}

func (s *SegmentTreeLazy) query(node, l, r, ql, qr int) int {
	if qr < l || r < ql {
		return 0
	}
	if ql <= l && r <= qr {
		return s.sum[node]
	}
	s.push(node, l, r)
	mid := (l + r) / 2
	return s.query(2*node, l, mid, ql, qr) + s.query(2*node+1, mid+1, r, ql, qr)
}
