package problems.strings.level2;

import java.util.HashMap;
import java.util.Map;

//Converting Roman Numerals to Decimal lying between 1 to 3999

/*
Given a Roman numeral, the task is to find its corresponding decimal value.

Example : 

Input: IX
Output: 9
IX is a Roman symbol which represents 9 

Input: XL
Output: 40
XL is a Roman symbol which represents 40
*/
public class RomanNumeralsToDecimal {
	// This function returns
    // value of a Roman symbol
    int value(char r)
    {
        if (r == 'I')
            return 1;
        if (r == 'V')
            return 5;
        if (r == 'X')
            return 10;
        if (r == 'L')
            return 50;
        if (r == 'C')
            return 100;
        if (r == 'D')
            return 500;
        if (r == 'M')
            return 1000;
        return -1;
    }
 
    // Finds decimal value of a
    // given romal numeral
    int romanToDecimal(String str)
    {
        // Initialize result
        int res = 0;
 
        for (int i = 0; i < str.length(); i++)
        {
            // Getting value of symbol s[i]
            int s1 = value(str.charAt(i));
 
            // Getting value of symbol s[i+1]
            if (i + 1 < str.length())
            {
                int s2 = value(str.charAt(i + 1));
 
                // Comparing both values
                if (s1 >= s2)
                {
                    // Value of current symbol
                    // is greater or equalto
                    // the next symbol
                    res = res + s1;
                }
                else
                {
                    // Value of current symbol is
                    // less than the next symbol
                    res = res + s2 - s1;
                    i++;
                }
            }
            else {
                res = res + s1;
            }
        }
 
        return res;
    }
 
    // Driver Code
    public static void main(String args[])
    {
    	RomanNumeralsToDecimal ob = new RomanNumeralsToDecimal();
 
        // Considering inputs given are valid
        String str = "MCMIV";
        System.out.println("Integer form of Roman Numeral"
                           + " is "
                           + ob.romanToDecimal(str));
    }
    
    
    private static final Map<Character,
    Integer> roman = new HashMap<Character,
                                 Integer>()
{{
put('I', 1);
put('V', 5);
put('X', 10);
put('L', 50);
put('C', 100);
put('D', 500);
put('M', 1000);
}};

//This function returns value
//of a Roman symbol
private static int romanToInt(String s)
{
int sum = 0;
int n = s.length();

for(int i = 0; i < n; i++)
{

// If present value is less than next value,
// subtract present from next value and add the
// resultant to the sum variable.
if (i != n - 1 && roman.get(s.charAt(i)) <
     roman.get(s.charAt(i + 1)))
{
sum += roman.get(s.charAt(i + 1)) -
roman.get(s.charAt(i));
i++;
}
else
{
sum += roman.get(s.charAt(i));
}
}
return sum;
}

//Driver Code
public static void main2(String[] args)
{

// Considering inputs given are valid
String input = "MCMIV";

System.out.print("Integer form of Roman Numeral is " +
romanToInt(input));
}
}
