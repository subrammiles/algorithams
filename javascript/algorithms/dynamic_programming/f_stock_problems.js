/*
 * Stock problems as one state machine: hold' = max(hold, cash - p), cash' = max(cash, hold + p - fee).
 */
const assert = require('node:assert/strict');

function maxProfitOnce(prices) {
  let low = Infinity;
  let best = 0;
  for (const p of prices) {
    low = Math.min(low, p);
    best = Math.max(best, p - low);
  }
  return best;
}

function maxProfitUnlimited(prices, fee = 0) {
  let hold = -Infinity;
  let cash = 0;
  for (const p of prices) [hold, cash] = [Math.max(hold, cash - p), Math.max(cash, hold + p - fee)];
  return cash;
}

function maxProfitK(k, prices) {
  if (k >= prices.length / 2) return maxProfitUnlimited(prices);
  const hold = new Array(k + 1).fill(-Infinity);
  const cash = new Array(k + 1).fill(0);
  for (const p of prices) {
    for (let t = k; t >= 1; t--) {
      cash[t] = Math.max(cash[t], hold[t] + p);
      hold[t] = Math.max(hold[t], cash[t - 1] - p);
    }
  }
  return cash[k];
}

function maxProfitCooldown(prices) {
  let hold = -Infinity;
  let cash = 0;
  let cashTwoDaysAgo = 0;
  for (const p of prices) [hold, cash, cashTwoDaysAgo] = [Math.max(hold, cashTwoDaysAgo - p), Math.max(cash, hold + p), cash];
  return cash;
}

const prices = [7, 1, 5, 3, 6, 4];
assert.equal(maxProfitOnce(prices), 5);
assert.equal(maxProfitUnlimited(prices), 7);
assert.equal(maxProfitK(2, [3, 3, 5, 0, 0, 3, 1, 4]), 6);
assert.equal(maxProfitK(2, [3, 2, 6, 5, 0, 3]), 7);
assert.equal(maxProfitK(100, prices), 7);
assert.equal(maxProfitCooldown([1, 2, 3, 0, 2]), 3);
assert.equal(maxProfitUnlimited([1, 3, 2, 8, 4, 9], 2), 8);
assert.equal(maxProfitOnce([7, 6, 4, 3, 1]), 0);
console.log('all checks passed');
