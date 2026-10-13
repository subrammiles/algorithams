package problems.strings.level1;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Palindrome {
	
	
	 public static int[] twoNumberSum(int[] array, int targetSum) {
	      int required[]=new int[2];
			  for(int i=0;i<array.length;i++){
					for(int j=i+1;j<array.length;j++){
						if(array[i]+array[j]==targetSum){
							required[1]=array[i];
							required[0]=array[j];
							return required;
						}
					}
				}
	    return  required;
	  }
	
	/*Check Palindrome*/
	public static boolean isPalindrom1(String str){
		//Used while loop
		int i=0;
		int j=str.length()-1;
		
		while(j>1) {
			Character c1=str.charAt(i);
			Character c2=str.charAt(j);
			if(c1 !=c2) {
				return false;
			}
			i++;
			j--;
		}
		return true;
	}
	public static boolean isPalindrom2(String str){
		//Used for loop
		int i=0;
		int j=str.length()-1;
		for(;i<str.length()/2;i++) {
			if(str.charAt(i)!=str.charAt(j-i)) {
				return false;
			}
		}
		return true;
	}
	
	public static int findLengthOfLongestPalindromicSubsequence(String s) {
		int longest=0;
		for(int i=0;i<s.length();i++) {
			for(int j=i+1;j<s.length();j++) {
				boolean isPal=isPalindrom1(s.substring(i, j));
				if(isPal) {
					int l=s.substring(i, j).length();
					if(l>longest) {
						longest=l;
					}
				}
			}
		}
		return longest;
	}
	
	public static int findLengthOfLongestPalindromicSubString(String s) {
		int longest=0;
		for(int i=0;i<s.length();i++) {
			for(int j=i;j<s.length();j++) {
				boolean isPal=isPalindrom1(s.substring(i, j+1));
				if(isPal) {
					int l=s.substring(i, j+1).length();
					if(l>longest) {
						longest=l;
					}
				}
			}
		}
		return longest;
	}
	
	
	// Function to find out the minimum number of deletions required to
    // convert a given string `X[i…j]` into a palindrome
    public static int minDeletionsToMakePal(String X, int i, int j)
    {
        // base condition
        if (i >= j) {
            return 0;
        }
 
        // if the last character of the string is the same as the first character
        if (X.charAt(i) == X.charAt(j)) {
            return minDeletionsToMakePal(X, i + 1, j - 1);
        }
 
        // otherwise, if the last character of the string is different from the
        // first character
 
        // 1. Remove the last character and recur for the remaining substring
        // 2. Remove the first character and recur for the remaining substring
 
        // return 1 (for remove operation) + minimum of the two values
 
        return 1 + Math.min(minDeletionsToMakePal(X, i, j - 1), minDeletionsToMakePal(X, i + 1, j));
    }
    
    
 // Function to check if the given string is k–palindrome or not
    public static int isKPalindrome(String X, int m, String Y, int n)
    {
    	
        // if either string is empty, remove all characters from the other string
        if (m == 0 || n == 0) {
            return n + m;
        }
 
        // ignore the last characters of both strings if they are the same
        // and recur for the remaining characters
        if (X.charAt(m - 1) == Y.charAt(n - 1)) {
            return isKPalindrome(X, m - 1, Y, n - 1);
        }
 
        // if the last character of both strings is different
 
        // remove the last character from the first string and recur
        int x = isKPalindrome(X, m - 1, Y, n);
 
        // remove the last character from the second string and recur
        int y = isKPalindrome(X, m, Y, n - 1);
 
        // return one more than the minimum of the above two operations
        return 1 + Integer.min(x, y);
    }
 
 
	

	public static void main(String[] args ) {
		boolean o1=isPalindrom1("abcdefggfedcba");
		boolean o2=isPalindrom2("abcdefggfedcba");
		int o3=findLengthOfLongestPalindromicSubString("ABBATTTTT");
		int o4=findLengthOfLongestPalindromicSubsequence("BBBBB");
		
		
		System.out.println(o3);
		System.out.println(o4);
		
		String X = "ACBCDBAA";
        int n = X.length();
 
        System.out.print("The minimum number of deletions required is " +
        		minDeletionsToMakePal(X, 0, n - 1));
        
        
        String s = "CABCBC";
        int k = 2;
 
        // get reverse of s
        String rev = new StringBuilder(s).reverse().toString();
 
        if (isKPalindrome(s, s.length(), rev, s.length()) <= 2*k) {
            System.out.println("The string is k–palindrome");
        }
        else {
            System.out.println("The string is not a k–palindrome");
        }
	}
}
