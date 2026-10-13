package a_twopointers_slidingwindow

// CharacterReplacement: window valid while length - maxFreq <= k; maxFreq never decreases. O(n).
func CharacterReplacement(s string, k int) int {
	var count [26]int
	left, maxFreq, best := 0, 0, 0
	for right := 0; right < len(s); right++ {
		count[s[right]-'A']++
		maxFreq = max(maxFreq, count[s[right]-'A'])
		for right-left+1-maxFreq > k {
			count[s[left]-'A']--
			left++
		}
		best = max(best, right-left+1)
	}
	return best
}
