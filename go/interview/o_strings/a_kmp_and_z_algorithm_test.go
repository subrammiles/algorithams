package o_strings

import (
	"reflect"
	"testing"
)

func TestPatternMatching(t *testing.T) {
	if !reflect.DeepEqual(LPS("aabaaab"), []int{0, 1, 0, 1, 2, 2, 3}) {
		t.Error("lps")
	}
	if !reflect.DeepEqual(KMPSearch("ababcabcabababd", "ababd"), []int{10}) || !reflect.DeepEqual(KMPSearch("aaaa", "aa"), []int{0, 1, 2}) {
		t.Error("kmp")
	}
	if !reflect.DeepEqual(ZSearch("ababcabcabababd", "ababd"), []int{10}) {
		t.Error("z")
	}
	if !RepeatedSubstringPattern("abcabcabc") || RepeatedSubstringPattern("aba") {
		t.Error("repeated pattern")
	}
}
