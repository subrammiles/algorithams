package h_graphs

// LadderLength runs bidirectional BFS, always expanding the smaller frontier.
func LadderLength(begin, end string, wordList []string) int {
	words := map[string]bool{}
	for _, w := range wordList {
		words[w] = true
	}
	if !words[end] {
		return 0
	}
	front, back, visited := map[string]bool{begin: true}, map[string]bool{end: true}, map[string]bool{}
	for steps := 1; len(front) > 0 && len(back) > 0; steps++ {
		if len(front) > len(back) {
			front, back = back, front
		}
		next := map[string]bool{}
		for w := range front {
			b := []byte(w)
			for i := range b {
				original := b[i]
				for c := byte('a'); c <= 'z'; c++ {
					if c == original {
						continue
					}
					b[i] = c
					cand := string(b)
					if back[cand] {
						return steps + 1
					}
					if words[cand] && !visited[cand] {
						visited[cand] = true
						next[cand] = true
					}
				}
				b[i] = original
			}
		}
		front = next
	}
	return 0
}
