package graphs

import (
	"math"
	"testing"
)

func TestCalcEquation(t *testing.T) {
	got := CalcEquation([][2]string{{"a", "b"}, {"b", "c"}}, []float64{2, 3}, [][2]string{{"a", "c"}, {"b", "a"}, {"a", "e"}, {"a", "a"}, {"x", "x"}})
	for i, want := range []float64{6, 0.5, -1, 1, -1} {
		if math.Abs(got[i]-want) > 1e-9 {
			t.Fatalf("query %d = %v, want %v", i, got[i], want)
		}
	}
	chain := CalcEquation([][2]string{{"a", "b"}, {"c", "d"}, {"b", "c"}}, []float64{1.5, 2.5, 5}, [][2]string{{"a", "d"}, {"d", "a"}})
	if math.Abs(chain[0]-18.75) > 1e-9 || math.Abs(chain[1]-1/18.75) > 1e-9 {
		t.Fatalf("chain %v", chain)
	}
}
