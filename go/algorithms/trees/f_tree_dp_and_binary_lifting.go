package trees

// Rob (House Robber III): each node returns (best if robbed, best if not robbed).
func Rob(root *TreeNode) int {
	var pair func(*TreeNode) (int, int)
	pair = func(n *TreeNode) (int, int) {
		if n == nil {
			return 0, 0
		}
		lr, ln := pair(n.Left)
		rr, rn := pair(n.Right)
		return n.Val + ln + rn, max(lr, ln) + max(rr, rn)
	}
	return max(pair(root))
}

// MinCameraCover: post-order states 0 = not covered, 1 = has camera, 2 = covered.
func MinCameraCover(root *TreeNode) int {
	cameras := 0
	var cover func(*TreeNode) int
	cover = func(n *TreeNode) int {
		if n == nil {
			return 2
		}
		l, r := cover(n.Left), cover(n.Right)
		if l == 0 || r == 0 {
			cameras++
			return 1
		}
		if l == 1 || r == 1 {
			return 2
		}
		return 0
	}
	if cover(root) == 0 {
		cameras++
	}
	return cameras
}

// BinaryLifting: up[j][v] = 2^j-th ancestor of v; O(log n) k-th ancestor and LCA queries.
type BinaryLifting struct {
	up    [][]int
	depth []int
	log   int
}

func NewBinaryLifting(parent []int) *BinaryLifting {
	n := len(parent)
	lg := 1
	for 1<<lg < n {
		lg++
	}
	bl := &BinaryLifting{up: [][]int{append([]int(nil), parent...)}, depth: make([]int, n), log: lg}
	children := make([][]int, n)
	root := 0
	for v, p := range parent {
		if p == -1 {
			root = v
		} else {
			children[p] = append(children[p], v)
		}
	}
	stack := []int{root}
	for len(stack) > 0 {
		v := stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		for _, c := range children[v] {
			bl.depth[c] = bl.depth[v] + 1
			stack = append(stack, c)
		}
	}
	for j := 1; j <= lg; j++ {
		prev, row := bl.up[j-1], make([]int, n)
		for v := range row {
			if prev[v] == -1 {
				row[v] = -1
			} else {
				row[v] = prev[prev[v]]
			}
		}
		bl.up = append(bl.up, row)
	}
	return bl
}

func (b *BinaryLifting) KthAncestor(v, k int) int {
	if k >= 1<<(b.log+1) {
		return -1
	}
	for j := 0; j <= b.log && v != -1; j++ {
		if k>>j&1 == 1 {
			v = b.up[j][v]
		}
	}
	return v
}

func (b *BinaryLifting) LCA(x, y int) int {
	if b.depth[x] < b.depth[y] {
		x, y = y, x
	}
	x = b.KthAncestor(x, b.depth[x]-b.depth[y])
	if x == y {
		return x
	}
	for j := b.log; j >= 0; j-- {
		if b.up[j][x] != b.up[j][y] {
			x, y = b.up[j][x], b.up[j][y]
		}
	}
	return b.up[0][x]
}
