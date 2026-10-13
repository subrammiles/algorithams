package problems.stack_queue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import common.Check;

/*
 * Stack problems. Signal: "most recent unmatched thing", nesting, or undo.
 *
 *  - Evaluate Reverse Polish Notation: push numbers; an operator pops two (careful: b first).
 *    Java's integer division truncates toward zero, which is what RPN expects.
 *  - Basic Calculator II (+ - * /, no parentheses): keep the last operator; push +n / -n,
 *    apply * and / immediately to the top of the stack; answer = sum of the stack.
 *  - Decode string "3[a2[c]]": push (current string, repeat count) on '[' and pop on ']'.
 *  - Asteroid collision: only a right-moving top vs a left-moving newcomer can collide.
 *  - Minimum remove to make parentheses valid: stack of unmatched '(' indices + mark stray ')'.
 */
public class A_StackProblems {

	static int evalRPN(String[] tokens) {
		Deque<Integer> stack = new ArrayDeque<>();
		for (String t : tokens) {
			if (t.length() == 1 && "+-*/".contains(t)) {
				int b = stack.pop(), a = stack.pop();
				switch (t) {
				case "+": stack.push(a + b); break;
				case "-": stack.push(a - b); break;
				case "*": stack.push(a * b); break;
				default: stack.push(a / b);
				}
			} else {
				stack.push(Integer.parseInt(t));
			}
		}
		return stack.pop();
	}

	static int calculate(String s) {
		Deque<Integer> stack = new ArrayDeque<>();
		int num = 0;
		char op = '+';
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isDigit(c)) num = num * 10 + (c - '0');
			if ((!Character.isDigit(c) && c != ' ') || i == s.length() - 1) {
				switch (op) {
				case '+': stack.push(num); break;
				case '-': stack.push(-num); break;
				case '*': stack.push(stack.pop() * num); break;
				default: stack.push(stack.pop() / num);
				}
				op = c;
				num = 0;
			}
		}
		int total = 0;
		for (int x : stack) total += x;
		return total;
	}

	static String decodeString(String s) {
		Deque<StringBuilder> strings = new ArrayDeque<>();
		Deque<Integer> counts = new ArrayDeque<>();
		StringBuilder cur = new StringBuilder();
		int k = 0;
		for (char c : s.toCharArray()) {
			if (Character.isDigit(c)) {
				k = k * 10 + (c - '0');
			} else if (c == '[') {
				strings.push(cur);
				counts.push(k);
				cur = new StringBuilder();
				k = 0;
			} else if (c == ']') {
				String inner = cur.toString();
				cur = strings.pop();
				cur.append(inner.repeat(counts.pop()));
			} else {
				cur.append(c);
			}
		}
		return cur.toString();
	}

	static int[] asteroidCollision(int[] asteroids) {
		Deque<Integer> stack = new ArrayDeque<>();
		for (int a : asteroids) {
			boolean alive = true;
			while (alive && a < 0 && !stack.isEmpty() && stack.peek() > 0) {
				if (stack.peek() < -a) stack.pop(); // top explodes, keep checking
				else {
					if (stack.peek() == -a) stack.pop(); // both explode
					alive = false;
				}
			}
			if (alive) stack.push(a);
		}
		int[] out = new int[stack.size()];
		for (int i = out.length - 1; i >= 0; i--) out[i] = stack.pop();
		return out;
	}

	static String minRemoveToMakeValid(String s) {
		StringBuilder sb = new StringBuilder(s);
		Deque<Integer> open = new ArrayDeque<>();
		List<Integer> remove = new ArrayList<>();
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') open.push(i);
			else if (s.charAt(i) == ')') {
				if (open.isEmpty()) remove.add(i);
				else open.pop();
			}
		}
		remove.addAll(open); // unmatched '('
		remove.sort(null);
		for (int i = remove.size() - 1; i >= 0; i--) sb.deleteCharAt(remove.get(i));
		return sb.toString();
	}

	public static void main(String[] args) {
		Check.eq("rpn", evalRPN(new String[] { "2", "1", "+", "3", "*" }), 9);
		Check.eq("rpn division", evalRPN(new String[] { "4", "13", "5", "/", "+" }), 6);
		Check.eq("rpn long", evalRPN(new String[] { "10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+" }), 22);
		Check.eq("calc", calculate("3+2*2"), 7);
		Check.eq("calc div", calculate(" 3/2 "), 1);
		Check.eq("calc spaces", calculate(" 3+5 / 2 "), 5);
		Check.eq("calc minus", calculate("14-3/2"), 13);
		Check.eq("decode", decodeString("3[a]2[bc]"), "aaabcbc");
		Check.eq("decode nested", decodeString("3[a2[c]]"), "accaccacc");
		Check.eq("decode tail", decodeString("2[abc]3[cd]ef"), "abcabccdcdcdef");
		Check.eq("asteroids", asteroidCollision(new int[] { 5, 10, -5 }), new int[] { 5, 10 });
		Check.eq("asteroids equal", asteroidCollision(new int[] { 8, -8 }), new int[] {});
		Check.eq("asteroids chain", asteroidCollision(new int[] { 10, 2, -5 }), new int[] { 10 });
		Check.eq("asteroids apart", asteroidCollision(new int[] { -2, -1, 1, 2 }), new int[] { -2, -1, 1, 2 });
		Check.eq("min remove", minRemoveToMakeValid("lee(t(c)o)de)"), "lee(t(c)o)de");
		Check.eq("min remove 2", minRemoveToMakeValid("a)b(c)d"), "ab(c)d");
		Check.eq("min remove all", minRemoveToMakeValid("))(("), "");
	}
}
