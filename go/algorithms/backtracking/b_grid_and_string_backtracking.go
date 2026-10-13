package backtracking

import (
	"strconv"
	"strings"
)

// Exist: word search with in-place visited marking.
func Exist(board [][]byte, word string) bool {
	var search func(i, r, c int) bool
	search = func(i, r, c int) bool {
		if i == len(word) {
			return true
		}
		if r < 0 || c < 0 || r >= len(board) || c >= len(board[0]) || board[r][c] != word[i] {
			return false
		}
		board[r][c] = '#'
		found := search(i+1, r+1, c) || search(i+1, r-1, c) || search(i+1, r, c+1) || search(i+1, r, c-1)
		board[r][c] = word[i]
		return found
	}
	for r := range board {
		for c := range board[r] {
			if search(0, r, c) {
				return true
			}
		}
	}
	return false
}

func isPalindrome(s string) bool {
	for i, j := 0, len(s)-1; i < j; i, j = i+1, j-1 {
		if s[i] != s[j] {
			return false
		}
	}
	return true
}

func Partition(s string) [][]string {
	var out [][]string
	var cur []string
	var walk func(start int)
	walk = func(start int) {
		if start == len(s) {
			out = append(out, append([]string(nil), cur...))
			return
		}
		for end := start + 1; end <= len(s); end++ {
			if isPalindrome(s[start:end]) {
				cur = append(cur, s[start:end])
				walk(end)
				cur = cur[:len(cur)-1]
			}
		}
	}
	walk(0)
	return out
}

func RestoreIPAddresses(s string) []string {
	out := []string{}
	var parts []string
	var walk func(start int)
	walk = func(start int) {
		remaining, partsLeft := len(s)-start, 4-len(parts)
		if remaining < partsLeft || remaining > 3*partsLeft {
			return // prune
		}
		if partsLeft == 0 {
			out = append(out, strings.Join(parts, "."))
			return
		}
		for l := 1; l <= 3 && start+l <= len(s); l++ {
			part := s[start : start+l]
			if v, _ := strconv.Atoi(part); (l > 1 && part[0] == '0') || v > 255 {
				continue
			}
			parts = append(parts, part)
			walk(start + l)
			parts = parts[:len(parts)-1]
		}
	}
	walk(0)
	return out
}
