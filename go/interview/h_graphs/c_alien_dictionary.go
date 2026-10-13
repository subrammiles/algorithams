package h_graphs

import (
	"sort"
	"strings"
)

// AlienOrder derives a letter order (edges from first differing letters + Kahn), or "".
func AlienOrder(words []string) string {
	graph := map[byte]map[byte]bool{}
	indegree := map[byte]int{}
	for _, w := range words {
		for i := 0; i < len(w); i++ {
			if graph[w[i]] == nil {
				graph[w[i]] = map[byte]bool{}
				indegree[w[i]] = 0
			}
		}
	}
	for i := 0; i+1 < len(words); i++ {
		a, b := words[i], words[i+1]
		if len(a) > len(b) && strings.HasPrefix(a, b) {
			return "" // prefix rule violated
		}
		for j := 0; j < min(len(a), len(b)); j++ {
			if a[j] != b[j] {
				if !graph[a[j]][b[j]] {
					graph[a[j]][b[j]] = true
					indegree[b[j]]++
				}
				break
			}
		}
	}
	sortedKeys := func(m map[byte]bool) []byte {
		var keys []byte
		for k := range m {
			keys = append(keys, k)
		}
		sort.Slice(keys, func(i, j int) bool { return keys[i] < keys[j] })
		return keys
	}
	var order []byte
	all := map[byte]bool{}
	for c := range graph {
		all[c] = true
	}
	for _, c := range sortedKeys(all) {
		if indegree[c] == 0 {
			order = append(order, c)
		}
	}
	for head := 0; head < len(order); head++ {
		for _, next := range sortedKeys(graph[order[head]]) {
			if indegree[next]--; indegree[next] == 0 {
				order = append(order, next)
			}
		}
	}
	if len(order) != len(graph) {
		return ""
	}
	return string(order)
}
