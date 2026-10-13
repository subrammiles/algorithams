// Package o_strings: linear-time pattern matching (KMP and Z-algorithm).
package o_strings

func LPS(p string) []int {
	lps := make([]int, len(p))
	for i, length := 1, 0; i < len(p); {
		switch {
		case p[i] == p[length]:
			length++
			lps[i] = length
			i++
		case length > 0:
			length = lps[length-1]
		default:
			i++
		}
	}
	return lps
}

func KMPSearch(text, p string) []int {
	lps := LPS(p)
	matches := []int{}
	for i, j := 0, 0; i < len(text); i++ {
		for j > 0 && text[i] != p[j] {
			j = lps[j-1]
		}
		if text[i] == p[j] {
			j++
			if j == len(p) {
				matches = append(matches, i-j+1)
				j = lps[j-1]
			}
		}
	}
	return matches
}

func ZArray(s string) []int {
	z := make([]int, len(s))
	for i, l, r := 1, 0, 0; i < len(s); i++ {
		if i < r {
			z[i] = min(r-i, z[i-l])
		}
		for i+z[i] < len(s) && s[z[i]] == s[i+z[i]] {
			z[i]++
		}
		if i+z[i] > r {
			l, r = i, i+z[i]
		}
	}
	return z
}

func ZSearch(text, p string) []int {
	z := ZArray(p + "$" + text)
	matches := []int{}
	for i := len(p) + 1; i < len(z); i++ {
		if z[i] == len(p) {
			matches = append(matches, i-len(p)-1)
		}
	}
	return matches
}

func RepeatedSubstringPattern(s string) bool {
	longest := LPS(s)[len(s)-1]
	return longest > 0 && len(s)%(len(s)-longest) == 0
}
