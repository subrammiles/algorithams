package q_concurrency

import (
	"strconv"
	"strings"
	"sync"
)

// ZeroEvenOdd prints 0 1 0 2 ... 0 n from three goroutines. Unbuffered channels hand the turn over
// (the Go equivalent of passing a semaphore permit).
func ZeroEvenOdd(n int) string {
	var sb strings.Builder
	zeroTurn, oddTurn, evenTurn := make(chan struct{}), make(chan struct{}), make(chan struct{})
	var wg sync.WaitGroup
	wg.Add(3)
	go func() { // zero
		defer wg.Done()
		for i := 1; i <= n; i++ {
			<-zeroTurn
			sb.WriteString("0")
			if i%2 == 1 {
				oddTurn <- struct{}{}
			} else {
				evenTurn <- struct{}{}
			}
		}
	}()
	go func() { // odd
		defer wg.Done()
		for i := 1; i <= n; i += 2 {
			<-oddTurn
			sb.WriteString(strconv.Itoa(i))
			if i < n {
				zeroTurn <- struct{}{}
			}
		}
	}()
	go func() { // even
		defer wg.Done()
		for i := 2; i <= n; i += 2 {
			<-evenTurn
			sb.WriteString(strconv.Itoa(i))
			if i < n {
				zeroTurn <- struct{}{}
			}
		}
	}()
	zeroTurn <- struct{}{} // start
	wg.Wait()
	return sb.String()
}
