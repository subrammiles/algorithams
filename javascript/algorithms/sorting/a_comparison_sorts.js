/*
 * All comparison sorts (complexity/stability table in the Java version). Remember: Array.sort()
 * without a comparator sorts as STRINGS ([10, 9, 1] -> [1, 10, 9]); V8 uses TimSort (stable).
 */
const assert = require('node:assert/strict');

const swap = (a, i, j) => ([a[i], a[j]] = [a[j], a[i]]);

function bubbleSort(input) {
  const a = [...input];
  for (let end = a.length - 1; end > 0; end--) {
    let swapped = false;
    for (let i = 0; i < end; i++) {
      if (a[i] > a[i + 1]) {
        swap(a, i, i + 1);
        swapped = true;
      }
    }
    if (!swapped) break;
  }
  return a;
}

function selectionSort(input) {
  const a = [...input];
  for (let i = 0; i < a.length - 1; i++) {
    let min = i;
    for (let j = i + 1; j < a.length; j++) if (a[j] < a[min]) min = j;
    swap(a, i, min);
  }
  return a;
}

function insertionSort(input) {
  const a = [...input];
  for (let i = 1; i < a.length; i++) {
    const x = a[i];
    let j = i - 1;
    for (; j >= 0 && a[j] > x; j--) a[j + 1] = a[j];
    a[j + 1] = x;
  }
  return a;
}

function shellSort(input) {
  const a = [...input];
  let gap = 1;
  while (gap < a.length / 3) gap = 3 * gap + 1;
  for (; gap >= 1; gap = Math.floor(gap / 3)) {
    for (let i = gap; i < a.length; i++) {
      const x = a[i];
      let j = i;
      for (; j >= gap && a[j - gap] > x; j -= gap) a[j] = a[j - gap];
      a[j] = x;
    }
  }
  return a;
}

function mergeSort(a) {
  if (a.length <= 1) return [...a];
  const mid = a.length >> 1;
  const left = mergeSort(a.slice(0, mid));
  const right = mergeSort(a.slice(mid));
  const out = [];
  let i = 0;
  let j = 0;
  while (i < left.length && j < right.length) out.push(left[i] <= right[j] ? left[i++] : right[j++]);
  return [...out, ...left.slice(i), ...right.slice(j)];
}

function quickSort(input) {
  const a = [...input];
  const sort = (lo, hi) => {
    if (lo >= hi) return;
    swap(a, lo + Math.floor(Math.random() * (hi - lo + 1)), hi);
    let store = lo;
    for (let i = lo; i < hi; i++) if (a[i] < a[hi]) swap(a, i, store++);
    swap(a, store, hi);
    sort(lo, store - 1);
    sort(store + 1, hi);
  };
  sort(0, a.length - 1);
  return a;
}

function quickSort3Way(input) {
  const a = [...input];
  const sort = (lo, hi) => {
    if (lo >= hi) return;
    const pivot = a[lo + Math.floor(Math.random() * (hi - lo + 1))];
    let lt = lo;
    let i = lo;
    let gt = hi;
    while (i <= gt) {
      if (a[i] < pivot) swap(a, lt++, i++);
      else if (a[i] > pivot) swap(a, i, gt--);
      else i++;
    }
    sort(lo, lt - 1);
    sort(gt + 1, hi);
  };
  sort(0, a.length - 1);
  return a;
}

function heapSort(input) {
  const a = [...input];
  const siftDown = (i, n) => {
    for (;;) {
      const l = 2 * i + 1;
      const r = l + 1;
      let largest = i;
      if (l < n && a[l] > a[largest]) largest = l;
      if (r < n && a[r] > a[largest]) largest = r;
      if (largest === i) return;
      swap(a, i, largest);
      i = largest;
    }
  };
  for (let i = (a.length >> 1) - 1; i >= 0; i--) siftDown(i, a.length);
  for (let end = a.length - 1; end > 0; end--) {
    swap(a, 0, end);
    siftDown(0, end);
  }
  return a;
}

const randomInts = (n, lo, hi) => Array.from({ length: n }, () => lo + Math.floor(Math.random() * (hi - lo)));
const inputs = [[], [1], [5, 4, 3, 2, 1], [1, 2, 3, 4, 5], [3, 1, 3, 1, 3, 1], randomInts(1000, -500, 500), randomInts(1000, 0, 3)];
const sorts = [bubbleSort, selectionSort, insertionSort, shellSort, mergeSort, quickSort, quickSort3Way, heapSort];
for (const data of inputs) {
  const expected = [...data].sort((x, y) => x - y);
  for (const sort of sorts) assert.deepEqual(sort(data), expected, sort.name);
}
assert.deepEqual([10, 9, 1].sort(), [1, 10, 9]); // the default-sort trap
console.log('all checks passed');
