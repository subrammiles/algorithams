// Package s_math: number theory, in-place matrix tricks and reservoir sampling.
package s_math

import "math/rand"

func GCD(a, b int) int {
	for b != 0 {
		a, b = b, a%b
	}
	return a
}

func ModPow(base, exp, mod int) int {
	result := 1
	base %= mod
	for exp > 0 {
		if exp&1 == 1 {
			result = result * base % mod
		}
		base = base * base % mod
		exp >>= 1
	}
	return result
}

func Sieve(n int) []int {
	composite := make([]bool, n+1)
	var primes []int
	for i := 2; i <= n; i++ {
		if composite[i] {
			continue
		}
		primes = append(primes, i)
		for j := i * i; j <= n; j += i {
			composite[j] = true
		}
	}
	return primes
}

// Rotate turns the matrix 90 degrees clockwise: transpose, then reverse each row.
func Rotate(m [][]int) {
	n := len(m)
	for i := 0; i < n; i++ {
		for j := i + 1; j < n; j++ {
			m[i][j], m[j][i] = m[j][i], m[i][j]
		}
	}
	for _, row := range m {
		for l, r := 0, n-1; l < r; l, r = l+1, r-1 {
			row[l], row[r] = row[r], row[l]
		}
	}
}

func SpiralOrder(m [][]int) []int {
	var out []int
	top, bottom, left, right := 0, len(m)-1, 0, len(m[0])-1
	for top <= bottom && left <= right {
		for c := left; c <= right; c++ {
			out = append(out, m[top][c])
		}
		top++
		for r := top; r <= bottom; r++ {
			out = append(out, m[r][right])
		}
		right--
		if top <= bottom {
			for c := right; c >= left; c-- {
				out = append(out, m[bottom][c])
			}
			bottom--
		}
		if left <= right {
			for r := bottom; r >= top; r-- {
				out = append(out, m[r][left])
			}
			left++
		}
	}
	return out
}

// ReservoirSample picks uniformly from a stream of unknown length: item i is kept w.p. 1/i.
func ReservoirSample(stream <-chan int, rng *rand.Rand) int {
	chosen, i := 0, 0
	for x := range stream {
		i++
		if rng.Intn(i) == 0 {
			chosen = x
		}
	}
	return chosen
}
