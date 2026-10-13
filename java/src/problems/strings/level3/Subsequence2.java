package problems.strings.level3;


import java.util.ArrayList;
import java.util.List;

public class Subsequence2 {
	
	
	
	// Declare a global list
    static List<String> al = new ArrayList<>();
 
   
    public static void main(String[] args)
    {
        
    	method2("abc"); // Calling a function
        
    }
   
    
    private static void method2(String str)
    {
       for(int i=0;i<str.length();i++) {
    	   for (int j=str.length();j>i;j--) {
    		  
    		   String subStr=str.substring(i,j);
    		   System.out.println(subStr);
    	   }
       }
    }
}

