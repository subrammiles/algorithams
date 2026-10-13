
function tradationalFactorial(number) {
    if (number == 0) {
        return 1;
    } else {
        return number * tradationalFactorial(number - 1);
    }
}

function tailFactorial(number, factorialValue) {
    if (number == 1) {
        return factorialValue;
    } else {
        return tailFactorial(number - 1, factorialValue * number);
    }
}

//Sum of n natural numbers
	
function recsum( x) {
    if (x == 1) {
        return x;
    } else {
        return x + recsum(x - 1);
    }
}

function tailrecsum( x,  running_total) {
    if (x == 0) {
        return running_total;
    } else {
        return tailrecsum(x - 1, running_total + x);
    }
}

let x = tradationalFactorial(5, 1);
let y = tailrecsum(5, 0);
console.log(y);

