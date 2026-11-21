package o_strings

import (
	"reflect"
	"testing"
)

func TestRollingHashAndManacher(t *testing.T) {
	if got := RabinKarp("abracadabra", "abra"); !reflect.DeepEqual(got, []int{0, 7}) {
		t.Errorf("rabin-karp %v", got)
	}
	if got := RabinKarp("aaaa", "aa"); !reflect.DeepEqual(got, []int{0, 1, 2}) {
		t.Errorf("rabin-karp overlap %v", got)
	}
	if got := RabinKarp("abc", "abcd"); len(got) != 0 {
		t.Errorf("rabin-karp missing %v", got)
	}

	for s, want := range map[string]string{"banana": "ana", "abcd": "", "aaaaa": "aaaa"} {
		if got := LongestDupSubstring(s); got != want {
			t.Errorf("longest dup %q = %q, want %q", s, got, want)
		}
	}

	if got := LongestPalindrome("babad"); len(got) != 3 {
		t.Errorf("manacher babad %q", got)
	}
	for s, want := range map[string]string{"cbbd": "bb", "forgeeksskeegfor": "geeksskeeg", "a": "a"} {
		if got := LongestPalindrome(s); got != want {
			t.Errorf("manacher %q = %q, want %q", s, got, want)
		}
	}
}
