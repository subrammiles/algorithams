package stack_queue

import (
	"reflect"
	"testing"
)

func TestStackProblems(t *testing.T) {
	if EvalRPN([]string{"2", "1", "+", "3", "*"}) != 9 || EvalRPN([]string{"4", "13", "5", "/", "+"}) != 6 ||
		EvalRPN([]string{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}) != 22 {
		t.Error("rpn")
	}
	if Calculate("3+2*2") != 7 || Calculate(" 3/2 ") != 1 || Calculate(" 3+5 / 2 ") != 5 || Calculate("14-3/2") != 13 {
		t.Error("calculator")
	}
	if DecodeString("3[a]2[bc]") != "aaabcbc" || DecodeString("3[a2[c]]") != "accaccacc" || DecodeString("2[abc]3[cd]ef") != "abcabccdcdcdef" {
		t.Error("decode")
	}
	cases := map[string][2][]int{
		"basic": {{5, 10, -5}, {5, 10}}, "equal": {{8, -8}, {}}, "chain": {{10, 2, -5}, {10}}, "apart": {{-2, -1, 1, 2}, {-2, -1, 1, 2}},
	}
	for name, c := range cases {
		if got := AsteroidCollision(c[0]); !reflect.DeepEqual(got, c[1]) {
			t.Errorf("asteroids %s: %v", name, got)
		}
	}
	if MinRemoveToMakeValid("lee(t(c)o)de)") != "lee(t(c)o)de" || MinRemoveToMakeValid("a)b(c)d") != "ab(c)d" || MinRemoveToMakeValid("))((") != "" {
		t.Error("min remove")
	}
}
