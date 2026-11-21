package o_strings

import "strings"

// Rabin-Karp, longest duplicate substring and Manacher (full explanations in the Java version).
//
//	RabinKarp            rolling hash; slide in O(1); VERIFY on a hash match (collisions)
//	LongestDupSubstring  binary search on length + rolling-hash check, O(n log n)
//	LongestPalindrome    Manacher, O(n)

const (
	hashMod  = 1_000_000_007
	hashBase = 131
)

func powMod(b, e int) int {
	r := 1
	for i := 0; i < e; i++ {
		r = r * b % hashMod
	}
	return r
}

func RabinKarp(text, pattern string) []int {
	n, m := len(text), len(pattern)
	out := []int{}
	if m == 0 || m > n {
		return out
	}
	power := powMod(hashBase, m-1)
	target, window := 0, 0
	for i := 0; i < m; i++ {
		target = (target*hashBase + int(pattern[i])) % hashMod
		window = (window*hashBase + int(text[i])) % hashMod
	}
	for i := 0; i+m <= n; i++ {
		if window == target && text[i:i+m] == pattern {
			out = append(out, i)
		}
		if i+m < n {
			window = (window - int(text[i])*power%hashMod + hashMod) % hashMod
			window = (window*hashBase + int(text[i+m])) % hashMod
		}
	}
	return out
}

func duplicateOfLength(s string, length int) int {
	power := powMod(hashBase, length-1)
	seen := map[int][]int{}
	h := 0
	for i := 0; i < len(s); i++ {
		if i >= length {
			h = (h - int(s[i-length])*power%hashMod + hashMod) % hashMod
		}
		h = (h*hashBase + int(s[i])) % hashMod
		if i < length-1 {
			continue
		}
		start := i - length + 1
		for _, other := range seen[h] {
			if s[other:other+length] == s[start:start+length] {
				return start
			}
		}
		seen[h] = append(seen[h], start)
	}
	return -1
}

func LongestDupSubstring(s string) string {
	lo, hi, best := 1, len(s)-1, ""
	for lo <= hi {
		mid := (lo + hi) / 2
		if start := duplicateOfLength(s, mid); start >= 0 {
			best = s[start : start+mid]
			lo = mid + 1
		} else {
			hi = mid - 1
		}
	}
	return best
}

func LongestPalindrome(s string) string {
	if s == "" {
		return s
	}
	var sb strings.Builder
	sb.WriteByte('#')
	for i := 0; i < len(s); i++ {
		sb.WriteByte(s[i])
		sb.WriteByte('#')
	}
	t := sb.String()
	n := len(t)
	p := make([]int, n)
	center, right, best := 0, 0, 0
	for i := 0; i < n; i++ {
		if i < right {
			p[i] = min(right-i, p[2*center-i])
		}
		for i-p[i]-1 >= 0 && i+p[i]+1 < n && t[i-p[i]-1] == t[i+p[i]+1] {
			p[i]++
		}
		if i+p[i] > right {
			center, right = i, i+p[i]
		}
		if p[i] > p[best] {
			best = i
		}
	}
	start := (best - p[best]) / 2
	return s[start : start+p[best]]
}
