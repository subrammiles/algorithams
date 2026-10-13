package g_trees

import (
	"strconv"
	"strings"
)

// Serialize writes preorder with "#" for nil children. O(n).
func Serialize(root *TreeNode) string {
	var parts []string
	var write func(*TreeNode)
	write = func(n *TreeNode) {
		if n == nil {
			parts = append(parts, "#")
			return
		}
		parts = append(parts, strconv.Itoa(n.Val))
		write(n.Left)
		write(n.Right)
	}
	write(root)
	return strings.Join(parts, ",")
}

func Deserialize(data string) *TreeNode {
	tokens := strings.Split(data, ",")
	i := 0
	var read func() *TreeNode
	read = func() *TreeNode {
		t := tokens[i]
		i++
		if t == "#" {
			return nil
		}
		v, _ := strconv.Atoi(t)
		n := &TreeNode{Val: v}
		n.Left = read()
		n.Right = read()
		return n
	}
	return read()
}
