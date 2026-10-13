package a_twopointers_slidingwindow

import "testing"

func TestCharacterReplacement(t *testing.T) {
	if CharacterReplacement("ABAB", 2) != 4 || CharacterReplacement("AABABBA", 1) != 4 {
		t.Fatal("character replacement")
	}
}
