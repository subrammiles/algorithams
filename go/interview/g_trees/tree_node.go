// Package g_trees: tree recursion patterns.
package g_trees

// TreeNode is a binary tree node.
type TreeNode struct {
	Val         int
	Left, Right *TreeNode
}

// Null marks a missing child in Build's level-order input.
const Null = -1 << 31

// Build creates a tree from LeetCode-style level order, using Null for missing children.
func Build(levelOrder ...int) *TreeNode {
	if len(levelOrder) == 0 || levelOrder[0] == Null {
		return nil
	}
	root := &TreeNode{Val: levelOrder[0]}
	queue := []*TreeNode{root}
	for i := 1; i < len(levelOrder); {
		n := queue[0]
		queue = queue[1:]
		for _, child := range []**TreeNode{&n.Left, &n.Right} {
			if i < len(levelOrder) && levelOrder[i] != Null {
				*child = &TreeNode{Val: levelOrder[i]}
				queue = append(queue, *child)
			}
			i++
		}
	}
	return root
}

func Find(root *TreeNode, val int) *TreeNode {
	if root == nil || root.Val == val {
		return root
	}
	if l := Find(root.Left, val); l != nil {
		return l
	}
	return Find(root.Right, val)
}
