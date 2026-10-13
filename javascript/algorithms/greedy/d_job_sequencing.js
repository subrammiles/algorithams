/*
 * Job sequencing with deadlines: highest profit first into the latest free slot; union-find
 * finds that slot quickly.
 */
const assert = require('node:assert/strict');

function schedule(jobs) {
  const maxDeadline = Math.max(...jobs.map((j) => j.deadline));
  const parent = Array.from({ length: maxDeadline + 1 }, (_, i) => i);
  const find = (x) => {
    while (parent[x] !== x) {
      parent[x] = parent[parent[x]];
      x = parent[x];
    }
    return x;
  };
  const slots = new Array(maxDeadline + 1).fill(null);
  for (const job of [...jobs].sort((a, b) => b.profit - a.profit)) {
    const slot = find(job.deadline);
    if (slot > 0) {
      slots[slot] = job.id;
      parent[slot] = slot - 1;
    }
  }
  return slots.filter((id) => id !== null);
}

const totalProfit = (jobs, chosen) => jobs.filter((j) => chosen.includes(j.id)).reduce((s, j) => s + j.profit, 0);

const job = (id, deadline, profit) => ({ id, deadline, profit });
const jobs = [job('a', 2, 100), job('b', 1, 19), job('c', 2, 27), job('d', 1, 25), job('e', 3, 15)];
const order = schedule(jobs);
assert.deepEqual(order, ['c', 'a', 'e']);
assert.equal(totalProfit(jobs, order), 142);
const jobs2 = [job('x', 4, 20), job('y', 1, 10), job('z', 1, 40), job('w', 1, 30)];
assert.equal(totalProfit(jobs2, schedule(jobs2)), 60);
console.log('all checks passed');
