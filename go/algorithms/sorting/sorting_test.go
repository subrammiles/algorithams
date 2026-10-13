package sorting

import (
	"math/rand"
	"reflect"
	"sort"
	"testing"
)

func TestComparisonSorts(t *testing.T) {
	rng := rand.New(rand.NewSource(1))
	random := func(n, lo, hi int) []int {
		out := make([]int, n)
		for i := range out {
			out[i] = lo + rng.Intn(hi-lo)
		}
		return out
	}
	inputs := [][]int{{}, {1}, {5, 4, 3, 2, 1}, {1, 2, 3, 4, 5}, {3, 1, 3, 1, 3, 1}, random(1000, -500, 500), random(1000, 0, 3)}
	sorts := map[string]func([]int) []int{
		"bubble": BubbleSort, "selection": SelectionSort, "insertion": InsertionSort, "shell": ShellSort,
		"merge": MergeSort, "quick": QuickSort, "quick3": QuickSort3Way, "heap": HeapSort,
	}
	for _, in := range inputs {
		want := append([]int{}, in...)
		sort.Ints(want)
		for name, s := range sorts {
			if got := s(in); !reflect.DeepEqual(append([]int{}, got...), want) {
				t.Errorf("%s failed on %v", name, in)
			}
		}
	}
}

func TestNonComparisonSorts(t *testing.T) {
	if got := CountingSort([]int{4, -2, 2, 8, 3, 3, -2, 1}); !reflect.DeepEqual(got, []int{-2, -2, 1, 2, 3, 3, 4, 8}) {
		t.Errorf("counting %v", got)
	}
	if got := RadixSort([]int{170, 45, 75, 90, 802, 24, 2, 66}); !reflect.DeepEqual(got, []int{2, 24, 45, 66, 75, 90, 170, 802}) {
		t.Errorf("radix %v", got)
	}
	big := make([]int, 2000)
	for i := range big {
		big[i] = rand.Intn(1 << 31)
	}
	want := append([]int(nil), big...)
	sort.Ints(want)
	if !reflect.DeepEqual(RadixSort(big), want) {
		t.Error("radix large")
	}
	d := []float64{0.897, 0.565, 0.656, 0.1234, 0.665, 0.3434}
	wantD := append([]float64(nil), d...)
	sort.Float64s(wantD)
	if !reflect.DeepEqual(BucketSort(d), wantD) {
		t.Error("bucket")
	}
}
