const knapSack = (values, weights, target) => {
    // T[i][j] stores the maximum value of knapsack having weight
    // less than equal to j with only first i items considered.
    let T = new Array(values.length + 1);
    for(let i = 0; i < T.length; i++){
      T[i] = new Array(target+1).fill(0);
    }
  
    // do for ith item
    for (let i = 1; i <= values.length; i++) {
      // consider all weights from 0 to maximum capacity W
      
      for (let j = 0; j <= target; j++) {
        // don't include ith element if j-weights[i-1] is negative
        if (weights[i-1] > j) {
          T[i][j] = T[i-1][j];
        } else {
          // find maximum value we get by excluding or including
          // the i'th item
          T[i][j] = Math.max(T[i-1][j], T[i-1][j-weights[i-1]] + values[i-1]);
        }
      }
    }
  
    // return maximum value
    return T[values.length][target];
  }

const values = [20, 5, 10, 40, 15, 25];
const weights = [1, 2, 3, 8, 7, 4];
const target = 10;

console.log(knapSack(values, weights, target));