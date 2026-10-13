package m_bits

import (
	"math/bits"
	"reflect"
	"testing"
)

func TestBits(t *testing.T) {
	if !IsPowerOfTwo(64) || IsPowerOfTwo(6) {
		t.Error("power of two")
	}
	if SingleNumber([]int{4, 1, 2, 1, 2}) != 4 || SingleNumberThrice([]int32{0, 1, 0, 1, 0, 1, -99}) != -99 {
		t.Error("single number")
	}
	if !reflect.DeepEqual(TwoSingleNumbers([]int{1, 2, 1, 3, 2, 5}), []int{3, 5}) {
		t.Error("two singles")
	}
	if !reflect.DeepEqual(CountBits(5), []int{0, 1, 1, 2, 1, 2}) {
		t.Error("count bits")
	}
	x := uint32(0b00000010100101000001111010011100)
	if ReverseBits(x) != 0b00111001011110000010100101000000 || ReverseBits(x) != bits.Reverse32(x) {
		t.Error("reverse bits")
	}
	if Add(15, -7) != 8 {
		t.Error("add")
	}
}
