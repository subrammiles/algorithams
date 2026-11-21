package stack_queue

import "strings"

// Infix -> postfix (shunting-yard) and postfix evaluation (full story in C_InfixToPostfix.java).
// operand -> output; '(' push; ')' pop until '('; operator: pop while the top binds at least as
// tightly (higher precedence, or equal and the new operator is left-associative), then push.

var precedence = map[byte]int{'^': 3, '*': 2, '/': 2, '+': 1, '-': 1}

func isAlnum(c byte) bool {
	return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')
}

func InfixToPostfix(infix string) string {
	var out strings.Builder
	var ops []byte
	for i := 0; i < len(infix); i++ {
		c := infix[i]
		switch {
		case isAlnum(c):
			out.WriteByte(c)
		case c == '(':
			ops = append(ops, c)
		case c == ')':
			for ops[len(ops)-1] != '(' {
				out.WriteByte(ops[len(ops)-1])
				ops = ops[:len(ops)-1]
			}
			ops = ops[:len(ops)-1]
		default:
			for len(ops) > 0 {
				top, isOp := precedence[ops[len(ops)-1]]
				if !isOp || !(top > precedence[c] || (top == precedence[c] && c != '^')) {
					break
				}
				out.WriteByte(ops[len(ops)-1])
				ops = ops[:len(ops)-1]
			}
			ops = append(ops, c)
		}
	}
	for i := len(ops) - 1; i >= 0; i-- {
		out.WriteByte(ops[i])
	}
	return out.String()
}

// EvaluatePostfix: single-digit operands, integer arithmetic.
func EvaluatePostfix(postfix string) int {
	var stack []int
	for i := 0; i < len(postfix); i++ {
		c := postfix[i]
		if c >= '0' && c <= '9' {
			stack = append(stack, int(c-'0'))
			continue
		}
		a, b := stack[len(stack)-2], stack[len(stack)-1]
		stack = stack[:len(stack)-2]
		var r int
		switch c {
		case '+':
			r = a + b
		case '-':
			r = a - b
		case '*':
			r = a * b
		case '/':
			r = a / b
		case '^':
			r = 1
			for k := 0; k < b; k++ {
				r *= a
			}
		}
		stack = append(stack, r)
	}
	return stack[0]
}
