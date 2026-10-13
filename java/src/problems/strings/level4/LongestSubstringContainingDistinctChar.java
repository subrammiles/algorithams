package problems.strings.level4;


//Given a string, find the longest substring containing distinct characters.


/*
 
  Input:  findlongestsubstring
 
Output: The longest substring with all distinct characters is dlongest or ubstring
 
 
Input:  longestsubstr
 
Output: The longest substring with all distinct characters is longest
 
 
Input:  abbcdafeegh
 
Output: The longest substring with all distinct characters is bcdafe
 
 
Input:  aaaaaa
 
Output: The longest substring with all distinct characters is a
  
 
 * */
public class LongestSubstringContainingDistinctChar {

	// Define the character range
    private static final int CHAR_RANGE = 128;
 
    // Function to find the longest substring with all
    // distinct characters using a sliding window
    public static String findLongestSubstring(String str)
    {
        // base case
        if (str == null || str.length() == 0) {
            return str;
        }
 
        // boolean array to mark characters present in the current window
        boolean[] window = new boolean[CHAR_RANGE];
 
        // stores the longest substring boundaries
        int begin = 0, end = 0;
 
        // `[low…high]` maintain the sliding window boundaries
        for (int low = 0, high = 0; high < str.length(); high++)
        {
            // if the current character is present in the current window
            if (window[str.charAt(high)])
            {
                // remove characters from the left of the window till
                // we encounter the current character
                while (str.charAt(low) != str.charAt(high))
                {
                    window[str.charAt(low)] = false;
                    low++;
                }
 
                low++;        // remove the current character
            }
            else {
                // if the current character is not present in the current
                // window, include it
                window[str.charAt(high)] = true;
 
                // update the maximum window size if necessary
                if (end - begin < high - low)
                {
                    begin = low;
                    end = high;
                }
            }
        }
 
        // return the longest substring found at `str[begin…end]`
        return str.substring(begin, end + 1);
    }
 
    public static void main(String[] args)
    {
        String str = "abbcdafeegh";
 
        System.out.print(findLongestSubstring(str));
    }

}
