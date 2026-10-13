package problems.strings.level3;

public class LongestSubstringWithNonRepeatingCharacters {


    // Naive algorithm for finding the longest substring without repeating characters
    // Quadratic time complexity 
    public static String getLongestSubstringNonRepeatingCharsNaive(String str) {
        String longestSubstring = "";
        for(int i = 0; i < str.length(); i++) {
            for(int j = i; j < str.length(); j++) {
                if(hasRepeatingChars(str, i, j)) {
                    break;
                } else if(longestSubstring.length() < j-i+1){
                    longestSubstring = str.substring(i, j+1);
                }
            }
        }
        return longestSubstring;
    }
     
    private static boolean hasRepeatingChars(String str, int start, int end) {
        boolean[] isCharPresent = new boolean[256];
        for(int i = 0; i < 256; i++) {
            isCharPresent[i] = false;
        }
        for(int i = start; i <= end; i++) {
            if(isCharPresent[str.charAt(i)]) {
                return true;
            } else {
                isCharPresent[str.charAt(i)] = true;
            }
        }
        return false;
    }
     
    // Linear time algorithm for finding the longest substring without repeating characters
    public static String getLongestSubstringNonRepeatingChars(String str) {
         
        if(str == null) {
            return null;
        }
         
        int n = str.length();
        if(n < 2) {
            return str;
        }
 
        // array to store last index of string characters seen in the string, initialized to -1
        int[] charIndexes = new int[256];
        for (int i = 0; i < 256; i++) {
            charIndexes[i] = -1;
        }
         
        // Set index of first character
        charIndexes[str.charAt(0)] = 0;
         
        int currLength = 1; // Length of current non repeating substring 
        int maxLength = 1;  // Length of longest substring with non repeating characters found
        int prevIdx = 0;    // previous index of current character
        int startIdx = 0;   // Starting index of longest substring with non repeating characters found
 
        for(int i = 1; i < n; i++) {
            prevIdx = charIndexes[str.charAt(i)];
            if(prevIdx == -1 || i - currLength > prevIdx) {
                currLength++;
            } else {
                if(currLength > maxLength) {
                    maxLength = currLength;
                    startIdx = i - maxLength;
                }
                currLength = i - prevIdx;
            }
            charIndexes[str.charAt(i)] = i;
        }
         
        // Check if longest substring with non repeating characters ends at end of the string
        if(currLength > maxLength) {
            maxLength = currLength;
            startIdx = n - maxLength;
        }
         
        return str.substring(startIdx, startIdx + maxLength);
    }
 
    public static void main(String[] args) {
        String str = "ABCDABDEFGCABD";
        /*String longestSubstring = getLongestSubstringNonRepeatingCharsNaive(str);
        System.out.println("Longest substring non repeating chars by Naive method:\t\t" + longestSubstring);*/
        String longestSubstring = getLongestSubstringNonRepeatingChars(str);
        System.out.println("Longest substring non repeating chars by linear time method:\t" + longestSubstring);
 
    }

}
