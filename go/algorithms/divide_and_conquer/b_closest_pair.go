package divide_and_conquer

import (
	"math"
	"sort"
)

// ClosestPair: divide and conquer, O(n log n) (full explanation in B_ClosestPair.java).
// Sort by x; d = min of both halves; check only the strip |x - midX| < d, kept sorted by y by
// merging on the way up; each strip point is compared with at most 7 following points.
func ClosestPair(points [][2]float64) float64 {
	p := append([][2]float64(nil), points...)
	sort.Slice(p, func(i, j int) bool { return p[i][0] < p[j][0] })
	return closest(p, make([][2]float64, len(p)))
}

func dist(a, b [2]float64) float64 { return math.Hypot(a[0]-b[0], a[1]-b[1]) }

// closest returns the smallest distance in p and leaves p sorted by y.
func closest(p, aux [][2]float64) float64 {
	n := len(p)
	if n <= 3 {
		best := math.Inf(1)
		for i := 0; i < n; i++ {
			for j := i + 1; j < n; j++ {
				best = math.Min(best, dist(p[i], p[j]))
			}
		}
		sort.Slice(p, func(i, j int) bool { return p[i][1] < p[j][1] })
		return best
	}
	mid := n / 2
	midX := p[mid][0]
	d := math.Min(closest(p[:mid], aux[:mid]), closest(p[mid:], aux[mid:]))

	i, j := 0, mid // merge the y-sorted halves
	for k := 0; k < n; k++ {
		if j >= n || (i < mid && p[i][1] <= p[j][1]) {
			aux[k] = p[i]
			i++
		} else {
			aux[k] = p[j]
			j++
		}
	}
	copy(p, aux[:n])

	var strip [][2]float64
	for _, q := range p {
		if math.Abs(q[0]-midX) < d {
			strip = append(strip, q)
		}
	}
	for a := range strip {
		for b := a + 1; b < len(strip) && strip[b][1]-strip[a][1] < d; b++ {
			d = math.Min(d, dist(strip[a], strip[b]))
		}
	}
	return d
}

func ClosestPairBruteForce(p [][2]float64) float64 {
	best := math.Inf(1)
	for i := range p {
		for j := i + 1; j < len(p); j++ {
			best = math.Min(best, dist(p[i], p[j]))
		}
	}
	return best
}
