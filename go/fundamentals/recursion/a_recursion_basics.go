// Package recursion: base case + recursive case. Depth n costs O(n) stack (Go stacks grow
// dynamically, so deep recursion is less of a problem than in Java/Python).
package recursion

func Factorial(n int) int {
	if n <= 1 {
		return 1
	}
	return n * Factorial(n-1)
}

// FibNaive is O(2^n): two calls per level.
func FibNaive(n int) int {
	if n < 2 {
		return n
	}
	return FibNaive(n-1) + FibNaive(n-2)
}

// FibMemo caches results: O(n) - top-down dynamic programming.
func FibMemo(n int, memo map[int]int) int {
	if n < 2 {
		return n
	}
	if v, ok := memo[n]; ok {
		return v
	}
	memo[n] = FibMemo(n-1, memo) + FibMemo(n-2, memo)
	return memo[n]
}

// FastPower computes x^n in O(log n) by squaring.
func FastPower(x float64, n int) float64 {
	if n < 0 {
		return 1 / FastPower(x, -n)
	}
	if n == 0 {
		return 1
	}
	half := FastPower(x, n/2)
	if n%2 == 0 {
		return half * half
	}
	return half * half * x
}

func SumOfDigits(n int) int {
	if n < 10 {
		return n
	}
	return n%10 + SumOfDigits(n/10)
}

func Reverse(s string) string {
	if s == "" {
		return s
	}
	return Reverse(s[1:]) + s[:1]
}

func IsPalindrome(s string) bool {
	if len(s) < 2 {
		return true
	}
	return s[0] == s[len(s)-1] && IsPalindrome(s[1:len(s)-1])
}
