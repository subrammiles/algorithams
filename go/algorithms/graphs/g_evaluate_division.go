package graphs

// CalcEquation uses weighted union-find: weight[x] = x / parent(x).
func CalcEquation(equations [][2]string, values []float64, queries [][2]string) []float64 {
	parent, weight := map[string]string{}, map[string]float64{}
	var find func(string) string
	find = func(x string) string {
		if p := parent[x]; p != x {
			root := find(p)
			weight[x] *= weight[p] // x/p * p/root
			parent[x] = root
		}
		return parent[x]
	}
	for i, eq := range equations {
		for _, x := range eq {
			if _, ok := parent[x]; !ok {
				parent[x], weight[x] = x, 1
			}
		}
		a, b := eq[0], eq[1]
		if ra, rb := find(a), find(b); ra != rb {
			parent[ra] = rb
			weight[ra] = values[i] * weight[b] / weight[a]
		}
	}
	out := make([]float64, len(queries))
	for i, q := range queries {
		_, okA := parent[q[0]]
		_, okB := parent[q[1]]
		if !okA || !okB || find(q[0]) != find(q[1]) {
			out[i] = -1
		} else {
			out[i] = weight[q[0]] / weight[q[1]]
		}
	}
	return out
}
