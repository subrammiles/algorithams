package q_concurrency

import (
	"math/rand"
	"sort"
	"testing"
)

func TestParallelMergeSort(t *testing.T) {
	rng := rand.New(rand.NewSource(1))
	data := make([]int, 1_000_000)
	for i := range data {
		data[i] = rng.Int()
	}
	want := append([]int(nil), data...)
	sort.Ints(want)
	ParallelMergeSort(data)
	for i := range data {
		if data[i] != want[i] {
			t.Fatalf("mismatch at %d", i)
		}
	}
}
