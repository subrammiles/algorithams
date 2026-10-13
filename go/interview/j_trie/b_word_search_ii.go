package j_trie

import "sort"

type wordNode struct {
	next [26]*wordNode
	word string
}

// FindWords walks a trie of the words in lock-step with a DFS from every cell.
func FindWords(board [][]byte, words []string) []string {
	root := &wordNode{}
	for _, w := range words {
		n := root
		for i := 0; i < len(w); i++ {
			c := w[i] - 'a'
			if n.next[c] == nil {
				n.next[c] = &wordNode{}
			}
			n = n.next[c]
		}
		n.word = w
	}
	var found []string
	var dfs func(r, c int, n *wordNode)
	dfs = func(r, c int, parent *wordNode) {
		if r < 0 || c < 0 || r >= len(board) || c >= len(board[0]) || board[r][c] == '#' {
			return
		}
		ch := board[r][c]
		n := parent.next[ch-'a']
		if n == nil {
			return
		}
		if n.word != "" {
			found = append(found, n.word)
			n.word = "" // avoid duplicates
		}
		board[r][c] = '#'
		dfs(r+1, c, n)
		dfs(r-1, c, n)
		dfs(r, c+1, n)
		dfs(r, c-1, n)
		board[r][c] = ch
	}
	for r := range board {
		for c := range board[r] {
			dfs(r, c, root)
		}
	}
	sort.Strings(found)
	return found
}
