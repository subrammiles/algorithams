package problems.strings.level2;

import java.util.Stack;

//Minimum number of bracket reversals needed to make an expression balanced

//Input:  exp = "}{"
//Output: 2
//We need to change '}' to '{' and '{' to
//'}' so that the expression becomes balanced, 
//the balanced expression is '{}'
//
//Input:  exp = "{{{"
//Output: Can't be made balanced using reversals
//
//Input:  exp = "{{{{"
//Output: 2 
//
//Input:  exp = "{{{{}}"
//Output: 1 
//
//Input:  exp = "}{{}}{{{"
//Output: 3

public class MiniBracketReversalsToMakeBalance {
	
	// Method count minimum reversal for
    //making an expression balanced.
    //Returns -1 if expression cannot be balanced
    static int countMinReversals(String expr)
    {
        int len = expr.length();
     
        // length of expression must be even to make
        // it balanced by using reversals.
        if (len%2 != 0)
        return -1;
     
        // After this loop, stack contains unbalanced
        // part of expression, i.e., expression of the
        // form "}}..}{{..{"
        Stack<Character> s=new Stack<>();
         
        for (int i=0; i<len; i++)
        {
            char c = expr.charAt(i);
            if (c =='}' && !s.empty())
            {
                if (s.peek()=='{')
                    s.pop();
                else
                    s.push(c);
            }
            else
                s.push(c);
        }
     
        // Length of the reduced expression
        // red_len = (m+n)
        int red_len = s.size();
     
        // count opening brackets at the end of
        // stack
        int n = 0;
        while (!s.empty() && s.peek() == '{')
        {
            s.pop();
            n++;
        }
     
        // return ceil(m/2) + ceil(n/2) which is
        // actually equal to (m+n)/2 + n%2 when
        // m+n is even.
        return (red_len/2 + n%2);
    }
    
    
    // Method count minimum reversal for
    // making an expression balanced.
    // Returns -1 if expression cannot be balanced
    static int countMinReversals2(String expr)
    {
        int len = expr.length();
        int ans;
       
        // Expressions of odd lengths
        // cannot be balanced
        if (len % 2 != 0) {
            return -1;
        }
        int left_brace = 0, right_brace = 0;
        for (int i = 0; i < len; i++) {
            char ch = expr.charAt(i);
           
            // If we find a left bracket then we simply
            // increment the left bracket
            if (ch == '{') {
                left_brace++;
            }
           
            // Else if left bracket is 0 then we find
            // unbalanced right bracket and increment
            // right bracket or if the expression
            // is balanced then we decrement left
            else {
                if (left_brace == 0) {
                    right_brace++;
                }
                else {
                    left_brace--;
                }
            }
        }
        ans = (int)(Math.ceil((0.0 + left_brace) / 2)
                    + Math.ceil((0.0 + right_brace) / 2));
        return ans;
    }
    
    
    
    // Returns count of minimum reversals for making
 // expr balanced. Returns -1 if expr cannot be
 // balanced.
 private int countMinReversals3(String s)
 {
    int temp=0, res=0, n=s.length();
    if(n%2!=0)
            return -1;
    for(int i=0;i<n;i++){
        if(s.charAt(i)=='{')
            temp++;
        else{
             if(temp==0){
                 res++;
                 temp++;
            }
            else
                temp--;
        }
    }
    if(temp>0)
        res += temp/2;
    return res;
 }
  

}
