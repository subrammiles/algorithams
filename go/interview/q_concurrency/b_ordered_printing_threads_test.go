package q_concurrency

import "testing"

func TestZeroEvenOdd(t *testing.T) {
	for i := 0; i < 50; i++ { // repeat to shake out races
		if got := ZeroEvenOdd(5); got != "0102030405" {
			t.Fatalf("got %s", got)
		}
	}
	if got := ZeroEvenOdd(2); got != "0102" {
		t.Fatalf("got %s", got)
	}
}
