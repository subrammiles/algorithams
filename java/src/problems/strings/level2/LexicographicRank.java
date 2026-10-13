package problems.strings.level2;


import java.io.*;
import java.util.*;

public class LexicographicRank {

	
	// Driver program to test above function
    public static void main(String[] args)
    {
    	
        String str = "string";
        System.out.println(findRank(str));
    }

    
    
	// A utility function to find factorial of n
    static int fact(int n)
    {
        return (n <= 1) ? 1 : n * fact(n - 1);
    }
 
    // A utility function to count smaller
    // characters on right of arr[low]
    static int findSmallerInRight(String str, int low,
                                  int high)
    {
        int countRight = 0, i;
 
        for (i = low + 1; i <= high; ++i)
            if (str.charAt(i) < str.charAt(low))
                ++countRight;
 
        return countRight;
    }
 
    // A function to find rank of a string in
    // all permutations of characters
    static int findRank(String str)
    {
        int len = str.length();
        int mul = fact(len);
        int rank = 1;
        int countRight;
 
        for (int i = 0; i < len; ++i) {
            mul /= len - i;
 
            // count number of chars smaller
            // than str[i] from str[i+1] to
            // str[len-1]
            countRight = findSmallerInRight(str, i, len - 1);
 
            rank += countRight * mul;
        }
 
        return rank;
    }
 
    

}
