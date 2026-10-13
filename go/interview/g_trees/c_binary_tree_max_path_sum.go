package g_trees

import "math"

// MaxPathSum: return the best downward path, record the best bending path. O(n).
func MaxPathSum(root *TreeNode) int {
	best := math.MinInt
	var gain func(*TreeNode) int
	gain = func(n *TreeNode) int {
		if n == nil {
			return 0
		}
		l, r := max(0, gain(n.Left)), max(0, gain(n.Right))
		best = max(best, n.Val+l+r)
		return n.Val + max(l, r)
	}
	gain(root)
	return best
}

func Diameter(root *TreeNode) int {
	best := 0
	var depth func(*TreeNode) int
	depth = func(n *TreeNode) int {
		if n == nil {
			return 0
		}
		l, r := depth(n.Left), depth(n.Right)
		best = max(best, l+r)
		return 1 + max(l, r)
	}
	depth(root)
	return best
}
