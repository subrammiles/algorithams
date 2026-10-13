package greedy

import "sort"

func CanJump(nums []int) bool {
	furthest := 0
	for i, n := range nums {
		if i > furthest {
			return false
		}
		furthest = max(furthest, i+n)
	}
	return true
}

func FindContentChildren(greed, cookies []int) int {
	g, s := append([]int(nil), greed...), append([]int(nil), cookies...)
	sort.Ints(g)
	sort.Ints(s)
	child := 0
	for _, cookie := range s {
		if child < len(g) && cookie >= g[child] {
			child++
		}
	}
	return child
}

func NumRescueBoats(people []int, limit int) int {
	p := append([]int(nil), people...)
	sort.Ints(p)
	boats := 0
	for lo, hi := 0, len(p)-1; lo <= hi; hi, boats = hi-1, boats+1 {
		if p[lo]+p[hi] <= limit {
			lo++
		}
	}
	return boats
}

func TwoCitySchedCost(costs [][2]int) int {
	c := append([][2]int(nil), costs...)
	sort.Slice(c, func(i, j int) bool { return c[i][0]-c[i][1] < c[j][0]-c[j][1] })
	total := 0
	for i, x := range c {
		if i < len(c)/2 {
			total += x[0]
		} else {
			total += x[1]
		}
	}
	return total
}

// GreedyCoinCount is only optimal for canonical coin systems ({4,3,1}, 6 -> 3 coins, optimal 2).
func GreedyCoinCount(coinsDescending []int, amount int) int {
	count := 0
	for _, coin := range coinsDescending {
		count += amount / coin
		amount %= coin
	}
	if amount != 0 {
		return -1
	}
	return count
}
