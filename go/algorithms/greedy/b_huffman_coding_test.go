package greedy

import (
	"strings"
	"testing"
)

func TestHuffman(t *testing.T) {
	freq := map[byte]int{'a': 5, 'b': 9, 'c': 12, 'd': 13, 'e': 16, 'f': 45}
	codes := HuffmanCodes(BuildHuffmanTree(freq))
	cost := 0
	for s, f := range freq {
		cost += f * len(codes[s])
	}
	if cost != 224 || len(codes['f']) != 1 {
		t.Fatalf("cost %d, code(f) %q", cost, codes['f'])
	}
	for _, x := range codes {
		for _, y := range codes {
			if x != y && strings.HasPrefix(y, x) {
				t.Fatal("not prefix free")
			}
		}
	}
	text := "abracadabra"
	textFreq := map[byte]int{}
	for i := 0; i < len(text); i++ {
		textFreq[text[i]]++
	}
	root := BuildHuffmanTree(textFreq)
	c := HuffmanCodes(root)
	var sb strings.Builder
	for i := 0; i < len(text); i++ {
		sb.WriteString(c[text[i]])
	}
	if HuffmanDecode(sb.String(), root) != text || sb.Len() >= 8*len(text) {
		t.Fatal("round trip")
	}
}
