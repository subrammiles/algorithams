package e_intervals

import (
	"reflect"
	"testing"
)

func TestSweepLine(t *testing.T) {
	city := [][3]int{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}}
	want := [][2]int{{2, 10}, {3, 15}, {7, 12}, {12, 0}, {15, 10}, {20, 8}, {24, 0}}
	if got := GetSkyline(city); !reflect.DeepEqual(got, want) {
		t.Errorf("skyline %v", got)
	}
	if got := GetSkyline([][3]int{{0, 2, 3}, {2, 5, 3}}); !reflect.DeepEqual(got, [][2]int{{0, 3}, {5, 0}}) {
		t.Errorf("skyline touching %v", got)
	}
	if got := GetSkyline([][3]int{{1, 2, 1}, {1, 2, 2}, {1, 2, 3}}); !reflect.DeepEqual(got, [][2]int{{1, 3}, {2, 0}}) {
		t.Errorf("skyline same start %v", got)
	}

	a := [][2]int{{0, 2}, {5, 10}, {13, 23}, {24, 25}}
	b := [][2]int{{1, 5}, {8, 12}, {15, 24}, {25, 26}}
	if got := IntervalIntersection(a, b); !reflect.DeepEqual(got, [][2]int{{1, 2}, {5, 5}, {8, 10}, {15, 23}, {24, 24}, {25, 25}}) {
		t.Errorf("intersections %v", got)
	}

	if got := EmployeeFreeTime([][][2]int{{{1, 2}, {5, 6}}, {{1, 3}}, {{4, 10}}}); !reflect.DeepEqual(got, [][2]int{{3, 4}}) {
		t.Errorf("free time %v", got)
	}
	if got := EmployeeFreeTime([][][2]int{{{1, 3}, {6, 7}}, {{2, 4}}, {{2, 5}, {9, 12}}}); !reflect.DeepEqual(got, [][2]int{{5, 6}, {7, 9}}) {
		t.Errorf("free time 2 %v", got)
	}

	trips := [][3]int{{2, 1, 5}, {3, 3, 7}}
	if CarPooling(trips, 4) || !CarPooling(trips, 5) || !CarPooling([][3]int{{3, 2, 7}, {3, 7, 9}}, 3) {
		t.Error("car pooling")
	}
}
