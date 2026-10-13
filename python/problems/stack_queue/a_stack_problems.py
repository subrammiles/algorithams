"""Stack problems: evaluate RPN (truncate toward zero!), basic calculator II, decode string,
asteroid collision, minimum remove to make parentheses valid.
"""


def eval_rpn(tokens):
    stack = []
    for t in tokens:
        if t in "+-*/" and len(t) == 1:
            b, a = stack.pop(), stack.pop()
            if t == "+":
                stack.append(a + b)
            elif t == "-":
                stack.append(a - b)
            elif t == "*":
                stack.append(a * b)
            else:
                stack.append(int(a / b))  # Python's // floors; RPN expects truncation
        else:
            stack.append(int(t))
    return stack.pop()


def calculate(s):
    stack, num, op = [], 0, "+"
    for i, ch in enumerate(s):
        if ch.isdigit():
            num = num * 10 + int(ch)
        if (not ch.isdigit() and ch != " ") or i == len(s) - 1:
            if op == "+":
                stack.append(num)
            elif op == "-":
                stack.append(-num)
            elif op == "*":
                stack.append(stack.pop() * num)
            else:
                stack.append(int(stack.pop() / num))
            op, num = ch, 0
    return sum(stack)


def decode_string(s):
    stack, cur, k = [], "", 0
    for ch in s:
        if ch.isdigit():
            k = k * 10 + int(ch)
        elif ch == "[":
            stack.append((cur, k))
            cur, k = "", 0
        elif ch == "]":
            prev, times = stack.pop()
            cur = prev + cur * times
        else:
            cur += ch
    return cur


def asteroid_collision(asteroids):
    stack = []
    for a in asteroids:
        alive = True
        while alive and a < 0 and stack and stack[-1] > 0:
            if stack[-1] < -a:
                stack.pop()
            else:
                if stack[-1] == -a:
                    stack.pop()
                alive = False
        if alive:
            stack.append(a)
    return stack


def min_remove_to_make_valid(s):
    chars, open_ = list(s), []
    for i, ch in enumerate(chars):
        if ch == "(":
            open_.append(i)
        elif ch == ")":
            if open_:
                open_.pop()
            else:
                chars[i] = ""
    for i in open_:
        chars[i] = ""
    return "".join(chars)


if __name__ == "__main__":
    assert eval_rpn(["2", "1", "+", "3", "*"]) == 9 and eval_rpn(["4", "13", "5", "/", "+"]) == 6
    assert eval_rpn(["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]) == 22
    assert calculate("3+2*2") == 7 and calculate(" 3/2 ") == 1 and calculate(" 3+5 / 2 ") == 5 and calculate("14-3/2") == 13
    assert decode_string("3[a]2[bc]") == "aaabcbc" and decode_string("3[a2[c]]") == "accaccacc"
    assert decode_string("2[abc]3[cd]ef") == "abcabccdcdcdef"
    assert asteroid_collision([5, 10, -5]) == [5, 10] and asteroid_collision([8, -8]) == []
    assert asteroid_collision([10, 2, -5]) == [10] and asteroid_collision([-2, -1, 1, 2]) == [-2, -1, 1, 2]
    assert min_remove_to_make_valid("lee(t(c)o)de)") == "lee(t(c)o)de"
    assert min_remove_to_make_valid("a)b(c)d") == "ab(c)d" and min_remove_to_make_valid("))((") == ""
    print("all checks passed")
