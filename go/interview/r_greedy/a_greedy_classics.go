// Package r_greedy: local choices that can be proven safe (exchange argument).
package r_greedy

func Jump(nums []int) int {
	jumps, currentEnd, farthest := 0, 0, 0
	for i := 0; i < len(nums)-1; i++ {
		farthest = max(farthest, i+nums[i])
		if i == currentEnd {
			jumps, currentEnd = jumps+1, farthest
		}
	}
	return jumps
}

func CanCompleteCircuit(gas, cost []int) int {
	total, tank, start := 0, 0, 0
	for i := range gas {
		total += gas[i] - cost[i]
		tank += gas[i] - cost[i]
		if tank < 0 {
			start, tank = i+1, 0
		}
	}
	if total < 0 {
		return -1
	}
	return start
}

func LeastInterval(tasks string, n int) int {
	var freq [26]int
	for i := 0; i < len(tasks); i++ {
		freq[tasks[i]-'A']++
	}
	top, countTop := 0, 0
	for _, f := range freq {
		if f > top {
			top, countTop = f, 1
		} else if f == top {
			countTop++
		}
	}
	return max(len(tasks), (top-1)*(n+1)+countTop)
}

func Candy(ratings []int) int {
	c := make([]int, len(ratings))
	for i := range c {
		c[i] = 1
	}
	for i := 1; i < len(ratings); i++ {
		if ratings[i] > ratings[i-1] {
			c[i] = c[i-1] + 1
		}
	}
	sum := c[len(c)-1]
	for i := len(ratings) - 2; i >= 0; i-- {
		if ratings[i] > ratings[i+1] {
			c[i] = max(c[i], c[i+1]+1)
		}
		sum += c[i]
	}
	return sum
}
