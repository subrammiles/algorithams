package greedy

import (
	"container/heap"
	"sort"
)

// HuffmanNode is a leaf (Left == nil) or an internal node.
type HuffmanNode struct {
	Symbol      byte
	Freq, order int
	Left, Right *HuffmanNode
}

type huffHeap []*HuffmanNode

func (h huffHeap) Len() int { return len(h) }
func (h huffHeap) Less(i, j int) bool {
	if h[i].Freq != h[j].Freq {
		return h[i].Freq < h[j].Freq
	}
	return h[i].order < h[j].order // deterministic tie-breaking
}
func (h huffHeap) Swap(i, j int) { h[i], h[j] = h[j], h[i] }
func (h *huffHeap) Push(x any)   { *h = append(*h, x.(*HuffmanNode)) }
func (h *huffHeap) Pop() any {
	old := *h
	x := old[len(old)-1]
	*h = old[:len(old)-1]
	return x
}

// BuildHuffmanTree repeatedly merges the two least frequent trees. O(n log n).
func BuildHuffmanTree(freq map[byte]int) *HuffmanNode {
	symbols := make([]byte, 0, len(freq))
	for s := range freq {
		symbols = append(symbols, s)
	}
	sort.Slice(symbols, func(i, j int) bool { return symbols[i] < symbols[j] })
	h := &huffHeap{}
	order := 0
	for _, s := range symbols {
		heap.Push(h, &HuffmanNode{Symbol: s, Freq: freq[s], order: order})
		order++
	}
	for h.Len() > 1 {
		a, b := heap.Pop(h).(*HuffmanNode), heap.Pop(h).(*HuffmanNode)
		heap.Push(h, &HuffmanNode{Freq: a.Freq + b.Freq, order: order, Left: a, Right: b})
		order++
	}
	return heap.Pop(h).(*HuffmanNode)
}

func HuffmanCodes(root *HuffmanNode) map[byte]string {
	codes := map[byte]string{}
	var assign func(n *HuffmanNode, prefix string)
	assign = func(n *HuffmanNode, prefix string) {
		if n.Left == nil {
			if prefix == "" {
				prefix = "0"
			}
			codes[n.Symbol] = prefix
			return
		}
		assign(n.Left, prefix+"0")
		assign(n.Right, prefix+"1")
	}
	assign(root, "")
	return codes
}

func HuffmanDecode(bits string, root *HuffmanNode) string {
	var out []byte
	n := root
	for i := 0; i < len(bits); i++ {
		if bits[i] == '0' {
			n = n.Left
		} else {
			n = n.Right
		}
		if n.Left == nil {
			out = append(out, n.Symbol)
			n = root
		}
	}
	return string(out)
}
