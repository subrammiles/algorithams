package q_concurrency

import (
	"sync"
	"sync/atomic"
	"testing"
)

func TestRWLock(t *testing.T) {
	l := NewRWLock()
	var readers, writers, violations atomic.Int32
	counter, writes := 0, 0
	var wg sync.WaitGroup
	for g := 0; g < 8; g++ {
		wg.Add(1)
		go func() {
			defer wg.Done()
			for i := 0; i < 2000; i++ {
				if i%10 == 0 {
					l.Lock()
					if writers.Add(1) != 1 || readers.Load() != 0 {
						violations.Add(1)
					}
					counter++ // plain int: safe only because writers are exclusive
					writes++
					writers.Add(-1)
					l.Unlock()
				} else {
					l.RLock()
					readers.Add(1)
					if writers.Load() != 0 {
						violations.Add(1)
					}
					readers.Add(-1)
					l.RUnlock()
				}
			}
		}()
	}
	wg.Wait()
	if violations.Load() != 0 {
		t.Fatalf("%d reader/writer overlaps", violations.Load())
	}
	if counter != 1600 || writes != 1600 {
		t.Fatalf("counter %d writes %d, want 1600", counter, writes)
	}
}

func TestWorkerPool(t *testing.T) {
	p := NewWorkerPool(4, 16)
	var sum atomic.Int64
	var running, maxRunning atomic.Int32
	for i := 1; i <= 1000; i++ {
		v := i
		p.Submit(func() {
			now := running.Add(1)
			for {
				m := maxRunning.Load()
				if now <= m || maxRunning.CompareAndSwap(m, now) {
					break
				}
			}
			sum.Add(int64(v))
			running.Add(-1)
		})
	}
	p.Submit(func() { panic("boom") }) // must not kill a worker
	p.Submit(func() { sum.Add(1) })
	p.Shutdown()
	if sum.Load() != 500_501 {
		t.Fatalf("sum %d", sum.Load())
	}
	if maxRunning.Load() > 4 {
		t.Fatalf("ran %d at once", maxRunning.Load())
	}
}
