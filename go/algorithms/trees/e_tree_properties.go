package trees

func IsSymmetric(root *TreeNode) bool {
	var mirror func(a, b *TreeNode) bool
	mirror = func(a, b *TreeNode) bool {
		if a == nil || b == nil {
			return a == b
		}
		return a.Val == b.Val && mirror(a.Left, b.Right) && mirror(a.Right, b.Left)
	}
	return root == nil || mirror(root.Left, root.Right)
}

func sameTree(a, b *TreeNode) bool {
	if a == nil || b == nil {
		return a == b
	}
	return a.Val == b.Val && sameTree(a.Left, b.Left) && sameTree(a.Right, b.Right)
}

func IsSubtree(root, sub *TreeNode) bool {
	if root == nil {
		return sub == nil
	}
	return sameTree(root, sub) || IsSubtree(root.Left, sub) || IsSubtree(root.Right, sub)
}

// Flatten rewires the tree into a preorder "linked list" along Right pointers.
func Flatten(root *TreeNode) {
	var prev *TreeNode
	var walk func(*TreeNode)
	walk = func(n *TreeNode) {
		if n == nil {
			return
		}
		walk(n.Right)
		walk(n.Left)
		n.Right, n.Left, prev = prev, nil, n
	}
	walk(root)
}

// CountCompleteNodes is O(log^2 n): perfect subtrees are counted with 2^h - 1.
func CountCompleteNodes(root *TreeNode) int {
	if root == nil {
		return 0
	}
	leftDepth, rightDepth := 0, 0
	for n := root; n != nil; n = n.Left {
		leftDepth++
	}
	for n := root; n != nil; n = n.Right {
		rightDepth++
	}
	if leftDepth == rightDepth {
		return 1<<leftDepth - 1
	}
	return 1 + CountCompleteNodes(root.Left) + CountCompleteNodes(root.Right)
}
