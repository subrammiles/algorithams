package problems.strings.level2;


/*
 Find all strings of a given length containing balanced parentheses
Given a positive number n, find all strings of length n containing balanced parentheses.

For example,

Input:  n = 4
Output:
(())
()()
 
Input:  n = 6
Output:
((()))
(()())
(())()
()(())
()()()
 
Input:  n = 5
Output: Invalid input 
 
 * */
public class AllStringsContainingBalancedPar {
	// Function to find all strings of length `n` containing balanced parentheses
    public static void balParenthesis(int n, String str, int open)
    {
        // if `n` is odd with no open parentheses, balanced parentheses
        // cannot be formed
        if ((n & 1) == 1 && open == 0) {
            return;
        }
 
        // base case: length `n` is reached
        if (n == 0)
        {
            // if the output string contains all balanced parenthesis, print it
            if (open == 0) {
                System.out.println(str);
            }
            return;
        }
 
        // Optimization: return if we cannot close all open parentheses
        // with left characters
        if (open > n) {
            return;
        }
 
        // recur with open parentheses
        balParenthesis(n - 1, str + "(", open + 1);
 
        // recur with closed parentheses only if the output string has
        // at least one unclosed parentheses
        if (open > 0) {
            balParenthesis(n - 1, str + ")", open - 1);
        }
    }
 
    public static void main(String[] args)
    {
        int n = 6;
        balParenthesis(n, "", 0);
    }
}
