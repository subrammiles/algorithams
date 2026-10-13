const knapSack = (values, weights, n, target, lookup) => {
    // base case: Negative capacity
    if (target < 0) {
      return Number.MIN_SAFE_INTEGER;
    }
  
    // base case: no items left or capacity becomes 0
    if (n < 0 || target == 0) {
      return 0;
    }
  
    // construct an unique map key from dynamic elements of the input
    const key = `${n}|${target}`;
  
    // if sub-problem is seen for the first time, solve it and
    // store its result in a map
    if (!lookup.has(key))
    {
      // Case 1. include current item n in knapSack (values[n]) & recur
      // for remaining items (n-1) with decreased capacity (target - weights[n])
      let include = values[n] + knapSack(values, weights, n - 1, target - weights[n], lookup);
  
      // Case 2. exclude current item n from knapSack and recur for
      // remaining items (n-1)
      let exclude = knapSack(values, weights, n - 1, target, lookup);
  
      // assign max value we get by including or excluding current item
      lookup.set(key, Math.max(include, exclude));
    }
  
    // return solution to current sub-problem
    return lookup.get(key);
  }

const values = [20, 5, 10, 40, 15, 25];
const weights = [1, 2, 3, 8, 7, 4];
const target = 10;
let lookup = new Map();

console.log(knapSack(values, weights, values.length - 1, target, lookup));
