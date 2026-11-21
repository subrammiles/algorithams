package a_twopointers_slidingwindow

// The three sliding-window templates (full explanations in the Java version):
//
//	A) longest valid:  grow right; while invalid shrink left; record after.
//	B) shortest valid: grow right; while valid record, then shrink left.
//	C) fixed size k:   add right, record once right >= k-1, remove right-k+1.
//	exactly(K) = atMost(K) - atMost(K-1).

func LengthOfLongestSubstring(s string) int {
	last := map[byte]int{}
	best, left := 0, 0
	for right := 0; right < len(s); right++ {
		if i, ok := last[s[right]]; ok {
			left = max(left, i+1)
		}
		last[s[right]] = right
		best = max(best, right-left+1)
	}
	return best
}

func FindAnagrams(s, p string) []int {
	k := len(p)
	out := []int{}
	var need, window [26]int
	for i := 0; i < k; i++ {
		need[p[i]-'a']++
	}
	for right := 0; right < len(s); right++ {
		window[s[right]-'a']++
		if right >= k {
			window[s[right-k]-'a']--
		}
		if right >= k-1 && need == window { // arrays compare by value in Go
			out = append(out, right-k+1)
		}
	}
	return out
}

func LongestOnes(nums []int, k int) int {
	best, zeros, left := 0, 0, 0
	for right, x := range nums {
		if x == 0 {
			zeros++
		}
		for zeros > k {
			if nums[left] == 0 {
				zeros--
			}
			left++
		}
		best = max(best, right-left+1)
	}
	return best
}

func atMostKDistinct(nums []int, k int) int {
	count := map[int]int{}
	total, left := 0, 0
	for right, x := range nums {
		count[x]++
		for len(count) > k {
			count[nums[left]]--
			if count[nums[left]] == 0 {
				delete(count, nums[left])
			}
			left++
		}
		total += right - left + 1
	}
	return total
}

func SubarraysWithKDistinct(nums []int, k int) int {
	return atMostKDistinct(nums, k) - atMostKDistinct(nums, k-1)
}

func MinSubArrayLen(target int, nums []int) int {
	best, sum, left := len(nums)+1, 0, 0
	for right, x := range nums {
		sum += x
		for sum >= target {
			best = min(best, right-left+1)
			sum -= nums[left]
			left++
		}
	}
	if best == len(nums)+1 {
		return 0
	}
	return best
}
