package d_heaps

import "testing"

func TestMedianFinder(t *testing.T) {
	var m MedianFinder
	m.AddNum(1)
	m.AddNum(2)
	if m.FindMedian() != 1.5 {
		t.Fatal("1,2")
	}
	m.AddNum(3)
	if m.FindMedian() != 2 {
		t.Fatal("1,2,3")
	}
	m.AddNum(-5)
	m.AddNum(10)
	if m.FindMedian() != 2 {
		t.Fatal("five numbers")
	}
}
