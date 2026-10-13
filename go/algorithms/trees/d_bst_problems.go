package trees

import "math"

// RecoverTree fixes a BST where two nodes were swapped (inorder sees one or two drops).
func RecoverTree(root *TreeNode) {
	var prev, first, second *TreeNode
	var walk func(*TreeNode)
	walk = func(n *TreeNode) {
		if n == nil {
			return
		}
		walk(n.Left)
		if prev != nil && prev.Val > n.Val {
			if first == nil {
				first = prev
			}
			second = n
		}
		prev = n
		walk(n.Right)
	}
	walk(root)
	first.Val, second.Val = second.Val, first.Val
}

// InorderSuccessor returns the smallest value > x, or ok=false.
func InorderSuccessor(root *TreeNode, x int) (int, bool) {
	candidate, found := 0, false
	for n := root; n != nil; {
		if x < n.Val {
			candidate, found = n.Val, true
			n = n.Left
		} else {
			n = n.Right
		}
	}
	return candidate, found
}

func TrimBST(n *TreeNode, low, high int) *TreeNode {
	if n == nil {
		return nil
	}
	if n.Val < low {
		return TrimBST(n.Right, low, high)
	}
	if n.Val > high {
		return TrimBST(n.Left, low, high)
	}
	n.Left, n.Right = TrimBST(n.Left, low, high), TrimBST(n.Right, low, high)
	return n
}

// BSTFromPreorder passes an upper bound down; each value is consumed once. O(n).
func BSTFromPreorder(preorder []int) *TreeNode {
	i := 0
	var build func(bound int) *TreeNode
	build = func(bound int) *TreeNode {
		if i == len(preorder) || preorder[i] > bound {
			return nil
		}
		n := &TreeNode{Val: preorder[i]}
		i++
		n.Left = build(n.Val)
		n.Right = build(bound)
		return n
	}
	return build(math.MaxInt)
}
