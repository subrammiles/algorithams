package backtracking

import "sort"

// Classic backtracking (full explanations in D_ClassicBacktracking.java).
// Skeleton: if complete -> record; for each choice: if valid -> apply, recurse, UNDO.
//
//	RatInMaze         all paths, moves D L R U (alphabetical -> sorted output)
//	MColoring         first colour unused by coloured neighbours; O(m^V)
//	HamiltonianCycle  fix start 0, extend with unvisited neighbours, close the cycle; O(V!)
//	KnightsTour       Warnsdorff: try the square with the fewest onward moves first

var ratMoves = []struct {
	dr, dc int
	ch     byte
}{{1, 0, 'D'}, {0, -1, 'L'}, {0, 1, 'R'}, {-1, 0, 'U'}}

var knightMoves = [8][2]int{{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}}

func RatInMaze(maze [][]int) []string {
	n := len(maze)
	out := []string{}
	if n == 0 || maze[0][0] == 0 || maze[n-1][n-1] == 0 {
		return out
	}
	seen := make([][]bool, n)
	for i := range seen {
		seen[i] = make([]bool, n)
	}
	seen[0][0] = true
	var path []byte
	var walk func(r, c int)
	walk = func(r, c int) {
		if r == n-1 && c == n-1 {
			out = append(out, string(path))
			return
		}
		for _, m := range ratMoves {
			nr, nc := r+m.dr, c+m.dc
			if nr < 0 || nc < 0 || nr >= n || nc >= n || maze[nr][nc] == 0 || seen[nr][nc] {
				continue
			}
			seen[nr][nc] = true
			path = append(path, m.ch)
			walk(nr, nc)
			path = path[:len(path)-1] // undo
			seen[nr][nc] = false
		}
	}
	walk(0, 0)
	return out
}

// MColoring returns colours 1..m per vertex, or nil if m colours are not enough.
func MColoring(n int, edges [][2]int, m int) []int {
	adj := make([][]int, n)
	for _, e := range edges {
		adj[e[0]] = append(adj[e[0]], e[1])
		adj[e[1]] = append(adj[e[1]], e[0])
	}
	color := make([]int, n)
	var colorFrom func(v int) bool
	colorFrom = func(v int) bool {
		if v == n {
			return true
		}
		for c := 1; c <= m; c++ {
			ok := true
			for _, u := range adj[v] {
				if color[u] == c {
					ok = false
					break
				}
			}
			if !ok {
				continue
			}
			color[v] = c
			if colorFrom(v + 1) {
				return true
			}
			color[v] = 0
		}
		return false
	}
	if colorFrom(0) {
		return color
	}
	return nil
}

// HamiltonianCycle returns a cycle starting and ending at 0, or nil if none exists.
func HamiltonianCycle(n int, edges [][2]int) []int {
	adj := make([][]bool, n)
	for i := range adj {
		adj[i] = make([]bool, n)
	}
	for _, e := range edges {
		adj[e[0]][e[1]], adj[e[1]][e[0]] = true, true
	}
	path := []int{0}
	used := make([]bool, n)
	used[0] = true
	var extend func() bool
	extend = func() bool {
		last := path[len(path)-1]
		if len(path) == n {
			return adj[last][0]
		}
		for v := 1; v < n; v++ {
			if used[v] || !adj[last][v] {
				continue
			}
			used[v] = true
			path = append(path, v)
			if extend() {
				return true
			}
			path = path[:len(path)-1]
			used[v] = false
		}
		return false
	}
	if !extend() {
		return nil
	}
	return append(path, 0)
}

// KnightsTour returns board[r][c] = move number starting at (0,0), or nil if there is no tour.
func KnightsTour(n int) [][]int {
	board := make([][]int, n)
	for i := range board {
		board[i] = make([]int, n)
		for j := range board[i] {
			board[i][j] = -1
		}
	}
	free := func(r, c int) bool { return r >= 0 && c >= 0 && r < n && c < n && board[r][c] == -1 }
	onward := func(r, c int) int {
		count := 0
		for _, k := range knightMoves {
			if free(r+k[0], c+k[1]) {
				count++
			}
		}
		return count
	}
	var tour func(r, c, step int) bool
	tour = func(r, c, step int) bool {
		if step == n*n {
			return true
		}
		var next [][3]int
		for _, k := range knightMoves {
			if nr, nc := r+k[0], c+k[1]; free(nr, nc) {
				next = append(next, [3]int{nr, nc, onward(nr, nc)})
			}
		}
		sort.SliceStable(next, func(i, j int) bool { return next[i][2] < next[j][2] }) // Warnsdorff
		for _, sq := range next {
			board[sq[0]][sq[1]] = step
			if tour(sq[0], sq[1], step+1) {
				return true
			}
			board[sq[0]][sq[1]] = -1
		}
		return false
	}
	board[0][0] = 0
	if tour(0, 0, 1) {
		return board
	}
	return nil
}

func IsValidTour(board [][]int) bool {
	n := len(board)
	where := make([][2]int, n*n)
	seen := make([]bool, n*n)
	for r := range board {
		for c, s := range board[r] {
			if s < 0 || s >= n*n || seen[s] {
				return false
			}
			seen[s], where[s] = true, [2]int{r, c}
		}
	}
	abs := func(x int) int {
		if x < 0 {
			return -x
		}
		return x
	}
	for s := 1; s < n*n; s++ {
		if abs(where[s][0]-where[s-1][0])*abs(where[s][1]-where[s-1][1]) != 2 {
			return false
		}
	}
	return true
}
