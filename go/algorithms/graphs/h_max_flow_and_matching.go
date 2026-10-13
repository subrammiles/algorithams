package graphs

import "math"

// MaxFlow is Edmonds-Karp: BFS augmenting paths in the residual graph. O(V E^2).
func MaxFlow(capacity [][]int, source, sink int) int {
	n := len(capacity)
	residual := make([][]int, n)
	for i := range capacity {
		residual[i] = append([]int(nil), capacity[i]...)
	}
	flow := 0
	for {
		parent := make([]int, n)
		for i := range parent {
			parent[i] = -1
		}
		parent[source] = source
		queue := []int{source}
		for head := 0; head < len(queue) && parent[sink] == -1; head++ {
			u := queue[head]
			for v := 0; v < n; v++ {
				if parent[v] == -1 && residual[u][v] > 0 {
					parent[v] = u
					queue = append(queue, v)
				}
			}
		}
		if parent[sink] == -1 {
			return flow
		}
		bottleneck := math.MaxInt
		for v := sink; v != source; v = parent[v] {
			bottleneck = min(bottleneck, residual[parent[v]][v])
		}
		for v := sink; v != source; v = parent[v] {
			residual[parent[v]][v] -= bottleneck
			residual[v][parent[v]] += bottleneck // reverse edge: allows undoing
		}
		flow += bottleneck
	}
}

// MaxBipartiteMatching is Kuhn's algorithm. adj[left] lists the right vertices it can take.
func MaxBipartiteMatching(adj [][]int, rightCount int) int {
	matchOfRight := make([]int, rightCount)
	for i := range matchOfRight {
		matchOfRight[i] = -1
	}
	var tryAugment func(left int, seen []bool) bool
	tryAugment = func(left int, seen []bool) bool {
		for _, right := range adj[left] {
			if seen[right] {
				continue
			}
			seen[right] = true
			if matchOfRight[right] == -1 || tryAugment(matchOfRight[right], seen) {
				matchOfRight[right] = left
				return true
			}
		}
		return false
	}
	matches := 0
	for left := range adj {
		if tryAugment(left, make([]bool, rightCount)) {
			matches++
		}
	}
	return matches
}
