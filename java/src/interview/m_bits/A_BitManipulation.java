package interview.m_bits;

import interview.Check;

/*
 * Bit manipulation cheat sheet:
 *   x & (x - 1)   clears the lowest set bit      -> power of two test, popcount loop
 *   x & -x        isolates the lowest set bit
 *   a ^ a = 0, a ^ 0 = a                         -> find the unique element
 *   (x >> i) & 1  reads bit i;  x | (1 << i) sets it;  x & ~(1 << i) clears it
 *   >>> is the unsigned shift in Java (>> keeps the sign)
 */
public class A_BitManipulation {

	static boolean isPowerOfTwo(int x) {
		return x > 0 && (x & (x - 1)) == 0;
	}

	/* Every element appears twice except one. */
	static int singleNumber(int[] nums) {
		int x = 0;
		for (int n : nums) x ^= n;
		return x;
	}

	/* Every element appears three times except one: count each bit modulo 3. */
	static int singleNumberThrice(int[] nums) {
		int result = 0;
		for (int bit = 0; bit < 32; bit++) {
			int count = 0;
			for (int n : nums) count += (n >> bit) & 1;
			if (count % 3 != 0) result |= 1 << bit;
		}
		return result;
	}

	/* Two elements appear once: split all numbers by any bit where the two differ. */
	static int[] twoSingleNumbers(int[] nums) {
		int xor = 0;
		for (int n : nums) xor ^= n;
		int diff = xor & -xor;
		int a = 0;
		for (int n : nums) if ((n & diff) != 0) a ^= n;
		int b = xor ^ a;
		return new int[] { Math.min(a, b), Math.max(a, b) };
	}

	/* bits[i] = number of 1 bits in i, O(n): drop the lowest bit and reuse the answer. */
	static int[] countBits(int n) {
		int[] bits = new int[n + 1];
		for (int i = 1; i <= n; i++) bits[i] = bits[i & (i - 1)] + 1;
		return bits;
	}

	static int reverseBits(int x) {
		int r = 0;
		for (int i = 0; i < 32; i++) {
			r = (r << 1) | (x & 1);
			x >>>= 1;
		}
		return r;
	}

	/* Add without + : XOR is the sum without carry, AND << 1 is the carry. */
	static int add(int a, int b) {
		while (b != 0) {
			int carry = (a & b) << 1;
			a ^= b;
			b = carry;
		}
		return a;
	}

	public static void main(String[] args) {
		Check.isTrue("power of two 64", isPowerOfTwo(64));
		Check.eq("power of two 6", isPowerOfTwo(6), false);
		Check.eq("single", singleNumber(new int[] { 4, 1, 2, 1, 2 }), 4);
		Check.eq("single thrice", singleNumberThrice(new int[] { 0, 1, 0, 1, 0, 1, -99 }), -99);
		Check.eq("two singles", twoSingleNumbers(new int[] { 1, 2, 1, 3, 2, 5 }), new int[] { 3, 5 });
		Check.eq("count bits", countBits(5), new int[] { 0, 1, 1, 2, 1, 2 });
		Check.eq("reverse bits", reverseBits(0b00000010100101000001111010011100), 0b00111001011110000010100101000000);
		Check.eq("add", add(15, -7), 8);
	}
}
