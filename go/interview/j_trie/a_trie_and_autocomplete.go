// Package j_trie: prefix trees for autocomplete and multi-word board search.
package j_trie

type node struct {
	next  [26]*node
	isEnd bool
}

type Trie struct{ root node }

func (t *Trie) Insert(word string) {
	n := &t.root
	for i := 0; i < len(word); i++ {
		c := word[i] - 'a'
		if n.next[c] == nil {
			n.next[c] = &node{}
		}
		n = n.next[c]
	}
	n.isEnd = true
}

func (t *Trie) walk(s string) *node {
	n := &t.root
	for i := 0; i < len(s) && n != nil; i++ {
		n = n.next[s[i]-'a']
	}
	return n
}

func (t *Trie) Search(word string) bool {
	n := t.walk(word)
	return n != nil && n.isEnd
}

func (t *Trie) StartsWith(prefix string) bool { return t.walk(prefix) != nil }

// Autocomplete returns up to limit words with the prefix, in lexicographic order.
func (t *Trie) Autocomplete(prefix string, limit int) []string {
	var out []string
	var collect func(*node, []byte)
	collect = func(n *node, path []byte) {
		if len(out) == limit {
			return
		}
		if n.isEnd {
			out = append(out, string(path))
		}
		for i, child := range n.next {
			if child != nil {
				collect(child, append(path, byte('a'+i)))
			}
		}
	}
	if start := t.walk(prefix); start != nil {
		collect(start, []byte(prefix))
	}
	return out
}

// SearchWithDot treats '.' as any letter.
func (t *Trie) SearchWithDot(pattern string) bool {
	var match func(*node, int) bool
	match = func(n *node, i int) bool {
		if n == nil {
			return false
		}
		if i == len(pattern) {
			return n.isEnd
		}
		if pattern[i] != '.' {
			return match(n.next[pattern[i]-'a'], i+1)
		}
		for _, child := range n.next {
			if match(child, i+1) {
				return true
			}
		}
		return false
	}
	return match(&t.root, 0)
}
