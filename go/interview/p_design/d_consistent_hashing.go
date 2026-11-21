package p_design

import (
	"crypto/md5"
	"encoding/binary"
	"sort"
	"strconv"
)

// HashRing places virtual nodes on a ring; a key belongs to the first node clockwise.
type HashRing struct {
	virtualNodes int
	positions    []uint32 // sorted
	owner        map[uint32]string
}

func NewHashRing(virtualNodes int) *HashRing {
	return &HashRing{virtualNodes: virtualNodes, owner: map[uint32]string{}}
}

func ringHash(s string) uint32 {
	sum := md5.Sum([]byte(s))
	return binary.LittleEndian.Uint32(sum[:4])
}

func (r *HashRing) AddNode(node string) {
	for i := 0; i < r.virtualNodes; i++ {
		h := ringHash(node + "#" + strconv.Itoa(i))
		r.owner[h] = node
		r.positions = append(r.positions, h)
	}
	sort.Slice(r.positions, func(i, j int) bool { return r.positions[i] < r.positions[j] })
}

// RemoveNode drops a node's virtual nodes; only its keys move (to the next node clockwise).
func (r *HashRing) RemoveNode(node string) {
	for i := 0; i < r.virtualNodes; i++ {
		delete(r.owner, ringHash(node+"#"+strconv.Itoa(i)))
	}
	kept := r.positions[:0]
	for _, h := range r.positions {
		if _, ok := r.owner[h]; ok {
			kept = append(kept, h)
		}
	}
	r.positions = kept
}

func (r *HashRing) NodeFor(key string) string {
	h := ringHash(key)
	i := sort.Search(len(r.positions), func(i int) bool { return r.positions[i] >= h })
	return r.owner[r.positions[i%len(r.positions)]] // wrap around
}
