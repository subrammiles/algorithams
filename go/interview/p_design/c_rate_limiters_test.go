package p_design

import (
	"sync"
	"testing"
)

func TestRateLimiters(t *testing.T) {
	b := NewTokenBucket(3, 1, 0)
	if !b.Allow(0) || !b.Allow(0) || !b.Allow(0) || b.Allow(0) || b.Allow(500) || !b.Allow(1000) {
		t.Fatal("token bucket")
	}
	w := NewSlidingWindowLog(2, 1000)
	if !w.Allow("u1", 0) || !w.Allow("u1", 100) || w.Allow("u1", 900) || !w.Allow("u2", 900) || !w.Allow("u1", 1000) {
		t.Fatal("sliding window log")
	}
}

func TestTokenBucketConcurrent(t *testing.T) {
	b := NewTokenBucket(100, 0, 0) // no refill: exactly 100 requests may pass
	var wg sync.WaitGroup
	var mu sync.Mutex
	allowed := 0
	for g := 0; g < 10; g++ {
		wg.Add(1)
		go func() {
			defer wg.Done()
			for i := 0; i < 50; i++ {
				if b.Allow(0) {
					mu.Lock()
					allowed++
					mu.Unlock()
				}
			}
		}()
	}
	wg.Wait()
	if allowed != 100 {
		t.Fatalf("allowed %d, want 100", allowed)
	}
}
