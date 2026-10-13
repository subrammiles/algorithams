package p_design

import "sync"

// TokenBucket refills at a steady rate up to capacity; safe for concurrent use.
type TokenBucket struct {
	mu          sync.Mutex
	capacity    float64
	refillPerMs float64
	tokens      float64
	last        int64
}

func NewTokenBucket(capacity, refillPerSecond float64, nowMs int64) *TokenBucket {
	return &TokenBucket{capacity: capacity, refillPerMs: refillPerSecond / 1000, tokens: capacity, last: nowMs}
}

func (b *TokenBucket) Allow(nowMs int64) bool {
	b.mu.Lock()
	defer b.mu.Unlock()
	b.tokens = min(b.capacity, b.tokens+float64(nowMs-b.last)*b.refillPerMs)
	b.last = nowMs
	if b.tokens >= 1 {
		b.tokens--
		return true
	}
	return false
}

// SlidingWindowLog keeps request timestamps per user: exact, O(limit) memory per user.
type SlidingWindowLog struct {
	mu       sync.Mutex
	limit    int
	windowMs int64
	logs     map[string][]int64
}

func NewSlidingWindowLog(limit int, windowMs int64) *SlidingWindowLog {
	return &SlidingWindowLog{limit: limit, windowMs: windowMs, logs: map[string][]int64{}}
}

func (w *SlidingWindowLog) Allow(user string, nowMs int64) bool {
	w.mu.Lock()
	defer w.mu.Unlock()
	log := w.logs[user]
	for len(log) > 0 && log[0] <= nowMs-w.windowMs {
		log = log[1:]
	}
	allowed := len(log) < w.limit
	if allowed {
		log = append(log, nowMs)
	}
	w.logs[user] = log
	return allowed
}
