
/*
Given a text txt[0..n-1] and a pattern pat[0..m-1], write a function search(char pat[], char txt[]) that prints all occurrences of pat[] and its permutations (or anagrams) in txt[]. You may assume that n > m. 
Expected time complexity is O(n)
Examples: 

1) Input:  txt[] = "BACDGABCDA"  pat[] = "ABCD"
   Output:   Found at Index 0
             Found at Index 5
             Found at Index 6
2) Input: txt[] =  "AAABABAA" pat[] = "AABA"
   Output:   Found at Index 0
             Found at Index 1
             Found at Index 4
*/             
  

package problems.strings.level4;


public class AnagramSubStringSearch {
	 static final int MAX = 256;
     
	    // This function returns true if contents
	    // of arr1[] and arr2[] are same, otherwise
	    // false.
	    static boolean compare(char arr1[], char arr2[])
	    {
	        for (int i = 0; i < MAX; i++)
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
	        char[] countP = new char[MAX];
	        char[] countTW = new char[MAX];
	        for (int i = 0; i < M; i++)
	        {
	            (countP[pat.charAt(i)])++;
	            (countTW[txt.charAt(i)])++;
	        }
	 
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
	 
	    /* Driver program to test above function */
	    public static void main(String args[])
	    {
	        String txt = "BACDGABCDA";
	        String pat = "ABCD";
	        search(pat, txt);
	    }
}
