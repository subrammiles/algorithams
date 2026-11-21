package q_concurrency

import "sync"

// RWLock: many readers OR one writer, with writer preference so readers cannot starve writers
// (full explanations in the Java version). Built from a Mutex + Cond to show the mechanics;
// in real code use sync.RWMutex (which is also writer-preferring).
type RWLock struct {
	mu             sync.Mutex
	cond           *sync.Cond
	readers        int
	waitingWriters int
	writing        bool
}

func NewRWLock() *RWLock {
	l := &RWLock{}
	l.cond = sync.NewCond(&l.mu)
	return l
}

func (l *RWLock) RLock() {
	l.mu.Lock()
	for l.writing || l.waitingWriters > 0 {
		l.cond.Wait()
	}
	l.readers++
	l.mu.Unlock()
}

func (l *RWLock) RUnlock() {
	l.mu.Lock()
	l.readers--
	if l.readers == 0 {
		l.cond.Broadcast()
	}
	l.mu.Unlock()
}

func (l *RWLock) Lock() {
	l.mu.Lock()
	l.waitingWriters++
	for l.writing || l.readers > 0 {
		l.cond.Wait()
	}
	l.waitingWriters--
	l.writing = true
	l.mu.Unlock()
}

func (l *RWLock) Unlock() {
	l.mu.Lock()
	l.writing = false
	l.cond.Broadcast()
	l.mu.Unlock()
}

// WorkerPool: n goroutines ranging over a jobs channel. Shutdown closes the channel (workers finish
// the queued jobs, then exit) and waits. A panicking job is recovered so it cannot kill its worker.
// Idiomatic alternatives: errgroup.Group with SetLimit, or a semaphore channel.
type WorkerPool struct {
	jobs chan func()
	wg   sync.WaitGroup
}

func NewWorkerPool(workers, queueSize int) *WorkerPool {
	p := &WorkerPool{jobs: make(chan func(), queueSize)}
	p.wg.Add(workers)
	for i := 0; i < workers; i++ {
		go func() {
			defer p.wg.Done()
			for job := range p.jobs {
				runSafely(job)
			}
		}()
	}
	return p
}

func runSafely(job func()) {
	defer func() { _ = recover() }()
	job()
}

// Submit blocks when the queue is full (back-pressure). Submitting after Shutdown panics,
// like sending on any closed channel.
func (p *WorkerPool) Submit(job func()) { p.jobs <- job }

func (p *WorkerPool) Shutdown() {
	close(p.jobs)
	p.wg.Wait()
}
