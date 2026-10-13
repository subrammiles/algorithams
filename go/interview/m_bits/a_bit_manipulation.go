// Package m_bits: x & (x-1) clears the lowest set bit, x & -x isolates it, a ^ a = 0.
// math/bits has OnesCount, Reverse, TrailingZeros, LeadingZeros built in.
package m_bits

import "sort"

func IsPowerOfTwo(x int) bool { return x > 0 && x&(x-1) == 0 }

func SingleNumber(nums []int) int {
	x := 0
	for _, n := range nums {
		x ^= n
	}
	return x
}

// SingleNumberThrice counts each bit mod 3 (int32 keeps two's complement semantics).
func SingleNumberThrice(nums []int32) int32 {
	var result int32
	for bit := 0; bit < 32; bit++ {
		count := 0
		for _, n := range nums {
			count += int(n>>bit) & 1
		}
		if count%3 != 0 {
			result |= 1 << bit
		}
	}
	return result
}

func TwoSingleNumbers(nums []int) []int {
	xor := SingleNumber(nums)
	diff := xor & -xor
	a := 0
	for _, n := range nums {
		if n&diff != 0 {
			a ^= n
		}
	}
	out := []int{a, xor ^ a}
	sort.Ints(out)
	return out
}

func CountBits(n int) []int {
	bits := make([]int, n+1)
	for i := 1; i <= n; i++ {
		bits[i] = bits[i&(i-1)] + 1
	}
	return bits
}

func ReverseBits(x uint32) uint32 {
	var r uint32
	for i := 0; i < 32; i++ {
		r = r<<1 | x&1
		x >>= 1
	}
	return r
}

// Add without +: XOR is the sum without carry, AND << 1 is the carry.
func Add(a, b int32) int32 {
	for b != 0 {
		a, b = a^b, (a&b)<<1
	}
	return a
}
