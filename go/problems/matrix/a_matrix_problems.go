// Package matrix: in-place matrix tricks.
package matrix

// SetZeroes uses the first row/column as markers: O(1) extra space.
func SetZeroes(m [][]int) {
	rows, cols := len(m), len(m[0])
	firstRowZero, firstColZero := false, false
	for c := 0; c < cols; c++ {
		firstRowZero = firstRowZero || m[0][c] == 0
	}
	for r := 0; r < rows; r++ {
		firstColZero = firstColZero || m[r][0] == 0
	}
	for r := 1; r < rows; r++ {
		for c := 1; c < cols; c++ {
			if m[r][c] == 0 {
				m[r][0], m[0][c] = 0, 0
			}
		}
	}
	for r := 1; r < rows; r++ {
		for c := 1; c < cols; c++ {
			if m[r][0] == 0 || m[0][c] == 0 {
				m[r][c] = 0
			}
		}
	}
	if firstRowZero {
		for c := range m[0] {
			m[0][c] = 0
		}
	}
	if firstColZero {
		for r := range m {
			m[r][0] = 0
		}
	}
}

// SearchMatrixII walks the staircase from the top-right corner: O(rows + cols).
func SearchMatrixII(m [][]int, target int) bool {
	r, c := 0, len(m[0])-1
	for r < len(m) && c >= 0 {
		switch {
		case m[r][c] == target:
			return true
		case m[r][c] > target:
			c--
		default:
			r++
		}
	}
	return false
}

// GameOfLife stores the next state in bit 1, then shifts.
func GameOfLife(board [][]int) {
	rows, cols := len(board), len(board[0])
	for r := 0; r < rows; r++ {
		for c := 0; c < cols; c++ {
			live := 0
			for dr := -1; dr <= 1; dr++ {
				for dc := -1; dc <= 1; dc++ {
					nr, nc := r+dr, c+dc
					if (dr != 0 || dc != 0) && nr >= 0 && nc >= 0 && nr < rows && nc < cols {
						live += board[nr][nc] & 1
					}
				}
			}
			alive := board[r][c]&1 == 1
			if (alive && (live == 2 || live == 3)) || (!alive && live == 3) {
				board[r][c] |= 2
			}
		}
	}
	for _, row := range board {
		for c := range row {
			row[c] >>= 1
		}
	}
}
