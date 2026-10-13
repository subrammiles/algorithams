package trees

import "sort"

func PathSum(root *TreeNode, target int) [][]int {
	var out [][]int
	var path []int
	var walk func(n *TreeNode, remaining int)
	walk = func(n *TreeNode, remaining int) {
		if n == nil {
			return
		}
		path = append(path, n.Val)
		if n.Left == nil && n.Right == nil && remaining == n.Val {
			out = append(out, append([]int(nil), path...))
		}
		walk(n.Left, remaining-n.Val)
		walk(n.Right, remaining-n.Val)
		path = path[:len(path)-1]
	}
	walk(root, target)
	return out
}

// PathSumAnyDownward counts downward paths with the target sum using prefix sums. O(n).
func PathSumAnyDownward(root *TreeNode, target int) int {
	prefixCount := map[int]int{0: 1}
	var walk func(n *TreeNode, prefix int) int
	walk = func(n *TreeNode, prefix int) int {
		if n == nil {
			return 0
		}
		prefix += n.Val
		count := prefixCount[prefix-target]
		prefixCount[prefix]++
		count += walk(n.Left, prefix) + walk(n.Right, prefix)
		prefixCount[prefix]-- // leaving this path
		return count
	}
	return walk(root, 0)
}

func SumNumbers(root *TreeNode) int {
	var walk func(n *TreeNode, value int) int
	walk = func(n *TreeNode, value int) int {
		if n == nil {
			return 0
		}
		value = value*10 + n.Val
		if n.Left == nil && n.Right == nil {
			return value
		}
		return walk(n.Left, value) + walk(n.Right, value)
	}
	return walk(root, 0)
}

// DistanceK records parents, then BFS outwards from the target in three directions.
func DistanceK(root, target *TreeNode, k int) []int {
	parent := map[*TreeNode]*TreeNode{}
	var record func(n, p *TreeNode)
	record = func(n, p *TreeNode) {
		if n == nil {
			return
		}
		parent[n] = p
		record(n.Left, n)
		record(n.Right, n)
	}
	record(root, nil)
	frontier := []*TreeNode{target}
	seen := map[*TreeNode]bool{target: true}
	for d := 0; d < k; d++ {
		var next []*TreeNode
		for _, n := range frontier {
			for _, nb := range []*TreeNode{n.Left, n.Right, parent[n]} {
				if nb != nil && !seen[nb] {
					seen[nb] = true
					next = append(next, nb)
				}
			}
		}
		frontier = next
	}
	out := make([]int, len(frontier))
	for i, n := range frontier {
		out[i] = n.Val
	}
	sort.Ints(out)
	return out
}
