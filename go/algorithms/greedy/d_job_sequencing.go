package greedy

import "sort"

type Job struct {
	ID               string
	Deadline, Profit int
}

// Schedule takes jobs by decreasing profit into the latest free slot <= deadline (union-find).
func Schedule(jobs []Job) []string {
	sorted := append([]Job(nil), jobs...)
	sort.Slice(sorted, func(i, j int) bool { return sorted[i].Profit > sorted[j].Profit })
	maxDeadline := 0
	for _, j := range jobs {
		maxDeadline = max(maxDeadline, j.Deadline)
	}
	parent := make([]int, maxDeadline+1)
	for i := range parent {
		parent[i] = i
	}
	find := func(x int) int {
		for parent[x] != x {
			parent[x] = parent[parent[x]]
			x = parent[x]
		}
		return x
	}
	slots := make([]string, maxDeadline+1)
	for _, j := range sorted {
		if slot := find(j.Deadline); slot > 0 {
			slots[slot] = j.ID
			parent[slot] = slot - 1
		}
	}
	var order []string
	for _, id := range slots[1:] {
		if id != "" {
			order = append(order, id)
		}
	}
	return order
}

func TotalProfit(jobs []Job, chosen []string) int {
	total := 0
	for _, j := range jobs {
		for _, id := range chosen {
			if j.ID == id {
				total += j.Profit
			}
		}
	}
	return total
}
