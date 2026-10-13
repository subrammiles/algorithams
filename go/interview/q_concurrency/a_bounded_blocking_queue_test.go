package q_concurrency

import (
	"sync"
	"sync/atomic"
	"testing"
	"time"
)

func TestBoundedBlockingQueue(t *testing.T) {
	q := NewBoundedBlockingQueue[int](5)
	const producers, perProducer = 4, 10000
	var consumed atomic.Int64
	var wg sync.WaitGroup
	for p := 0; p < producers; p++ {
		wg.Add(2)
		go func() {
			defer wg.Done()
			for i := 1; i <= perProducer; i++ {
				q.Put(i)
			}
		}()
		go func() {
			defer wg.Done()
			for i := 0; i < perProducer; i++ {
				consumed.Add(int64(q.Take()))
			}
		}()
	}
	done := make(chan struct{})
	go func() { wg.Wait(); close(done) }()
	select {
	case <-done:
	case <-time.After(30 * time.Second):
		t.Fatal("deadlock")
	}
	if want := int64(producers * perProducer * (perProducer + 1) / 2); consumed.Load() != want || q.Len() != 0 {
		t.Fatalf("consumed %d want %d, left %d", consumed.Load(), want, q.Len())
	}
}
