package s_math

import (
	"math/rand"
	"reflect"
	"testing"
)

func TestMath(t *testing.T) {
	if GCD(84, 36) != 12 || ModPow(2, 30, 1_000_000_007) != 73741817 {
		t.Error("gcd / modpow")
	}
	if !reflect.DeepEqual(Sieve(30), []int{2, 3, 5, 7, 11, 13, 17, 19, 23, 29}) {
		t.Error("sieve")
	}
	m := [][]int{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}
	Rotate(m)
	if !reflect.DeepEqual(m, [][]int{{7, 4, 1}, {8, 5, 2}, {9, 6, 3}}) {
		t.Errorf("rotate %v", m)
	}
	if got := SpiralOrder([][]int{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}); !reflect.DeepEqual(got, []int{1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7}) {
		t.Errorf("spiral %v", got)
	}
}

func TestReservoirSample(t *testing.T) {
	rng := rand.New(rand.NewSource(3))
	hits := make([]int, 5)
	for trial := 0; trial < 20000; trial++ {
		ch := make(chan int, 5)
		for i := 0; i < 5; i++ {
			ch <- i
		}
		close(ch)
		hits[ReservoirSample(ch, rng)]++
	}
	for _, h := range hits {
		if h < 3600 || h > 4400 {
			t.Fatalf("not uniform: %v", hits)
		}
	}
}
