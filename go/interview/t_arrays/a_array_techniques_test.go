package t_arrays

import (
	"reflect"
	"testing"
)

func TestArrayTechniques(t *testing.T) {
	if got := ProductExceptSelf([]int{1, 2, 3, 4}); !reflect.DeepEqual(got, []int{24, 12, 8, 6}) {
		t.Errorf("product %v", got)
	}
	if got := ProductExceptSelf([]int{-1, 1, 0, -3, 3}); !reflect.DeepEqual(got, []int{0, 0, 9, 0, 0}) {
		t.Errorf("product with zero %v", got)
	}

	for _, c := range []struct {
		in   []int
		want int
	}{{[]int{3, 4, -1, 1}, 2}, {[]int{7, 8, 9, 11, 12}, 1}, {[]int{1, 2, 0}, 3}, {[]int{1, 1, 2, 2}, 3}} {
		if got := FirstMissingPositive(c.in); got != c.want {
			t.Errorf("first missing %v = %d, want %d", c.in, got, c.want)
		}
	}

	if got := FindDuplicates([]int{4, 3, 2, 7, 8, 2, 3, 1}); !reflect.DeepEqual(got, []int{2, 3}) {
		t.Errorf("duplicates %v", got)
	}

	colors := []int{2, 0, 2, 1, 1, 0}
	SortColors(colors)
	if !reflect.DeepEqual(colors, []int{0, 0, 1, 1, 2, 2}) {
		t.Errorf("sort colors %v", colors)
	}

	for _, c := range [][2][]int{{{1, 2, 3}, {1, 3, 2}}, {{3, 2, 1}, {1, 2, 3}}, {{1, 1, 5}, {1, 5, 1}}, {{1, 3, 2}, {2, 1, 3}}} {
		p := append([]int(nil), c[0]...)
		NextPermutation(p)
		if !reflect.DeepEqual(p, c[1]) {
			t.Errorf("next permutation of %v = %v", c[0], p)
		}
	}

	r := []int{1, 2, 3, 4, 5, 6, 7}
	Rotate(r, 3)
	if !reflect.DeepEqual(r, []int{5, 6, 7, 1, 2, 3, 4}) {
		t.Errorf("rotate %v", r)
	}
	r2 := []int{-1, -100, 3, 99}
	Rotate(r2, 6)
	if !reflect.DeepEqual(r2, []int{3, 99, -1, -100}) {
		t.Errorf("rotate k>n %v", r2)
	}

	if got := CorpFlightBookings([][3]int{{1, 2, 10}, {2, 3, 20}, {2, 5, 25}}, 5); !reflect.DeepEqual(got, []int{10, 55, 45, 25, 25}) {
		t.Errorf("bookings %v", got)
	}

	for _, c := range []struct{ in, want []int }{
		{[]int{3, 2, 3}, []int{3}}, {[]int{1, 2}, []int{1, 2}}, {[]int{1, 1, 1, 3, 3, 2, 2, 2}, []int{1, 2}},
	} {
		if got := MajorityElementII(c.in); !reflect.DeepEqual(got, c.want) {
			t.Errorf("majority II %v = %v", c.in, got)
		}
	}
}
