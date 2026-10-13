"""Recursion basics: base case + recursive case. Depth n costs O(n) stack
(Python's default recursion limit is 1000).

factorial O(n) | naive fibonacci O(2^n) | memoised fibonacci O(n) (= top-down DP) | fast power O(log n)
"""
from functools import lru_cache


def factorial(n):
    return 1 if n <= 1 else n * factorial(n - 1)


def fib_naive(n):
    return n if n < 2 else fib_naive(n - 1) + fib_naive(n - 2)


@lru_cache(maxsize=None)
def fib_memo(n):
    return n if n < 2 else fib_memo(n - 1) + fib_memo(n - 2)


def fast_power(x, n):
    if n < 0:
        return 1 / fast_power(x, -n)
    if n == 0:
        return 1
    half = fast_power(x, n // 2)
    return half * half if n % 2 == 0 else half * half * x


def sum_of_digits(n):
    return n if n < 10 else n % 10 + sum_of_digits(n // 10)


def reverse(s):
    return s if not s else reverse(s[1:]) + s[0]


def is_palindrome(s, lo=0, hi=None):
    hi = len(s) - 1 if hi is None else hi
    if lo >= hi:
        return True
    return s[lo] == s[hi] and is_palindrome(s, lo + 1, hi - 1)


if __name__ == "__main__":
    assert factorial(10) == 3628800
    assert fib_naive(20) == 6765 and fib_memo(50) == 12586269025
    assert fast_power(2, 10) == 1024 and fast_power(2, -2) == 0.25
    assert sum_of_digits(9875) == 29
    assert reverse("hello") == "olleh"
    assert is_palindrome("racecar") and not is_palindrome("abca")
    print("all checks passed")
