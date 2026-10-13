package recursion

import "testing"

func TestRecursionBasics(t *testing.T) {
	if Factorial(10) != 3628800 || FibNaive(20) != 6765 || FibMemo(50, map[int]int{}) != 12586269025 {
		t.Error("factorial / fibonacci")
	}
	if FastPower(2, 10) != 1024 || FastPower(2, -2) != 0.25 {
		t.Error("fast power")
	}
	if SumOfDigits(9875) != 29 || Reverse("hello") != "olleh" || !IsPalindrome("racecar") || IsPalindrome("abca") {
		t.Error("digits / reverse / palindrome")
	}
}
