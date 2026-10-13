package problems.strings.level2;

import java.util.HashSet;
import java.io.*;

import java.util.*;

public class AllPermutationsString {
	public static HashSet<String> getAllPermutations(String str) {
        // Create a hash set to prevent any duplicate entries
        HashSet<String> permutations = new HashSet<String>();
         
        if(str == null || str.length() == 0) {
            permutations.add("");
            return permutations;
        }
         
        char first = str.charAt(0);
        String remainingString = str.substring(1);
        HashSet<String> words = getAllPermutations(remainingString);
        for(String word: words) {
            for(int i = 0; i <= word.length(); i++) {
                String prefix = word.substring(0, i);
                String suffix = word.substring(i);
                permutations.add(prefix + first + suffix);
            }
        }
        return permutations;
    }
     
    public static void main(String[] args) {
        String str2 = "ab";
        HashSet<String> permutations = getAllPermutations(str2);
        System.out.println(permutations.toString());
        
        //
        Scanner scn = new Scanner(System.in);
        String str = scn.next();

        solution(str);
        scn.close();
    }
    
    public static void solution(String str) {
        int len = str.length();
        int total = factorial(len);

        for (int number = 0; number < total; number++) {
          StringBuilder sb = new StringBuilder(str);
          int temp = number;

          for (int divisor = len; divisor >= 1; divisor--) {
            int q = temp / divisor;
            int r = temp % divisor;

            System.out.print(sb.charAt(r));
            sb.deleteCharAt(r);
            temp = q;
          }
          System.out.println();
        }
      }

      public static int factorial(int n) {
        int fact = 1;
        for (int i = 2; i <= n; i++) {
          fact *= i;
        }
        return fact;
      }
      
      
      
      //
//  	public static void main(String[] args)
//    {
//        String str = "ABC";
//        int n = str.length();
//       permute(str, 0, n-1);
//       
//       
//    }
 
   
    private static  void permute(String str, int l, int r)
    {
    	 System.out.println("Method called " +str+" l= "+l+" r="+r);
        if (l == r)
            System.out.println(str);
        else
        {
            for (int i = l; i <= r; i++)
            {
            	 System.out.println("i= " +i+" l="+l+" r " +r);
                str = swapTwoLettersWhenPositionGiven(str,l,i);
                System.out.println("s1= " +str);
                permute(str, l+1, r);
                str = swapTwoLettersWhenPositionGiven(str,l,i);
                System.out.println("S2 = "+str);
            }
        }
    }
 
    
    public static String swapTwoLettersWhenPositionGiven(String a, int i, int j)
    {
    	//THis method seaps 2 letters in the stering
    	//swap(ABC,0,1) return BAC.
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i] ;
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }
    
    static void permute(String s , String answer)
    {  
        if (s.length() == 0)
        {
            System.out.print(answer + "  ");
            return;
        }
         
        for(int i = 0 ;i < s.length(); i++)
        {
            char ch = s.charAt(i);
            String left_substr = s.substring(0, i);
            String right_substr = s.substring(i + 1);
            String rest = left_substr + right_substr;
            permute(rest, answer + ch);
        }
    }

      

}
