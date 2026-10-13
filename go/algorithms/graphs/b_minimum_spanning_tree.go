package graphs

import (
	"math"
	"sort"
)

func manhattan(a, b [2]int) int {
	dx, dy := a[0]-b[0], a[1]-b[1]
	if dx < 0 {
		dx = -dx
	}
	if dy < 0 {
		dy = -dy
	}
	return dx + dy
}

// MinCostPrim: array-based Prim, O(V^2) - best for this complete graph.
func MinCostPrim(points [][2]int) int {
	n := len(points)
	best, inTree := make([]int, n), make([]bool, n)
	for i := range best {
		best[i] = math.MaxInt
	}
	best[0] = 0
	total := 0
	for step := 0; step < n; step++ {
		u := -1
		for v := 0; v < n; v++ {
			if !inTree[v] && (u == -1 || best[v] < best[u]) {
				u = v
			}
		}
		inTree[u] = true
		total += best[u]
		for v := 0; v < n; v++ {
			if !inTree[v] {
				best[v] = min(best[v], manhattan(points[u], points[v]))
			}
		}
	}
	return total
}

func MinCostKruskal(points [][2]int) int {
	n := len(points)
	var edges [][3]int
	for i := 0; i < n; i++ {
		for j := i + 1; j < n; j++ {
			edges = append(edges, [3]int{manhattan(points[i], points[j]), i, j})
		}
	}
	sort.Slice(edges, func(a, b int) bool { return edges[a][0] < edges[b][0] })
	parent := make([]int, n)
	for i := range parent {
		parent[i] = i
	}
	var find func(int) int
	find = func(x int) int {
		if parent[x] != x {
			parent[x] = find(parent[x])
		}
		return parent[x]
	}
	total := 0
	for _, e := range edges {
		if a, b := find(e[1]), find(e[2]); a != b {
			parent[a] = b
			total += e[0]
		}
	}
	return total
}
