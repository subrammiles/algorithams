package j_trie

import (
	"reflect"
	"testing"
)

func TestFindWords(t *testing.T) {
	board := [][]byte{[]byte("oaan"), []byte("etae"), []byte("ihkr"), []byte("iflv")}
	if got := FindWords(board, []string{"oath", "pea", "eat", "rain"}); !reflect.DeepEqual(got, []string{"eat", "oath"}) {
		t.Fatalf("got %v", got)
	}
}
