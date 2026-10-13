const knapSack = (values, weights, n, target) => {
    //Base case: Negative value
    if(target < 0){
      return Number.MIN_SAFE_INTEGER;
    }
    
    //Base case: no items left or capacity becomes 0
    if(n < 0 || target === 0){
      return 0;
    }
    
    // Case 1. include current item n in knapSack (values[n]) and recur for
    // remaining items (n - 1) with decreased capacity (weight - weights[n])
    let include = values[n] + knapSack(values, weights, n - 1, target - weights[n]);
  
    // Case 2. exclude current item n from knapSack and recur for
    // remaining items (n - 1)
    let exclude = knapSack(values, weights, n - 1, target);
    
    // return maximum value we get by including or excluding current item
    return Math.max(include, exclude);
  }

const values = [20, 5, 10, 40, 15, 25];
const weights = [1, 2, 3, 8, 7, 4];
const target = 10;

console.log(knapSack(values, weights, values.length - 1, target));