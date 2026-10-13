// Package i_unionfind: disjoint set union for connectivity that only grows.
package i_unionfind

import "sort"

type DSU struct {
	parent, rank []int
	Components   int
}

func NewDSU(n int) *DSU {
	d := &DSU{parent: make([]int, n), rank: make([]int, n), Components: n}
	for i := range d.parent {
		d.parent[i] = i
	}
	return d
}

func (d *DSU) Find(x int) int {
	for d.parent[x] != x {
		d.parent[x] = d.parent[d.parent[x]]
		x = d.parent[x]
	}
	return x
}

func (d *DSU) Union(a, b int) bool {
	ra, rb := d.Find(a), d.Find(b)
	if ra == rb {
		return false
	}
	if d.rank[ra] < d.rank[rb] {
		ra, rb = rb, ra
	}
	d.parent[rb] = ra
	if d.rank[ra] == d.rank[rb] {
		d.rank[ra]++
	}
	d.Components--
	return true
}

// FindRedundantConnection returns the first edge that closes a cycle.
func FindRedundantConnection(edges [][2]int) [2]int {
	d := NewDSU(len(edges) + 1)
	for _, e := range edges {
		if !d.Union(e[0], e[1]) {
			return e
		}
	}
	return [2]int{}
}

// AccountsMerge unions every email of an account with its first email, then groups by root.
func AccountsMerge(accounts [][]string) [][]string {
	id, owner := map[string]int{}, map[string]string{}
	for _, acc := range accounts {
		for _, e := range acc[1:] {
			if _, ok := id[e]; !ok {
				id[e] = len(id)
			}
			owner[e] = acc[0]
		}
	}
	d := NewDSU(len(id))
	for _, acc := range accounts {
		for _, e := range acc[2:] {
			d.Union(id[acc[1]], id[e])
		}
	}
	groups := map[int][]string{}
	for e, i := range id {
		root := d.Find(i)
		groups[root] = append(groups[root], e)
	}
	var result [][]string
	for _, emails := range groups {
		sort.Strings(emails)
		result = append(result, append([]string{owner[emails[0]]}, emails...))
	}
	sort.Slice(result, func(i, j int) bool { return result[i][1] < result[j][1] })
	return result
}
