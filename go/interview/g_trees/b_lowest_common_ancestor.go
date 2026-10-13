package g_trees

// LCA in a binary tree: O(n).
func LCA(root, p, q *TreeNode) *TreeNode {
	if root == nil || root == p || root == q {
		return root
	}
	left, right := LCA(root.Left, p, q), LCA(root.Right, p, q)
	if left != nil && right != nil {
		return root
	}
	if left != nil {
		return left
	}
	return right
}

// LCABST walks down a BST: O(h).
func LCABST(root *TreeNode, p, q int) *TreeNode {
	for root != nil {
		switch {
		case p < root.Val && q < root.Val:
			root = root.Left
		case p > root.Val && q > root.Val:
			root = root.Right
		default:
			return root
		}
	}
	return nil
}
