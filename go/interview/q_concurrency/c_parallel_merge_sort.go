package q_concurrency

import (
	"sort"
	"sync"
)

const sequentialThreshold = 1 << 13

// ParallelMergeSort sorts each half in its own goroutine above a size threshold.
// Below it, task overhead dominates, so fall back to sort.Ints.
func ParallelMergeSort(a []int) {
	buffer := make([]int, len(a))
	parallelSort(a, buffer)
}

func parallelSort(a, buffer []int) {
	if len(a) <= sequentialThreshold {
		sort.Ints(a)
		return
	}
	mid := len(a) / 2
	var wg sync.WaitGroup
	wg.Add(1)
	go func() {
		defer wg.Done()
		parallelSort(a[:mid], buffer[:mid])
	}()
	parallelSort(a[mid:], buffer[mid:]) // current goroutine does the other half
	wg.Wait()
	copy(buffer, a)
	i, j, k := 0, mid, 0
	for i < mid && j < len(a) {
		if buffer[i] <= buffer[j] {
			a[k] = buffer[i]
			i++
		} else {
			a[k] = buffer[j]
			j++
		}
		k++
	}
	k += copy(a[k:], buffer[i:mid])
	copy(a[k:], buffer[j:])
}
