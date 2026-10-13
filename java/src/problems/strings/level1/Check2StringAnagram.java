package problems.strings.level1;

import java.io.*;
import java.util.*;
public class Check2StringAnagram {
	static int NO_OF_CHARS = 256;
	
	
	
	private static boolean isAnagram1(char[] s1,char[] s2){
		/*Method to Check 2 strings are anagrams*/
		Arrays.sort(s1);
		Arrays.sort(s2);
		if(s1.length !=s2.length){
			return false;
		}
		for(int i=0;i<s1.length;i++){
			if(s1[i] != s2[i]){
				return false;
			}
		}
		return true;
	}
	
	private static boolean isAnagram2(char str1[],char str2[]) {
		/*Method to Check 2 strings are anagrams*/
		int count1[] = new int[NO_OF_CHARS];
        Arrays.fill(count1, 0);
        int count2[] = new int[NO_OF_CHARS];
        Arrays.fill(count2, 0);
        int i;
        
        System.out.println(count1.toString());
        System.out.println(count2.toString());
        // For each character in input strings,
        // increment count in the corresponding
        // count array
        for (i = 0; i < str1.length && i < str2.length;
             i++) {
            count1[str1[i]]++;
            count2[str2[i]]++;
        }
        
        System.out.println(Arrays.toString(count1));
        System.out.println(Arrays.toString(count2));
        // If both strings are of different length.
        // Removing this condition will make the program
        // fail for strings like "aaca" and "aca"
        if (str1.length != str2.length)
            return false;
 
        // Compare count arrays
        for (i = 0; i < NO_OF_CHARS; i++)
            if (count1[i] != count2[i])
                return false;
 
        return true;
		
	}
	

	static boolean isAnagram3(char[] str1,char[] str2)
	{
		/*Method to Check 2 strings are anagrams*/
	    // Create a count array and initialize
	    // all values as 0
	    int[] count = new int[NO_OF_CHARS];
	    int i;
	 
	    // For each character in input strings,
	    // increment count in the corresponding
	    // count array
	    for(i = 0; i < str1.length; i++)
	    {
	        count[str1[i]]++;
	        count[str2[i]]--;
	    }
	 
	    // If both strings are of different
	    // length. Removing this condition
	    // will make the program fail for
	    // strings like "aaca" and "aca"
	    if (str1.length != str2.length)
	        return false;
	 
	    // See if there is any non-zero
	    // value in count array
	    for(i = 0; i < NO_OF_CHARS; i++)
	        if (count[i] != 0)
	        {
	            return false;
	        }
	    return true;
	}
	
	/*Anagram Substring Search (Or Search for all permutations)*/
	
	// This function returns true if contents
    // of arr1[] and arr2[] are same, otherwise
    // false.
    static boolean compare(int arr1[], int arr2[])
    {
        for (int i = 0; i < NO_OF_CHARS; i++)
            if (arr1[i] != arr2[i])
                return false;
        return true;
    }
 
    // This function search for all permutations
    // of pat[] in txt[]
    static void search(String pat, String txt)
    {
        int M = pat.length();
        int N = txt.length();
 
        // countP[]:  Store count of all
        // characters of pattern
        // countTW[]: Store count of current
        // window of text
        int[] countP = new int[NO_OF_CHARS];
        int[] countTW = new int[NO_OF_CHARS];
        System.out.println(Arrays.toString(countP));
        System.out.println(Arrays.toString(countTW));
        for (int i = 0; i < M; i++)
        {
            (countP[pat.charAt(i)])++;
            (countTW[txt.charAt(i)])++;
        }
        
        System.out.println(Arrays.toString(countP));
        System.out.println(Arrays.toString(countTW));
 
        // Traverse through remaining characters
        // of pattern
        for (int i = M; i < N; i++)
        {
            // Compare counts of current window
            // of text with counts of pattern[]
            if (compare(countP, countTW))
                System.out.println("Found at Index " +
                                          (i - M));
             
            // Add current character to current
            // window
            (countTW[txt.charAt(i)])++;
 
            // Remove the first character of previous
            // window
            countTW[txt.charAt(i-M)]--;
        }
 
        // Check for the last window in text
        if (compare(countP, countTW))
            System.out.println("Found at Index " +
                                       (N - M));
    }
	
	
	
	
	
	public static void main(String[] args) {
	
	  char s1[]= ("app").toCharArray();
	  char s2[]= ("patp").toCharArray();
	  
	  //System.out.println(isAnagram3(s1,s2));
	  search("ABCD","BACDGABCDA");
	  
	}

}


