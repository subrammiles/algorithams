// Package backtracking: choose -> explore -> un-choose.
package backtracking

import "strings"

func SolveNQueens(n int) [][]string {
	var out [][]string
	cols, diag, anti := make([]bool, n), make([]bool, 2*n-1), make([]bool, 2*n-1)
	queenCol := make([]int, n)
	var place func(r int)
	place = func(r int) {
		if r == n {
			board := make([]string, n)
			for i, c := range queenCol {
				board[i] = strings.Repeat(".", c) + "Q" + strings.Repeat(".", n-c-1)
			}
			out = append(out, board)
			return
		}
		for c := 0; c < n; c++ {
			d, a := r-c+n-1, r+c
			if cols[c] || diag[d] || anti[a] {
				continue
			}
			queenCol[r] = c
			cols[c], diag[d], anti[a] = true, true, true
			place(r + 1)
			cols[c], diag[d], anti[a] = false, false, false
		}
	}
	place(0)
	return out
}

// TotalNQueens counts solutions with bitmasks: free = ^(cols | left | right) & full.
func TotalNQueens(n int) int {
	full := 1<<n - 1
	var count func(cols, left, right int) int
	count = func(cols, left, right int) int {
		if cols == full {
			return 1
		}
		total := 0
		for free := ^(cols | left | right) & full; free != 0; free &= free - 1 {
			bit := free & -free
			total += count(cols|bit, (left|bit)<<1&full, (right|bit)>>1)
		}
		return total
	}
	return count(0, 0, 0)
}
