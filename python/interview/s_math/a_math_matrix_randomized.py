"""Math, matrix and randomized building blocks.

gcd (Euclid), fast modular power (pow(b, e, m) is built in), sieve of Eratosthenes,
rotate image (transpose + reverse rows), spiral order, reservoir sampling (item i kept w.p. 1/i).
"""
import random


def gcd(a, b):
    while b:
        a, b = b, a % b
    return a


def mod_pow(base, exp, mod):
    result, base = 1, base % mod
    while exp:
        if exp & 1:
            result = result * base % mod
        base = base * base % mod
        exp >>= 1
    return result


def sieve(n):
    composite = [False] * (n + 1)
    primes = []
    for i in range(2, n + 1):
        if not composite[i]:
            primes.append(i)
            for j in range(i * i, n + 1, i):
                composite[j] = True
    return primes


def rotate(m):
    n = len(m)
    for i in range(n):
        for j in range(i + 1, n):
            m[i][j], m[j][i] = m[j][i], m[i][j]
    for row in m:
        row.reverse()


def spiral_order(m):
    out = []
    top, bottom, left, right = 0, len(m) - 1, 0, len(m[0]) - 1
    while top <= bottom and left <= right:
        out += [m[top][c] for c in range(left, right + 1)]
        top += 1
        out += [m[r][right] for r in range(top, bottom + 1)]
        right -= 1
        if top <= bottom:
            out += [m[bottom][c] for c in range(right, left - 1, -1)]
            bottom -= 1
        if left <= right:
            out += [m[r][left] for r in range(bottom, top - 1, -1)]
            left += 1
    return out


def reservoir_sample(stream, rnd):
    chosen = None
    for i, x in enumerate(stream, start=1):
        if rnd.randrange(i) == 0:
            chosen = x
    return chosen


if __name__ == "__main__":
    assert gcd(84, 36) == 12
    assert mod_pow(2, 30, 1_000_000_007) == pow(2, 30, 1_000_000_007) == 73741817
    assert sieve(30) == [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]
    m = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
    rotate(m)
    assert m == [[7, 4, 1], [8, 5, 2], [9, 6, 3]]
    assert spiral_order([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]]) == [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]
    rnd = random.Random(3)
    hits = [0] * 5
    for _ in range(50_000):
        hits[reservoir_sample(range(5), rnd)] += 1
    assert all(9_000 < h < 11_000 for h in hits), hits
    print("all checks passed")
