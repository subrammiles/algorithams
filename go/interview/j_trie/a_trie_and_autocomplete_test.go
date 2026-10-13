package j_trie

import (
	"reflect"
	"testing"
)

func TestTrie(t *testing.T) {
	var tr Trie
	for _, w := range []string{"apple", "app", "apply", "ape", "bat"} {
		tr.Insert(w)
	}
	if !tr.Search("app") || tr.Search("ap") || !tr.StartsWith("ap") {
		t.Fatal("search")
	}
	if got := tr.Autocomplete("app", 10); !reflect.DeepEqual(got, []string{"app", "apple", "apply"}) {
		t.Errorf("autocomplete %v", got)
	}
	if got := tr.Autocomplete("a", 2); !reflect.DeepEqual(got, []string{"ape", "app"}) {
		t.Errorf("autocomplete limit %v", got)
	}
	if !tr.SearchWithDot("b.t") || tr.SearchWithDot("a..x") {
		t.Error("dot search")
	}
}
