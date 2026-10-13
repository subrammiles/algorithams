package l_backtracking

import "math/bits"

// SolveSudoku: bitmasks for rows/cols/boxes + MRV (fill the most constrained cell first).
func SolveSudoku(board [][]byte) bool {
	var rows, cols, boxes [9]uint16
	box := func(r, c int) int { return (r/3)*3 + c/3 }
	toggle := func(r, c int, d uint) {
		rows[r] ^= 1 << d
		cols[c] ^= 1 << d
		boxes[box(r, c)] ^= 1 << d
	}
	candidates := func(r, c int) uint16 { return ^(rows[r] | cols[c] | boxes[box(r, c)]) & 0x1FF }
	for r := 0; r < 9; r++ {
		for c := 0; c < 9; c++ {
			if board[r][c] != '.' {
				toggle(r, c, uint(board[r][c]-'1'))
			}
		}
	}
	var backtrack func() bool
	backtrack = func() bool {
		bestR, bestC, bestCount := -1, -1, 10
		for r := 0; r < 9; r++ {
			for c := 0; c < 9; c++ {
				if board[r][c] == '.' {
					if n := bits.OnesCount16(candidates(r, c)); n < bestCount {
						bestR, bestC, bestCount = r, c, n
					}
				}
			}
		}
		if bestR == -1 {
			return true
		}
		for mask := candidates(bestR, bestC); mask != 0; mask &= mask - 1 {
			d := uint(bits.TrailingZeros16(mask))
			toggle(bestR, bestC, d)
			board[bestR][bestC] = byte('1' + d)
			if backtrack() {
				return true
			}
			toggle(bestR, bestC, d)
			board[bestR][bestC] = '.'
		}
		return false
	}
	return backtrack()
}
