package divide_and_conquer

import (
	"math"
	"math/rand"
	"testing"
)

func TestClosestPair(t *testing.T) {
	pts := [][2]float64{{2, 3}, {12, 30}, {40, 50}, {5, 1}, {12, 10}, {3, 4}}
	if got := ClosestPair(pts); math.Abs(got-math.Sqrt2) > 1e-9 {
		t.Errorf("classic example %v", got)
	}
	if got := ClosestPair([][2]float64{{1, 1}, {5, 5}, {1, 1}}); got != 0 {
		t.Errorf("duplicates %v", got)
	}
	if got := ClosestPair([][2]float64{{0, 0}, {3, 4}}); got != 5 {
		t.Errorf("two points %v", got)
	}
	rng := rand.New(rand.NewSource(3))
	for trial := 0; trial < 20; trial++ {
		p := make([][2]float64, 300)
		for i := range p {
			p[i] = [2]float64{rng.Float64() * 1000, rng.Float64() * 1000}
		}
		if got, want := ClosestPair(p), ClosestPairBruteForce(p); math.Abs(got-want) > 1e-9 {
			t.Fatalf("trial %d: %v vs brute force %v", trial, got, want)
		}
	}
}
