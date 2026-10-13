package g_trees

import "math"

// IsValidBST passes (low, high) bounds down; int64 bounds avoid MinInt/MaxInt edge cases.
func IsValidBST(root *TreeNode) bool {
	var valid func(*TreeNode, int64, int64) bool
	valid = func(n *TreeNode, low, high int64) bool {
		if n == nil {
			return true
		}
		v := int64(n.Val)
		return v > low && v < high && valid(n.Left, low, v) && valid(n.Right, v, high)
	}
	return valid(root, math.MinInt64, math.MaxInt64)
}

func InorderIterative(root *TreeNode) []int {
	var out []int
	var stack []*TreeNode
	for cur := root; cur != nil || len(stack) > 0; {
		for cur != nil {
			stack = append(stack, cur)
			cur = cur.Left
		}
		cur = stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		out = append(out, cur.Val)
		cur = cur.Right
	}
	return out
}

// InorderMorris uses O(1) extra space by temporarily threading right pointers.
func InorderMorris(root *TreeNode) []int {
	var out []int
	for cur := root; cur != nil; {
		if cur.Left == nil {
			out = append(out, cur.Val)
			cur = cur.Right
			continue
		}
		pred := cur.Left
		for pred.Right != nil && pred.Right != cur {
			pred = pred.Right
		}
		if pred.Right == nil {
			pred.Right = cur
			cur = cur.Left
		} else {
			pred.Right = nil
			out = append(out, cur.Val)
			cur = cur.Right
		}
	}
	return out
}
