package stack_queue

import "testing"

func TestInfixToPostfix(t *testing.T) {
	for in, want := range map[string]string{
		"a+b*c":                 "abc*+",
		"(a+b)*c":               "ab+c*",
		"a-b-c":                 "ab-c-",
		"a^b^c":                 "abc^^",
		"a+b*(c^d-e)^(f+g*h)-i": "abcd^e-fgh*+^*+i-",
	} {
		if got := InfixToPostfix(in); got != want {
			t.Errorf("%s -> %s, want %s", in, got, want)
		}
	}
	if EvaluatePostfix("231*+9-") != -4 {
		t.Error("evaluate")
	}
	if EvaluatePostfix(InfixToPostfix("(1+2)*(3+4)-2^3")) != 13 || EvaluatePostfix(InfixToPostfix("2^3^2")) != 512 {
		t.Error("infix -> postfix -> value")
	}
}
