package h_graphs

import "testing"

func TestWordLadder(t *testing.T) {
	words := []string{"hot", "dot", "dog", "lot", "log", "cog"}
	if LadderLength("hit", "cog", words) != 5 || LadderLength("hit", "cog", words[:5]) != 0 {
		t.Fatal("word ladder")
	}
}
