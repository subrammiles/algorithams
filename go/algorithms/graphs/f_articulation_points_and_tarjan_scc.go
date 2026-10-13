package graphs

import "sort"

// ArticulationPoints: root with >= 2 DFS children, or a child v with low[v] >= disc[u].
func ArticulationPoints(n int, edges [][2]int) []int {
	graph := make([][]int, n)
	for _, e := range edges {
		graph[e[0]] = append(graph[e[0]], e[1])
		graph[e[1]] = append(graph[e[1]], e[0])
	}
	disc, low, isCut := make([]int, n), make([]int, n), make([]bool, n)
	for i := range disc {
		disc[i] = -1
	}
	timer := 0
	var dfs func(u, parent int)
	dfs = func(u, parent int) {
		disc[u], low[u] = timer, timer
		timer++
		children := 0
		for _, v := range graph[u] {
			if v == parent {
				continue
			}
			if disc[v] == -1 {
				children++
				dfs(v, u)
				low[u] = min(low[u], low[v])
				if parent != -1 && low[v] >= disc[u] {
					isCut[u] = true
				}
			} else {
				low[u] = min(low[u], disc[v])
			}
		}
		if parent == -1 && children >= 2 {
			isCut[u] = true
		}
	}
	for i := 0; i < n; i++ {
		if disc[i] == -1 {
			dfs(i, -1)
		}
	}
	out := []int{}
	for i, cut := range isCut {
		if cut {
			out = append(out, i)
		}
	}
	return out
}

// TarjanSCC finds strongly connected components in one DFS.
func TarjanSCC(n int, edges [][2]int) [][]int {
	graph := make([][]int, n)
	for _, e := range edges {
		graph[e[0]] = append(graph[e[0]], e[1])
	}
	disc, low, onStack := make([]int, n), make([]int, n), make([]bool, n)
	for i := range disc {
		disc[i] = -1
	}
	var stack []int
	var components [][]int
	timer := 0
	var dfs func(u int)
	dfs = func(u int) {
		disc[u], low[u] = timer, timer
		timer++
		stack = append(stack, u)
		onStack[u] = true
		for _, v := range graph[u] {
			if disc[v] == -1 {
				dfs(v)
				low[u] = min(low[u], low[v])
			} else if onStack[v] {
				low[u] = min(low[u], disc[v])
			}
		}
		if low[u] == disc[u] { // u roots a component
			var comp []int
			for {
				v := stack[len(stack)-1]
				stack = stack[:len(stack)-1]
				onStack[v] = false
				comp = append(comp, v)
				if v == u {
					break
				}
			}
			sort.Ints(comp)
			components = append(components, comp)
		}
	}
	for i := 0; i < n; i++ {
		if disc[i] == -1 {
			dfs(i)
		}
	}
	sort.Slice(components, func(i, j int) bool { return components[i][0] < components[j][0] })
	return components
}
