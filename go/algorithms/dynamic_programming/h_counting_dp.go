package dynamic_programming

const mod = 1_000_000_007

// NumTrees counts BSTs with n keys: Catalan numbers.
func NumTrees(n int) int {
	g := make([]int, n+1)
	g[0] = 1
	for nodes := 1; nodes <= n; nodes++ {
		for root := 1; root <= nodes; root++ {
			g[nodes] += g[root-1] * g[nodes-root]
		}
	}
	return g[n]
}

func CountPalindromicSubstrings(s string) int {
	count := 0
	for centre := 0; centre < 2*len(s)-1; centre++ {
		for lo, hi := centre/2, centre/2+centre%2; lo >= 0 && hi < len(s) && s[lo] == s[hi]; lo, hi = lo-1, hi+1 {
			count++
		}
	}
	return count
}

func NumRollsToTarget(dice, faces, target int) int {
	ways := make([]int, target+1)
	ways[0] = 1
	for d := 0; d < dice; d++ {
		next := make([]int, target+1)
		for t := 1; t <= target; t++ {
			for f := 1; f <= faces && f <= t; f++ {
				next[t] = (next[t] + ways[t-f]) % mod
			}
		}
		ways = next
	}
	return ways[target]
}
