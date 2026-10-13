package stack_queue

import (
	"math"
	"testing"
)

func TestQueueProblems(t *testing.T) {
	var s StackUsingQueue
	s.Push(1)
	s.Push(2)
	s.Push(3)
	if s.Top() != 3 || s.Pop() != 3 || s.Pop() != 2 || s.Empty() {
		t.Error("stack using queue")
	}
	m := NewMovingAverage(3)
	if m.Next(1) != 1 || m.Next(10) != 5.5 || math.Abs(m.Next(3)-14.0/3) > 1e-9 || m.Next(5) != 6 {
		t.Error("moving average")
	}
	var rc RecentCounter
	for i, tc := range [][2]int{{1, 1}, {100, 2}, {3001, 3}, {3002, 3}} {
		if got := rc.Ping(tc[0]); got != tc[1] {
			t.Errorf("ping %d: got %d", i, got)
		}
	}
}
