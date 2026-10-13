// Package q_concurrency: coordination problems with goroutines.
//
// Idiomatic Go: a buffered channel IS a bounded blocking queue (make(chan T, n)).
// BoundedBlockingQueue below builds one from a mutex + two condition variables, which is
// what the interview question is really testing.
package q_concurrency

import "sync"

type BoundedBlockingQueue[T any] struct {
	mu                sync.Mutex
	notFull, notEmpty *sync.Cond
	items             []T
	capacity          int
}

func NewBoundedBlockingQueue[T any](capacity int) *BoundedBlockingQueue[T] {
	q := &BoundedBlockingQueue[T]{capacity: capacity}
	q.notFull = sync.NewCond(&q.mu)
	q.notEmpty = sync.NewCond(&q.mu)
	return q
}

func (q *BoundedBlockingQueue[T]) Put(item T) {
	q.mu.Lock()
	defer q.mu.Unlock()
	for len(q.items) == q.capacity { // always wait in a loop
		q.notFull.Wait()
	}
	q.items = append(q.items, item)
	q.notEmpty.Signal()
}

func (q *BoundedBlockingQueue[T]) Take() T {
	q.mu.Lock()
	defer q.mu.Unlock()
	for len(q.items) == 0 {
		q.notEmpty.Wait()
	}
	item := q.items[0]
	q.items = q.items[1:]
	q.notFull.Signal()
	return item
}

func (q *BoundedBlockingQueue[T]) Len() int {
	q.mu.Lock()
	defer q.mu.Unlock()
	return len(q.items)
}
