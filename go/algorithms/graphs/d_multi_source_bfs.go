package graphs

const Inf = 2147483647

// UpdateMatrix: distance of every cell to the nearest 0 (all zeros start the BFS).
func UpdateMatrix(mat [][]int) [][]int {
	rows, cols := len(mat), len(mat[0])
	dist := make([][]int, rows)
	var queue [][2]int
	for r := range mat {
		dist[r] = make([]int, cols)
		for c, v := range mat[r] {
			if v == 0 {
				queue = append(queue, [2]int{r, c})
			} else {
				dist[r][c] = -1
			}
		}
	}
	for head := 0; head < len(queue); head++ {
		cell := queue[head]
		for _, d := range dirs {
			r, c := cell[0]+d[0], cell[1]+d[1]
			if r >= 0 && c >= 0 && r < rows && c < cols && dist[r][c] == -1 {
				dist[r][c] = dist[cell[0]][cell[1]] + 1
				queue = append(queue, [2]int{r, c})
			}
		}
	}
	return dist
}

// WallsAndGates: -1 wall, 0 gate, Inf empty room; filled in place.
func WallsAndGates(rooms [][]int) {
	var queue [][2]int
	for r := range rooms {
		for c, v := range rooms[r] {
			if v == 0 {
				queue = append(queue, [2]int{r, c})
			}
		}
	}
	for head := 0; head < len(queue); head++ {
		cell := queue[head]
		for _, d := range dirs {
			r, c := cell[0]+d[0], cell[1]+d[1]
			if r >= 0 && c >= 0 && r < len(rooms) && c < len(rooms[0]) && rooms[r][c] == Inf {
				rooms[r][c] = rooms[cell[0]][cell[1]] + 1
				queue = append(queue, [2]int{r, c})
			}
		}
	}
}

// ShortestBridge marks the first island, then BFS from all its cells to the second.
func ShortestBridge(grid [][]int) int {
	n := len(grid)
	g := make([][]int, n)
	for i := range grid {
		g[i] = append([]int(nil), grid[i]...)
	}
	var start [2]int
found:
	for r := 0; r < n; r++ {
		for c := 0; c < n; c++ {
			if g[r][c] == 1 {
				start = [2]int{r, c}
				break found
			}
		}
	}
	var queue [][2]int
	stack := [][2]int{start}
	g[start[0]][start[1]] = 2
	for len(stack) > 0 {
		cell := stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		queue = append(queue, cell)
		for _, d := range dirs {
			r, c := cell[0]+d[0], cell[1]+d[1]
			if r >= 0 && c >= 0 && r < n && c < n && g[r][c] == 1 {
				g[r][c] = 2
				stack = append(stack, [2]int{r, c})
			}
		}
	}
	for steps := 0; len(queue) > 0; steps++ {
		var next [][2]int
		for _, cell := range queue {
			for _, d := range dirs {
				r, c := cell[0]+d[0], cell[1]+d[1]
				if r < 0 || c < 0 || r >= n || c >= n || g[r][c] == 2 {
					continue
				}
				if g[r][c] == 1 {
					return steps
				}
				g[r][c] = 2
				next = append(next, [2]int{r, c})
			}
		}
		queue = next
	}
	return -1
}
