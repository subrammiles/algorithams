package h_graphs

import (
	"container/heap"
	"math"
)

type distItem struct{ node, dist int }
type distHeap []distItem

func (h distHeap) Len() int           { return len(h) }
func (h distHeap) Less(i, j int) bool { return h[i].dist < h[j].dist }
func (h distHeap) Swap(i, j int)      { h[i], h[j] = h[j], h[i] }
func (h *distHeap) Push(x any)        { *h = append(*h, x.(distItem)) }
func (h *distHeap) Pop() any {
	old := *h
	x := old[len(old)-1]
	*h = old[:len(old)-1]
	return x
}

// NetworkDelayTime is Dijkstra with lazy deletion. times[i] = {from, to, weight}, nodes 1..n.
func NetworkDelayTime(times [][3]int, n, source int) int {
	graph := map[int][][2]int{}
	for _, t := range times {
		graph[t[0]] = append(graph[t[0]], [2]int{t[1], t[2]})
	}
	dist := map[int]int{}
	h := &distHeap{{source, 0}}
	for h.Len() > 0 {
		top := heap.Pop(h).(distItem)
		if _, done := dist[top.node]; done {
			continue // stale entry
		}
		dist[top.node] = top.dist
		for _, e := range graph[top.node] {
			if _, done := dist[e[0]]; !done {
				heap.Push(h, distItem{e[0], top.dist + e[1]})
			}
		}
	}
	if len(dist) != n {
		return -1
	}
	worst := 0
	for _, d := range dist {
		worst = max(worst, d)
	}
	return worst
}

// FindCheapestPrice is Bellman-Ford limited to k+1 rounds (copy the array each round).
func FindCheapestPrice(n int, flights [][3]int, src, dst, k int) int {
	cost := make([]int, n)
	for i := range cost {
		cost[i] = math.MaxInt
	}
	cost[src] = 0
	for round := 0; round <= k; round++ {
		next := append([]int(nil), cost...)
		for _, f := range flights {
			if cost[f[0]] != math.MaxInt && cost[f[0]]+f[2] < next[f[1]] {
				next[f[1]] = cost[f[0]] + f[2]
			}
		}
		cost = next
	}
	if cost[dst] == math.MaxInt {
		return -1
	}
	return cost[dst]
}
