package h_graphs

// FindOrderKahn: prerequisites [a, b] mean b before a. Returns nil on a cycle. O(V + E).
func FindOrderKahn(n int, prerequisites [][2]int) []int {
	graph := make([][]int, n)
	indegree := make([]int, n)
	for _, p := range prerequisites {
		graph[p[1]] = append(graph[p[1]], p[0])
		indegree[p[0]]++
	}
	var order []int
	for i, d := range indegree {
		if d == 0 {
			order = append(order, i)
		}
	}
	for head := 0; head < len(order); head++ {
		for _, v := range graph[order[head]] {
			if indegree[v]--; indegree[v] == 0 {
				order = append(order, v)
			}
		}
	}
	if len(order) != n {
		return nil
	}
	return order
}

// HasCycleDFS uses white/grey/black colouring; a grey -> grey edge is a cycle.
func HasCycleDFS(n int, prerequisites [][2]int) bool {
	graph := make([][]int, n)
	for _, p := range prerequisites {
		graph[p[1]] = append(graph[p[1]], p[0])
	}
	color := make([]int, n)
	var dfs func(int) bool
	dfs = func(u int) bool {
		color[u] = 1
		for _, v := range graph[u] {
			if color[v] == 1 || (color[v] == 0 && dfs(v)) {
				return true
			}
		}
		color[u] = 2
		return false
	}
	for i := 0; i < n; i++ {
		if color[i] == 0 && dfs(i) {
			return true
		}
	}
	return false
}
