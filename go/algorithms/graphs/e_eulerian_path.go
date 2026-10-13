package graphs

import "sort"

// FindItinerary uses Hierholzer's algorithm: every ticket (edge) used once, lexically smallest.
func FindItinerary(tickets [][2]string) []string {
	graph := map[string][]string{}
	for _, t := range tickets {
		graph[t[0]] = append(graph[t[0]], t[1])
	}
	for src := range graph {
		sort.Sort(sort.Reverse(sort.StringSlice(graph[src]))) // pop from the end = smallest first
	}
	var route []string
	stack := []string{"JFK"}
	for len(stack) > 0 {
		top := stack[len(stack)-1]
		if next := graph[top]; len(next) > 0 {
			stack = append(stack, next[len(next)-1])
			graph[top] = next[:len(next)-1]
		} else {
			route = append(route, top)
			stack = stack[:len(stack)-1]
		}
	}
	for i, j := 0, len(route)-1; i < j; i, j = i+1, j-1 {
		route[i], route[j] = route[j], route[i]
	}
	return route
}
