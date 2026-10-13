package problems.strings.level2;

import java.util.Arrays;

public class FindAllLexicographicStrings {
	// Function to find all lexicographic permutations of a given
    // string where the repetition of characters is allowed
    public static void findLexicographic(char[] chars, String output)
    {
        // base condition (permutation found)
        if (output.length() == chars.length)
        {
            // print the permutation and return
            System.out.print(output + " ");
            return;
        }
 
        // consider all characters of the string one by one
        for (char c: chars) {
            findLexicographic(chars, output + c);
        }
    }
 
    // Wrapper over `findLexicographic()` function
    public static void findLexicographic(String str)
    {
        // base case
        if (str == null || str.length() == 0) {
            return;
        }
 
        // sort the string first to print in lexicographic order
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
 
        findLexicographic(chars, "");
    }
 
    public static void main(String[] args)
    {
        String str = "ACB";
 
        findLexicographic(str);
    }
}
