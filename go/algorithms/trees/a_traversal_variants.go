package trees

import "sort"

func ZigzagLevelOrder(root *TreeNode) [][]int {
	var levels [][]int
	level := []*TreeNode{}
	if root != nil {
		level = append(level, root)
	}
	for len(level) > 0 {
		values := make([]int, len(level))
		var next []*TreeNode
		for i, n := range level {
			if len(levels)%2 == 1 {
				values[len(level)-1-i] = n.Val
			} else {
				values[i] = n.Val
			}
			if n.Left != nil {
				next = append(next, n.Left)
			}
			if n.Right != nil {
				next = append(next, n.Right)
			}
		}
		levels = append(levels, values)
		level = next
	}
	return levels
}

func RightSideView(root *TreeNode) []int {
	var view []int
	var walk func(n *TreeNode, depth int)
	walk = func(n *TreeNode, depth int) {
		if n == nil {
			return
		}
		if depth == len(view) {
			view = append(view, n.Val)
		}
		walk(n.Right, depth+1)
		walk(n.Left, depth+1)
	}
	walk(root, 0)
	return view
}

func VerticalOrder(root *TreeNode) [][]int {
	cells := map[int][][2]int{} // col -> (row, val)
	var walk func(n *TreeNode, row, col int)
	walk = func(n *TreeNode, row, col int) {
		if n == nil {
			return
		}
		cells[col] = append(cells[col], [2]int{row, n.Val})
		walk(n.Left, row+1, col-1)
		walk(n.Right, row+1, col+1)
	}
	walk(root, 0, 0)
	cols := make([]int, 0, len(cells))
	for c := range cells {
		cols = append(cols, c)
	}
	sort.Ints(cols)
	var out [][]int
	for _, c := range cols {
		list := cells[c]
		sort.Slice(list, func(i, j int) bool {
			if list[i][0] != list[j][0] {
				return list[i][0] < list[j][0]
			}
			return list[i][1] < list[j][1]
		})
		column := make([]int, len(list))
		for i, cell := range list {
			column[i] = cell[1]
		}
		out = append(out, column)
	}
	return out
}

func Boundary(root *TreeNode) []int {
	if root == nil {
		return nil
	}
	isLeaf := func(n *TreeNode) bool { return n.Left == nil && n.Right == nil }
	var out []int
	if !isLeaf(root) {
		out = append(out, root.Val)
	}
	for n := root.Left; n != nil; {
		if !isLeaf(n) {
			out = append(out, n.Val)
		}
		if n.Left != nil {
			n = n.Left
		} else {
			n = n.Right
		}
	}
	var leaves func(*TreeNode)
	leaves = func(n *TreeNode) {
		if n == nil {
			return
		}
		if isLeaf(n) {
			out = append(out, n.Val)
		}
		leaves(n.Left)
		leaves(n.Right)
	}
	leaves(root)
	var right []int
	for n := root.Right; n != nil; {
		if !isLeaf(n) {
			right = append(right, n.Val)
		}
		if n.Right != nil {
			n = n.Right
		} else {
			n = n.Left
		}
	}
	for i := len(right) - 1; i >= 0; i-- {
		out = append(out, right[i])
	}
	return out
}
