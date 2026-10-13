

let currentNumber=0;

function method1(){
   if(currentNumber<=20){
     console.log(currentNumber);
     currentNumber++;
     method2();
   }else{
       return;
   }
}

function method2(){
    if(currentNumber<=20){
        console.log(currentNumber);
     currentNumber++;
method1();
    }else{
        return;
    }

}

method1();
