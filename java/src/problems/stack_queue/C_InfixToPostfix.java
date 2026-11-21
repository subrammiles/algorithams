package problems.stack_queue;

import java.util.ArrayDeque;
import java.util.Deque;

import common.Check;

/*
 * Infix -> postfix (Dijkstra's shunting-yard) and postfix evaluation
 * (older versions: datastructures/stack/applications/InfixToPostfixUsingStack and
 * EvaluationOfPostfixExpression).
 *
 * Shunting-yard, one pass, O(n), with an operator stack:
 *   operand        -> output
 *   '('            -> push
 *   ')'            -> pop to output until '(' (discard the '(')
 *   operator op    -> while the top is an operator that binds at least as tightly
 *                     (higher precedence, or equal precedence and op is LEFT-associative),
 *                     pop it to output; then push op
 *   end            -> pop everything to output
 * Precedence: ^ (3, right-associative) > * / (2) > + - (1).
 * Right-associativity matters: a^b^c = a^(b^c) -> "abc^^", but a-b-c = (a-b)-c -> "ab-c-".
 *
 * Postfix evaluation: operands push; an operator pops b then a (order matters for - and /),
 * pushes a op b. Postfix needs no parentheses and no precedence rules - that is why compilers and
 * calculators convert to it (see also A_StackProblems: evalRPN, basic calculator).
 */
public class C_InfixToPostfix {

	private static int precedence(char op) {
		switch (op) {
		case '^':
			return 3;
		case '*':
		case '/':
			return 2;
		case '+':
		case '-':
			return 1;
		default:
			return -1; // not an operator (e.g. '(')
		}
	}

	static String infixToPostfix(String infix) {
		StringBuilder out = new StringBuilder();
		Deque<Character> ops = new ArrayDeque<>();
		for (char c : infix.toCharArray()) {
			if (Character.isLetterOrDigit(c)) {
				out.append(c);
			} else if (c == '(') {
				ops.push(c);
			} else if (c == ')') {
				while (ops.peek() != '(') out.append(ops.pop());
				ops.pop(); // the '('
			} else {
				while (!ops.isEmpty() && precedence(ops.peek()) > 0
						&& (precedence(ops.peek()) > precedence(c) || (precedence(ops.peek()) == precedence(c) && c != '^'))) {
					out.append(ops.pop());
				}
				ops.push(c);
			}
		}
		while (!ops.isEmpty()) out.append(ops.pop());
		return out.toString();
	}

	// single-digit operands, integer arithmetic
	static int evaluatePostfix(String postfix) {
		Deque<Integer> stack = new ArrayDeque<>();
		for (char c : postfix.toCharArray()) {
			if (Character.isDigit(c)) {
				stack.push(c - '0');
				continue;
			}
			int b = stack.pop(), a = stack.pop();
			switch (c) {
			case '+':
				stack.push(a + b);
				break;
			case '-':
				stack.push(a - b);
				break;
			case '*':
				stack.push(a * b);
				break;
			case '/':
				stack.push(a / b);
				break;
			default:
				stack.push((int) Math.pow(a, b));
			}
		}
		return stack.pop();
	}

	public static void main(String[] args) {
		Check.eq("precedence", infixToPostfix("a+b*c"), "abc*+");
		Check.eq("parentheses", infixToPostfix("(a+b)*c"), "ab+c*");
		Check.eq("left-assoc minus", infixToPostfix("a-b-c"), "ab-c-");
		Check.eq("right-assoc power", infixToPostfix("a^b^c"), "abc^^");
		Check.eq("big one", infixToPostfix("a+b*(c^d-e)^(f+g*h)-i"), "abcd^e-fgh*+^*+i-");

		Check.eq("evaluate 231*+9-", evaluatePostfix("231*+9-"), -4);
		Check.eq("infix -> postfix -> value", evaluatePostfix(infixToPostfix("(1+2)*(3+4)-2^3")), 13);
		Check.eq("power is right-assoc", evaluatePostfix(infixToPostfix("2^3^2")), 512);
	}
}
