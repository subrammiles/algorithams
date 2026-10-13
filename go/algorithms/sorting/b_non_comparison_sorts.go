package sorting

// CountingSort is O(n + k), stable, and handles negatives with an offset.
func CountingSort(a []int) []int {
	if len(a) == 0 {
		return []int{}
	}
	lo, hi := a[0], a[0]
	for _, x := range a {
		lo, hi = min(lo, x), max(hi, x)
	}
	count := make([]int, hi-lo+1)
	for _, x := range a {
		count[x-lo]++
	}
	for i := 1; i < len(count); i++ {
		count[i] += count[i-1]
	}
	out := make([]int, len(a))
	for i := len(a) - 1; i >= 0; i-- { // backwards = stable
		count[a[i]-lo]--
		out[count[a[i]-lo]] = a[i]
	}
	return out
}

// RadixSort (LSD) for non-negative integers: a stable bucket pass per decimal digit.
func RadixSort(in []int) []int {
	a := append([]int(nil), in...)
	maxV := 0
	for _, x := range a {
		maxV = max(maxV, x)
	}
	for exp := 1; maxV/exp > 0; exp *= 10 {
		var buckets [10][]int
		for _, x := range a {
			d := x / exp % 10
			buckets[d] = append(buckets[d], x)
		}
		a = a[:0]
		for _, b := range buckets {
			a = append(a, b...)
		}
	}
	return a
}

// BucketSort for values in [0, 1).
func BucketSort(a []float64) []float64 {
	buckets := make([][]float64, len(a))
	for _, x := range a {
		i := int(x * float64(len(a)))
		buckets[i] = append(buckets[i], x)
	}
	var out []float64
	for _, b := range buckets {
		for i := 1; i < len(b); i++ { // insertion sort: buckets are tiny on average
			for j := i; j > 0 && b[j] < b[j-1]; j-- {
				b[j], b[j-1] = b[j-1], b[j]
			}
		}
		out = append(out, b...)
	}
	return out
}
