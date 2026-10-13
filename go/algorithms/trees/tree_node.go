// Package trees: tree algorithms - traversal variants, construction, paths, BST problems,
// properties, tree DP and binary lifting.
package trees

// Null marks a missing child in Build's level-order input.
const Null = -1 << 31

type TreeNode struct {
	Val         int
	Left, Right *TreeNode
}

// Build creates a tree from LeetCode-style level order (Null = missing child).
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

// ToLevelOrder is the inverse of Build (trailing Nulls trimmed).
func ToLevelOrder(root *TreeNode) []int {
	var out []int
	queue := []*TreeNode{root}
	for len(queue) > 0 {
		n := queue[0]
		queue = queue[1:]
		if n == nil {
			out = append(out, Null)
			continue
		}
		out = append(out, n.Val)
		queue = append(queue, n.Left, n.Right)
	}
	for len(out) > 0 && out[len(out)-1] == Null {
		out = out[:len(out)-1]
	}
	return out
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

func Inorder(n *TreeNode) []int {
	if n == nil {
		return nil
	}
	return append(append(Inorder(n.Left), n.Val), Inorder(n.Right)...)
}
