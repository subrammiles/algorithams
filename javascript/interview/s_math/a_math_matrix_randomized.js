/*
 * gcd, modular power (BigInt avoids precision loss above 2^53), sieve, rotate image, spiral order,
 * reservoir sampling.
 */
const assert = require('node:assert/strict');

const gcd = (a, b) => (b === 0 ? a : gcd(b, a % b));

function modPow(base, exp, mod) {
  let [b, e, m, result] = [BigInt(base), BigInt(exp), BigInt(mod), 1n];
  b %= m;
  while (e > 0n) {
    if (e & 1n) result = (result * b) % m;
    b = (b * b) % m;
    e >>= 1n;
  }
  return Number(result);
}

function sieve(n) {
  const composite = new Uint8Array(n + 1);
  const primes = [];
  for (let i = 2; i <= n; i++) {
    if (composite[i]) continue;
    primes.push(i);
    for (let j = i * i; j <= n; j += i) composite[j] = 1;
  }
  return primes;
}

function rotate(m) {
  const n = m.length;
  for (let i = 0; i < n; i++) for (let j = i + 1; j < n; j++) [m[i][j], m[j][i]] = [m[j][i], m[i][j]];
  m.forEach((row) => row.reverse());
}

function spiralOrder(m) {
  const out = [];
  let [top, bottom, left, right] = [0, m.length - 1, 0, m[0].length - 1];
  while (top <= bottom && left <= right) {
    for (let c = left; c <= right; c++) out.push(m[top][c]);
    top++;
    for (let r = top; r <= bottom; r++) out.push(m[r][right]);
    right--;
    if (top <= bottom) {
      for (let c = right; c >= left; c--) out.push(m[bottom][c]);
      bottom--;
    }
    if (left <= right) {
      for (let r = bottom; r >= top; r--) out.push(m[r][left]);
      left++;
    }
  }
  return out;
}

function reservoirSample(stream) {
  let chosen;
  let i = 0;
  for (const x of stream) if (Math.floor(Math.random() * ++i) === 0) chosen = x;
  return chosen;
}

assert.equal(gcd(84, 36), 12);
assert.equal(modPow(2, 30, 1_000_000_007), 73741817);
assert.deepEqual(sieve(30), [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]);
const m = [[1, 2, 3], [4, 5, 6], [7, 8, 9]];
rotate(m);
assert.deepEqual(m, [[7, 4, 1], [8, 5, 2], [9, 6, 3]]);
assert.deepEqual(spiralOrder([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]]), [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]);
const hits = [0, 0, 0, 0, 0];
for (let t = 0; t < 50_000; t++) hits[reservoirSample([0, 1, 2, 3, 4])]++;
assert.ok(hits.every((h) => h > 9000 && h < 11000), String(hits));
console.log('all checks passed');
