package recursion

// Hanoi appends the 2^n - 1 moves needed to move n disks from src to dst.
func Hanoi(n int, src, dst, via string, moves *[]string) {
	if n == 0 {
		return
	}
	Hanoi(n-1, src, via, dst, moves)
	*moves = append(*moves, src+"->"+dst)
	Hanoi(n-1, via, dst, src, moves)
}

// Josephus returns the 1-based survivor: J(1) = 0, J(n) = (J(n-1) + k) % n.
func Josephus(n, k int) int {
	pos := 0
	for m := 2; m <= n; m++ { // iterative form of the same recurrence
		pos = (pos + k) % m
	}
	return pos + 1
}

func BinaryStringsNoConsecutiveOnes(n int) []string {
	var out []string
	var gen func(cur string)
	gen = func(cur string) {
		if len(cur) == n {
			out = append(out, cur)
			return
		}
		gen(cur + "0")
		if cur == "" || cur[len(cur)-1] == '0' {
			gen(cur + "1")
		}
	}
	gen("")
	return out
}

var keypad = map[byte]string{'2': "abc", '3': "def", '4': "ghi", '5': "jkl", '6': "mno", '7': "pqrs", '8': "tuv", '9': "wxyz"}

func LetterCombinations(digits string) []string {
	out := []string{}
	if digits == "" {
		return out
	}
	var gen func(i int, cur string)
	gen = func(i int, cur string) {
		if i == len(digits) {
			out = append(out, cur)
			return
		}
		for _, ch := range keypad[digits[i]] {
			gen(i+1, cur+string(ch))
		}
	}
	gen(0, "")
	return out
}

func GenerateParentheses(n int) []string {
	var out []string
	var gen func(cur string, open, close int)
	gen = func(cur string, open, close int) {
		if len(cur) == 2*n {
			out = append(out, cur)
			return
		}
		if open < n {
			gen(cur+"(", open+1, close)
		}
		if close < open {
			gen(cur+")", open, close+1)
		}
	}
	gen("", 0, 0)
	return out
}
