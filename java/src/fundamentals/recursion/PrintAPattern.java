package fundamentals.recursion;

//Input: n = 16
//Output: 16, 11, 6, 1, -4, 1, 6, 11, 16
//
//Input: n = 10
//Output: 10, 5, 0, 5, 10
public class PrintAPattern {
	
	public static void main(String[] args) {
		printAPattern(16,16,true);
	}
	
	
	static void withoutAnyExtraVariableAndLoop(int n) {
		 // Base case (When n becomes 0 or
        // negative)
        if (n == 0 || n < 0) {
             
            System.out.print(n + " ");
             
            return;
        }
 
        // First print decreasing order
        System.out.print(n + " ");
         
        withoutAnyExtraVariableAndLoop(n - 5);
 
        // Then print increasing order
        System.out.print(n + " ");
	}
	static void  printAPattern(int maxNum,int currentNum,boolean bDecrement){
		System.out.println(currentNum);
		if(maxNum==currentNum&&!bDecrement) {
			return;
		}
		if(bDecrement) {
			if(currentNum-5>0) {
				printAPattern(maxNum,currentNum-5,true);
			}else {
				printAPattern(maxNum,currentNum-5,false);
			}
		}else {
			printAPattern(maxNum,currentNum+5,false);
		}
	}
}
