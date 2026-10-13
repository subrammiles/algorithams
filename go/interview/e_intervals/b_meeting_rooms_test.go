package e_intervals

import "testing"

func TestMeetingRooms(t *testing.T) {
	for _, c := range []struct {
		in   [][2]int
		want int
	}{
		{[][2]int{{0, 30}, {5, 10}, {15, 20}}, 2},
		{[][2]int{{1, 5}, {2, 6}, {3, 7}, {7, 8}}, 3},
	} {
		if MinRoomsHeap(c.in) != c.want || MinRoomsSweep(c.in) != c.want {
			t.Errorf("%v: heap %d sweep %d want %d", c.in, MinRoomsHeap(c.in), MinRoomsSweep(c.in), c.want)
		}
	}
}
