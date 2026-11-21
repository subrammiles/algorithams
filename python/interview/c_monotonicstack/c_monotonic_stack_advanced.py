"""Harder monotonic-stack problems (full explanations in the Java version).

sum_subarray_mins          contribution: a[i] * (#starts) * (#ends) where a[i] is the minimum.
                           previous STRICTLY smaller on the left, next smaller-OR-EQUAL on the right
                           so equal minimums are counted exactly once.
remove_duplicate_letters   greedy increasing stack; pop a bigger top if it appears again later.
find132pattern             scan right-to-left; popped values are candidate "2"s; keep the largest.
"""

MOD = 1_000_000_007


def sum_subarray_mins(a):
    n = len(a)
    left, right, stack = [0] * n, [0] * n, []
    for i in range(n):
        while stack and a[stack[-1]] >= a[i]:
            stack.pop()
        left[i] = i - stack[-1] if stack else i + 1
        stack.append(i)
    stack = []
    for i in range(n - 1, -1, -1):
        while stack and a[stack[-1]] > a[i]:
            stack.pop()
        right[i] = stack[-1] - i if stack else n - i
        stack.append(i)
    return sum(a[i] * left[i] * right[i] for i in range(n)) % MOD


def remove_duplicate_letters(s):
    last = {c: i for i, c in enumerate(s)}
    stack, in_stack = [], set()
    for i, c in enumerate(s):
        if c in in_stack:
            continue
        while stack and stack[-1] > c and last[stack[-1]] > i:
            in_stack.discard(stack.pop())
        stack.append(c)
        in_stack.add(c)
    return "".join(stack)


def find132pattern(a):
    stack, third = [], float("-inf")
    for x in reversed(a):
        if x < third:
            return True
        while stack and stack[-1] < x:
            third = stack.pop()
        stack.append(x)
    return False


if __name__ == "__main__":
    assert sum_subarray_mins([3, 1, 2, 4]) == 17
    assert sum_subarray_mins([11, 81, 94, 43, 3]) == 444
    assert sum_subarray_mins([2, 2, 2]) == 12

    assert remove_duplicate_letters("bcabc") == "abc"
    assert remove_duplicate_letters("cbacdcbc") == "acdb"

    assert find132pattern([3, 1, 4, 2])
    assert not find132pattern([1, 2, 3, 4])
    assert find132pattern([-1, 3, 2, 0])
    print("all checks passed")
