// Package graphs: graph algorithms beyond plain BFS/DFS.
package graphs

type Node struct {
	Val       int
	Neighbors []*Node
}

// CloneGraph registers the copy BEFORE recursing so cycles terminate.
func CloneGraph(n *Node) *Node {
	return clone(n, map[*Node]*Node{})
}

func clone(n *Node, copies map[*Node]*Node) *Node {
	if n == nil {
		return nil
	}
	if c, ok := copies[n]; ok {
		return c
	}
	c := &Node{Val: n.Val}
	copies[n] = c
	for _, nb := range n.Neighbors {
		c.Neighbors = append(c.Neighbors, clone(nb, copies))
	}
	return c
}

func CountComponents(n int, edges [][2]int) int {
	graph := make([][]int, n)
	for _, e := range edges {
		graph[e[0]] = append(graph[e[0]], e[1])
		graph[e[1]] = append(graph[e[1]], e[0])
	}
	seen := make([]bool, n)
	components := 0
	for s := 0; s < n; s++ {
		if seen[s] {
			continue
		}
		components++
		stack := []int{s}
		seen[s] = true
		for len(stack) > 0 {
			u := stack[len(stack)-1]
			stack = stack[:len(stack)-1]
			for _, v := range graph[u] {
				if !seen[v] {
					seen[v] = true
					stack = append(stack, v)
				}
			}
		}
	}
	return components
}

// ValidTree: exactly n-1 edges and connected.
func ValidTree(n int, edges [][2]int) bool {
	return len(edges) == n-1 && CountComponents(n, edges) == 1
}

func AllPathsSourceTarget(graph [][]int) [][]int {
	var paths [][]int
	var walk func(u int, path []int)
	walk = func(u int, path []int) {
		if u == len(graph)-1 {
			paths = append(paths, append([]int(nil), path...))
			return
		}
		for _, v := range graph[u] {
			walk(v, append(path, v))
		}
	}
	walk(0, []int{0})
	return paths
}
