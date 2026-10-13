// Package sorting: every comparison and non-comparison sort (see the Java file for the
// complexity / stability table). Go's sort.Ints / slices.Sort use pdqsort (unstable);
// sort.Stable / slices.SortStableFunc are stable.
package sorting

import "math/rand"

func BubbleSort(in []int) []int {
	a := append([]int(nil), in...)
	for end := len(a) - 1; end > 0; end-- {
		swapped := false
		for i := 0; i < end; i++ {
			if a[i] > a[i+1] {
				a[i], a[i+1] = a[i+1], a[i]
				swapped = true
			}
		}
		if !swapped {
			break
		}
	}
	return a
}

func SelectionSort(in []int) []int {
	a := append([]int(nil), in...)
	for i := 0; i < len(a)-1; i++ {
		m := i
		for j := i + 1; j < len(a); j++ {
			if a[j] < a[m] {
				m = j
			}
		}
		a[i], a[m] = a[m], a[i]
	}
	return a
}

func InsertionSort(in []int) []int {
	a := append([]int(nil), in...)
	for i := 1; i < len(a); i++ {
		x, j := a[i], i-1
		for ; j >= 0 && a[j] > x; j-- {
			a[j+1] = a[j]
		}
		a[j+1] = x
	}
	return a
}

func ShellSort(in []int) []int {
	a := append([]int(nil), in...)
	gap := 1
	for gap < len(a)/3 {
		gap = 3*gap + 1
	}
	for ; gap >= 1; gap /= 3 {
		for i := gap; i < len(a); i++ {
			x, j := a[i], i
			for ; j >= gap && a[j-gap] > x; j -= gap {
				a[j] = a[j-gap]
			}
			a[j] = x
		}
	}
	return a
}

func MergeSort(in []int) []int {
	if len(in) <= 1 {
		return append([]int(nil), in...)
	}
	mid := len(in) / 2
	left, right := MergeSort(in[:mid]), MergeSort(in[mid:])
	out := make([]int, 0, len(in))
	i, j := 0, 0
	for i < len(left) && j < len(right) {
		if left[i] <= right[j] { // <= keeps it stable
			out = append(out, left[i])
			i++
		} else {
			out = append(out, right[j])
			j++
		}
	}
	out = append(out, left[i:]...)
	return append(out, right[j:]...)
}

func QuickSort(in []int) []int {
	a := append([]int(nil), in...)
	rng := rand.New(rand.NewSource(7))
	var sort func(lo, hi int)
	sort = func(lo, hi int) {
		if lo >= hi {
			return
		}
		p := lo + rng.Intn(hi-lo+1)
		a[p], a[hi] = a[hi], a[p]
		store := lo
		for i := lo; i < hi; i++ {
			if a[i] < a[hi] {
				a[i], a[store] = a[store], a[i]
				store++
			}
		}
		a[store], a[hi] = a[hi], a[store]
		sort(lo, store-1)
		sort(store+1, hi)
	}
	sort(0, len(a)-1)
	return a
}

func QuickSort3Way(in []int) []int {
	a := append([]int(nil), in...)
	rng := rand.New(rand.NewSource(7))
	var sort func(lo, hi int)
	sort = func(lo, hi int) {
		if lo >= hi {
			return
		}
		pivot := a[lo+rng.Intn(hi-lo+1)]
		lt, i, gt := lo, lo, hi
		for i <= gt {
			switch {
			case a[i] < pivot:
				a[lt], a[i] = a[i], a[lt]
				lt, i = lt+1, i+1
			case a[i] > pivot:
				a[i], a[gt] = a[gt], a[i]
				gt--
			default:
				i++
			}
		}
		sort(lo, lt-1)
		sort(gt+1, hi)
	}
	sort(0, len(a)-1)
	return a
}

func HeapSort(in []int) []int {
	a := append([]int(nil), in...)
	siftDown := func(i, n int) {
		for {
			l, r, largest := 2*i+1, 2*i+2, i
			if l < n && a[l] > a[largest] {
				largest = l
			}
			if r < n && a[r] > a[largest] {
				largest = r
			}
			if largest == i {
				return
			}
			a[i], a[largest] = a[largest], a[i]
			i = largest
		}
	}
	for i := len(a)/2 - 1; i >= 0; i-- {
		siftDown(i, len(a))
	}
	for end := len(a) - 1; end > 0; end-- {
		a[0], a[end] = a[end], a[0]
		siftDown(0, end)
	}
	return a
}
