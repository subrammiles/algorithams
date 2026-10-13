// Package stack_queue: stack and queue interview problems.
package stack_queue

import (
	"strconv"
	"strings"
)

// EvalRPN: Go's integer division already truncates toward zero, as RPN expects.
func EvalRPN(tokens []string) int {
	var stack []int
	for _, t := range tokens {
		switch t {
		case "+", "-", "*", "/":
			b, a := stack[len(stack)-1], stack[len(stack)-2]
			stack = stack[:len(stack)-2]
			switch t {
			case "+":
				stack = append(stack, a+b)
			case "-":
				stack = append(stack, a-b)
			case "*":
				stack = append(stack, a*b)
			default:
				stack = append(stack, a/b)
			}
		default:
			n, _ := strconv.Atoi(t)
			stack = append(stack, n)
		}
	}
	return stack[0]
}

// Calculate evaluates + - * / without parentheses (Basic Calculator II).
func Calculate(s string) int {
	var stack []int
	num, op := 0, byte('+')
	for i := 0; i < len(s); i++ {
		ch := s[i]
		isDigit := ch >= '0' && ch <= '9'
		if isDigit {
			num = num*10 + int(ch-'0')
		}
		if (!isDigit && ch != ' ') || i == len(s)-1 {
			switch op {
			case '+':
				stack = append(stack, num)
			case '-':
				stack = append(stack, -num)
			case '*':
				stack[len(stack)-1] *= num
			case '/':
				stack[len(stack)-1] /= num
			}
			op, num = ch, 0
		}
	}
	total := 0
	for _, x := range stack {
		total += x
	}
	return total
}

func DecodeString(s string) string {
	type frame struct {
		prev  string
		times int
	}
	var stack []frame
	var cur strings.Builder
	k := 0
	for i := 0; i < len(s); i++ {
		ch := s[i]
		switch {
		case ch >= '0' && ch <= '9':
			k = k*10 + int(ch-'0')
		case ch == '[':
			stack = append(stack, frame{cur.String(), k})
			cur.Reset()
			k = 0
		case ch == ']':
			top := stack[len(stack)-1]
			stack = stack[:len(stack)-1]
			inner := cur.String()
			cur.Reset()
			cur.WriteString(top.prev + strings.Repeat(inner, top.times))
		default:
			cur.WriteByte(ch)
		}
	}
	return cur.String()
}

func AsteroidCollision(asteroids []int) []int {
	stack := []int{}
	for _, a := range asteroids {
		alive := true
		for alive && a < 0 && len(stack) > 0 && stack[len(stack)-1] > 0 {
			top := stack[len(stack)-1]
			if top < -a {
				stack = stack[:len(stack)-1]
				continue
			}
			if top == -a {
				stack = stack[:len(stack)-1]
			}
			alive = false
		}
		if alive {
			stack = append(stack, a)
		}
	}
	return stack
}

func MinRemoveToMakeValid(s string) string {
	b := []byte(s)
	var open []int
	for i, ch := range b {
		if ch == '(' {
			open = append(open, i)
		} else if ch == ')' {
			if len(open) > 0 {
				open = open[:len(open)-1]
			} else {
				b[i] = 0 // mark for removal
			}
		}
	}
	for _, i := range open {
		b[i] = 0
	}
	return strings.ReplaceAll(string(b), "\x00", "")
}
