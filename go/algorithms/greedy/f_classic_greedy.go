package greedy

import "sort"

// Classic greedy (full story in F_ClassicGreedy.java).
//
//	FractionalKnapsack     best value/weight ratio first, split the last item (0/1 knapsack needs DP!)
//	AssignMiceToHoles      sort both, pair in order, answer = max |mouse - hole|
//	PolicemenCatchThieves  two pointers over P and T positions

func FractionalKnapsack(values, weights []int, capacity int) float64 {
	order := make([]int, len(values))
	for i := range order {
		order[i] = i
	}
	sort.Slice(order, func(a, b int) bool {
		i, j := order[a], order[b]
		return float64(values[i])/float64(weights[i]) > float64(values[j])/float64(weights[j])
	})
	total, left := 0.0, capacity
	for _, i := range order {
		if left == 0 {
			break
		}
		take := min(left, weights[i])
		total += float64(values[i]) * float64(take) / float64(weights[i])
		left -= take
	}
	return total
}

func AssignMiceToHoles(mice, holes []int) int {
	m := append([]int(nil), mice...)
	h := append([]int(nil), holes...)
	sort.Ints(m)
	sort.Ints(h)
	worst := 0
	for i := range m {
		d := m[i] - h[i]
		if d < 0 {
			d = -d
		}
		worst = max(worst, d)
	}
	return worst
}

func PolicemenCatchThieves(line string, k int) int {
	var police, thieves []int
	for i := 0; i < len(line); i++ {
		if line[i] == 'P' {
			police = append(police, i)
		} else {
			thieves = append(thieves, i)
		}
	}
	caught, p, t := 0, 0, 0
	for p < len(police) && t < len(thieves) {
		d := police[p] - thieves[t]
		switch {
		case d <= k && d >= -k:
			caught, p, t = caught+1, p+1, t+1
		case thieves[t] < police[p]:
			t++
		default:
			p++
		}
	}
	return caught
}
