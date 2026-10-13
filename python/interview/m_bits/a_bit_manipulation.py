"""Bit tricks: x & (x-1) clears the lowest set bit, x & -x isolates it, a ^ a = 0.
Python ints are unbounded, so 32-bit tricks need explicit masking (& 0xFFFFFFFF).
"""


def is_power_of_two(x):
    return x > 0 and x & (x - 1) == 0


def single_number(nums):
    result = 0
    for n in nums:
        result ^= n
    return result


def single_number_thrice(nums):
    """Every number appears 3 times except one: count each bit mod 3 (32-bit signed)."""
    result = 0
    for bit in range(32):
        if sum((n >> bit) & 1 for n in nums) % 3:
            result |= 1 << bit
    return result - (1 << 32) if result >= 1 << 31 else result


def two_single_numbers(nums):
    xor = single_number(nums)
    diff = xor & -xor  # any bit where the two differ
    a = single_number([n for n in nums if n & diff])
    return sorted([a, xor ^ a])


def count_bits(n):
    bits = [0] * (n + 1)
    for i in range(1, n + 1):
        bits[i] = bits[i & (i - 1)] + 1
    return bits


def reverse_bits(x):
    r = 0
    for _ in range(32):
        r, x = (r << 1) | (x & 1), x >> 1
    return r


def add(a, b):
    """Add without +, emulating 32-bit two's complement."""
    mask = 0xFFFFFFFF
    while b & mask:
        a, b = a ^ b, (a & b) << 1
    a &= mask
    return a - (1 << 32) if a >= 1 << 31 else a


if __name__ == "__main__":
    assert is_power_of_two(64) and not is_power_of_two(6)
    assert single_number([4, 1, 2, 1, 2]) == 4
    assert single_number_thrice([0, 1, 0, 1, 0, 1, -99]) == -99
    assert two_single_numbers([1, 2, 1, 3, 2, 5]) == [3, 5]
    assert count_bits(5) == [0, 1, 1, 2, 1, 2]
    assert reverse_bits(0b00000010100101000001111010011100) == 0b00111001011110000010100101000000
    assert add(15, -7) == 8
    print("all checks passed")
