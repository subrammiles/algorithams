"""Infix -> postfix (shunting-yard) and postfix evaluation. Full story in C_InfixToPostfix.java.

operand -> output; '(' push; ')' pop until '('; operator: pop while the top binds at least as
tightly (higher precedence, or equal and the new operator is left-associative), then push.
Precedence ^ (right-assoc) > * / > + -.
"""
PRECEDENCE = {"^": 3, "*": 2, "/": 2, "+": 1, "-": 1}


def infix_to_postfix(infix):
    out, ops = [], []
    for c in infix:
        if c.isalnum():
            out.append(c)
        elif c == "(":
            ops.append(c)
        elif c == ")":
            while ops[-1] != "(":
                out.append(ops.pop())
            ops.pop()
        else:
            while ops and ops[-1] in PRECEDENCE and (
                PRECEDENCE[ops[-1]] > PRECEDENCE[c] or (PRECEDENCE[ops[-1]] == PRECEDENCE[c] and c != "^")
            ):
                out.append(ops.pop())
            ops.append(c)
    return "".join(out + ops[::-1])


def evaluate_postfix(postfix):
    """Single-digit operands, integer arithmetic (division truncates toward zero)."""
    stack = []
    for c in postfix:
        if c.isdigit():
            stack.append(int(c))
            continue
        b, a = stack.pop(), stack.pop()  # order matters for - / ^
        stack.append({"+": a + b, "-": a - b, "*": a * b, "^": a ** b}.get(c) if c != "/" else int(a / b))
    return stack.pop()


if __name__ == "__main__":
    assert infix_to_postfix("a+b*c") == "abc*+"
    assert infix_to_postfix("(a+b)*c") == "ab+c*"
    assert infix_to_postfix("a-b-c") == "ab-c-"
    assert infix_to_postfix("a^b^c") == "abc^^"
    assert infix_to_postfix("a+b*(c^d-e)^(f+g*h)-i") == "abcd^e-fgh*+^*+i-"

    assert evaluate_postfix("231*+9-") == -4
    assert evaluate_postfix(infix_to_postfix("(1+2)*(3+4)-2^3")) == 13
    assert evaluate_postfix(infix_to_postfix("2^3^2")) == 512
    print("all checks passed")
