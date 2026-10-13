package recursion

import (
	"reflect"
	"testing"
)

func TestClassicRecursion(t *testing.T) {
	var moves []string
	Hanoi(2, "A", "C", "B", &moves)
	if !reflect.DeepEqual(moves, []string{"A->B", "A->C", "B->C"}) {
		t.Errorf("hanoi %v", moves)
	}
	moves = nil
	Hanoi(10, "A", "C", "B", &moves)
	if len(moves) != 1023 {
		t.Error("hanoi 10")
	}
	if Josephus(7, 3) != 4 || Josephus(5, 2) != 3 {
		t.Error("josephus")
	}
	if got := BinaryStringsNoConsecutiveOnes(3); !reflect.DeepEqual(got, []string{"000", "001", "010", "100", "101"}) {
		t.Errorf("binary strings %v", got)
	}
	if got := LetterCombinations("23"); !reflect.DeepEqual(got, []string{"ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"}) {
		t.Errorf("letters %v", got)
	}
	if len(LetterCombinations("")) != 0 {
		t.Error("empty digits")
	}
	if got := GenerateParentheses(3); !reflect.DeepEqual(got, []string{"((()))", "(()())", "(())()", "()(())", "()()()"}) {
		t.Errorf("parentheses %v", got)
	}
}
