package h_graphs

// CriticalConnections finds bridges with Tarjan's low-link values. O(V + E).
func CriticalConnections(n int, edges [][2]int) [][2]int {
	graph := make([][]int, n)
	for _, e := range edges {
		graph[e[0]] = append(graph[e[0]], e[1])
		graph[e[1]] = append(graph[e[1]], e[0])
	}
	disc, low := make([]int, n), make([]int, n)
	for i := range disc {
		disc[i] = -1
	}
	var bridges [][2]int
	timer := 0
	var dfs func(u, parent int)
	dfs = func(u, parent int) {
		disc[u], low[u] = timer, timer
		timer++
		for _, v := range graph[u] {
			if v == parent {
				continue
			}
			if disc[v] == -1 {
				dfs(v, u)
				low[u] = min(low[u], low[v])
				if low[v] > disc[u] {
					bridges = append(bridges, [2]int{min(u, v), max(u, v)})
				}
			} else {
				low[u] = min(low[u], disc[v]) // back edge
			}
		}
	}
	for i := 0; i < n; i++ {
		if disc[i] == -1 {
			dfs(i, -1)
		}
	}
	return bridges
}

// CountSCC uses Kosaraju: finish order, reverse edges, DFS in decreasing finish order.
func CountSCC(n int, edges [][2]int) int {
	graph, rev := make([][]int, n), make([][]int, n)
	for _, e := range edges {
		graph[e[0]] = append(graph[e[0]], e[1])
		rev[e[1]] = append(rev[e[1]], e[0])
	}
	seen := make([]bool, n)
	var order []int
	var fill, mark func(int)
	fill = func(u int) {
		seen[u] = true
		for _, v := range graph[u] {
			if !seen[v] {
				fill(v)
			}
		}
		order = append(order, u)
	}
	mark = func(u int) {
		seen[u] = true
		for _, v := range rev[u] {
			if !seen[v] {
				mark(v)
			}
		}
	}
	for i := 0; i < n; i++ {
		if !seen[i] {
			fill(i)
		}
	}
	seen = make([]bool, n)
	components := 0
	for i := len(order) - 1; i >= 0; i-- {
		if !seen[order[i]] {
			components++
			mark(order[i])
		}
	}
	return components
}
