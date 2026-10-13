package stack_queue

// StackUsingQueue rotates the queue after each push so the newest item is at the front.
type StackUsingQueue struct{ q []int }

func (s *StackUsingQueue) Push(x int) {
	s.q = append(s.q, x)
	for i := 1; i < len(s.q); i++ {
		s.q = append(s.q[1:], s.q[0])
	}
}

func (s *StackUsingQueue) Pop() int {
	x := s.q[0]
	s.q = s.q[1:]
	return x
}

func (s *StackUsingQueue) Top() int    { return s.q[0] }
func (s *StackUsingQueue) Empty() bool { return len(s.q) == 0 }

// MovingAverage keeps a window queue and a running sum: O(1) per value.
type MovingAverage struct {
	size   int
	window []int
	sum    int
}

func NewMovingAverage(size int) *MovingAverage { return &MovingAverage{size: size} }

func (m *MovingAverage) Next(val int) float64 {
	m.window = append(m.window, val)
	m.sum += val
	if len(m.window) > m.size {
		m.sum -= m.window[0]
		m.window = m.window[1:]
	}
	return float64(m.sum) / float64(len(m.window))
}

// RecentCounter counts pings in the last 3000 ms.
type RecentCounter struct{ pings []int }

func (r *RecentCounter) Ping(t int) int {
	r.pings = append(r.pings, t)
	for r.pings[0] < t-3000 {
		r.pings = r.pings[1:]
	}
	return len(r.pings)
}
