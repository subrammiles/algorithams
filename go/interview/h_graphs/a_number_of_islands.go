// Package h_graphs: grid BFS/DFS, topological sort, shortest paths, bridges and SCCs.
package h_graphs

var dirs = [4][2]int{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}

// NumIslands floods each unvisited island with an explicit stack. O(rows * cols).
func NumIslands(grid []string) int {
	rows, cols := len(grid), len(grid[0])
	seen := make([][]bool, rows)
	for r := range seen {
		seen[r] = make([]bool, cols)
	}
	count := 0
	for r := 0; r < rows; r++ {
		for c := 0; c < cols; c++ {
			if grid[r][c] != '1' || seen[r][c] {
				continue
			}
			count++
			stack := [][2]int{{r, c}}
			seen[r][c] = true
			for len(stack) > 0 {
				cell := stack[len(stack)-1]
				stack = stack[:len(stack)-1]
				for _, d := range dirs {
					nr, nc := cell[0]+d[0], cell[1]+d[1]
					if nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == '1' && !seen[nr][nc] {
						seen[nr][nc] = true
						stack = append(stack, [2]int{nr, nc})
					}
				}
			}
		}
	}
	return count
}

// OrangesRotting is a multi-source BFS: every rotten orange starts at minute 0.
func OrangesRotting(grid [][]int) int {
	rows, cols := len(grid), len(grid[0])
	g := make([][]int, rows)
	var queue [][2]int
	fresh := 0
	for r := range grid {
		g[r] = append([]int(nil), grid[r]...)
		for c, v := range g[r] {
			if v == 2 {
				queue = append(queue, [2]int{r, c})
			} else if v == 1 {
				fresh++
			}
		}
	}
	minutes := 0
	for len(queue) > 0 && fresh > 0 {
		minutes++
		var next [][2]int
		for _, cell := range queue {
			for _, d := range dirs {
				nr, nc := cell[0]+d[0], cell[1]+d[1]
				if nr >= 0 && nr < rows && nc >= 0 && nc < cols && g[nr][nc] == 1 {
					g[nr][nc] = 2
					fresh--
					next = append(next, [2]int{nr, nc})
				}
			}
		}
		queue = next
	}
	if fresh > 0 {
		return -1
	}
	return minutes
}
