package graphs

import (
	"container/heap"
	"container/list"
	"math"
)

var dirs = [4][2]int{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}

// MinimumObstacles is 0-1 BFS: 0-weight moves go to the FRONT of the deque.
func MinimumObstacles(grid [][]int) int {
	rows, cols := len(grid), len(grid[0])
	dist := make([][]int, rows)
	for r := range dist {
		dist[r] = make([]int, cols)
		for c := range dist[r] {
			dist[r][c] = math.MaxInt
		}
	}
	dist[0][0] = 0
	dq := list.New()
	dq.PushBack([2]int{0, 0})
	for dq.Len() > 0 {
		cell := dq.Remove(dq.Front()).([2]int)
		for _, d := range dirs {
			r, c := cell[0]+d[0], cell[1]+d[1]
			if r < 0 || c < 0 || r >= rows || c >= cols {
				continue
			}
			if nd := dist[cell[0]][cell[1]] + grid[r][c]; nd < dist[r][c] {
				dist[r][c] = nd
				if grid[r][c] == 0 {
					dq.PushFront([2]int{r, c})
				} else {
					dq.PushBack([2]int{r, c})
				}
			}
		}
	}
	return dist[rows-1][cols-1]
}

// item / pq: a generic priority queue over float64 priorities (min-heap).
type item struct {
	priority float64
	a, b     int
}
type pq []item

func (p pq) Len() int           { return len(p) }
func (p pq) Less(i, j int) bool { return p[i].priority < p[j].priority }
func (p pq) Swap(i, j int)      { p[i], p[j] = p[j], p[i] }
func (p *pq) Push(x any)        { *p = append(*p, x.(item)) }
func (p *pq) Pop() any {
	old := *p
	x := old[len(old)-1]
	*p = old[:len(old)-1]
	return x
}

// MaxProbability is Dijkstra maximising the product (priority = -probability).
func MaxProbability(n int, edges [][2]int, prob []float64, start, end int) float64 {
	type arc struct {
		to int
		p  float64
	}
	graph := make([][]arc, n)
	for i, e := range edges {
		graph[e[0]] = append(graph[e[0]], arc{e[1], prob[i]})
		graph[e[1]] = append(graph[e[1]], arc{e[0], prob[i]})
	}
	best := make([]float64, n)
	best[start] = 1
	h := &pq{{-1, start, 0}}
	for h.Len() > 0 {
		top := heap.Pop(h).(item)
		p, u := -top.priority, top.a
		if u == end {
			return p
		}
		if p < best[u] {
			continue
		}
		for _, e := range graph[u] {
			if p*e.p > best[e.to] {
				best[e.to] = p * e.p
				heap.Push(h, item{-best[e.to], e.to, 0})
			}
		}
	}
	return 0
}

// SwimInWater: minimax path - Dijkstra on the maximum height seen so far.
func SwimInWater(grid [][]int) int {
	n := len(grid)
	seen := make([][]bool, n)
	for i := range seen {
		seen[i] = make([]bool, n)
	}
	seen[0][0] = true
	h := &pq{{float64(grid[0][0]), 0, 0}}
	for h.Len() > 0 {
		top := heap.Pop(h).(item)
		if top.a == n-1 && top.b == n-1 {
			return int(top.priority)
		}
		for _, d := range dirs {
			r, c := top.a+d[0], top.b+d[1]
			if r >= 0 && c >= 0 && r < n && c < n && !seen[r][c] {
				seen[r][c] = true
				heap.Push(h, item{math.Max(top.priority, float64(grid[r][c])), r, c})
			}
		}
	}
	return -1
}

// AStar: fewest steps top-left -> bottom-right, '#' = wall, Manhattan heuristic.
func AStar(grid []string) int {
	rows, cols := len(grid), len(grid[0])
	if grid[0][0] == '#' {
		return -1
	}
	h := func(r, c int) int { return rows - 1 - r + cols - 1 - c }
	g := make([][]int, rows)
	for r := range g {
		g[r] = make([]int, cols)
		for c := range g[r] {
			g[r][c] = math.MaxInt
		}
	}
	g[0][0] = 0
	open := &pq{{float64(h(0, 0)), 0, 0}}
	for open.Len() > 0 {
		top := heap.Pop(open).(item)
		r, c := top.a, top.b
		if r == rows-1 && c == cols-1 {
			return g[r][c]
		}
		if int(top.priority) > g[r][c]+h(r, c) {
			continue // stale
		}
		for _, d := range dirs {
			nr, nc := r+d[0], c+d[1]
			if nr < 0 || nc < 0 || nr >= rows || nc >= cols || grid[nr][nc] == '#' {
				continue
			}
			if g[r][c]+1 < g[nr][nc] {
				g[nr][nc] = g[r][c] + 1
				heap.Push(open, item{float64(g[nr][nc] + h(nr, nc)), nr, nc})
			}
		}
	}
	return -1
}
