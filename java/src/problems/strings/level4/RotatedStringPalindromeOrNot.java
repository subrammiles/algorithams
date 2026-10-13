package problems.strings.level4;

public class RotatedStringPalindromeOrNot {

	// Expand in both directions of `low` and `high` to find
    // palindrome of length `k`
    private static boolean expand(String str, int low, int high, int k)
    {
        while (low >= 0 && high < str.length() &&
    (str.charAt(low) == str.charAt(high)))
        {
            // return true palindrome of length `k` is found
            if (high - low + 1 == k) {
                return true;
            }
 
            // Expand in both directions
            low--;
            high++;
        }
 
        // return false if palindrome of length `k` is not found
        return false;
    }
 
    // Function to check if a palindromic substring of length `k` exists or not
    private static boolean longestPalindromicSubstring(String str, int k)
    {
        for (int i = 0; i < str.length() - 1; i++)
        {
            // check if odd or even length palindrome of length `k`
            // having `str[i]` as its midpoint exists
            if (expand(str, i, i, k) || expand(str, i, i + 1, k)) {
                return true;
            }
        }
 
        return false;
    }
 
    // Function to check if a given string is a rotated palindrome or not
    public static boolean isRotatedPalindrome(String str)
    {
        // length of the given string
        int n = str.length();
 
        // return true if the longest palindromic substring of length `n`
        // exists in the string `str + str`
        System.out.println(str + str);
        return longestPalindromicSubstring(str + str, n);
    }
 
    public static void main(String[] args)
    {
        // palindromic string
        String str = "ABCCBA";
 
        // rotate it by 2 units
        str = "CCBAAB";
 
        if (isRotatedPalindrome(str)) {
            System.out.println("The string is a rotated palindrome");
        }
        else {
            System.out.println("The string is not a rotated palindrome");
        }
    }

}
