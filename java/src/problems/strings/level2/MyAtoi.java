package problems.strings.level2;

public class MyAtoi {

	
	// A simple atoi() function
    static int myAtoi(String str)
    {
        // Initialize result
        int res = 0;
 
        // Iterate through all characters
        // of input string and update result
        // take ASCII character of corresponding digit and
        // subtract the code from '0' to get numerical
        // value and multiply res by 10 to shuffle
        // digits left to update running total
        for (int i = 0; i < str.length(); ++i)
            res = res * 10 + str.charAt(i) - '0';
 
        // return result.
        return res;
    }
 
    // Driver code
    public static void main(String[] args)
    {
        String str = "89789";
       
         
        // Function call
        int val = myAtoi(str);
        System.out.println(val);
    }
    
    
 // A simple atoi() function
    static int myAtoi2(char[] str)
    {
 
        // Initialize result
        int res = 0;
 
        // Initialize sign as positive
        int sign = 1;
 
        // Initialize index of first digit
        int i = 0;
 
        // If number is negative, then
        // update sign
        if (str[0] == '-') {
            sign = -1;
 
            // Also update index of first
            // digit
            i++;
        }
 
        // Iterate through all digits
        // and update the result
        for (; i < str.length; ++i)
            res = res * 10 + str[i] - '0';
 
        // Return result with sign
        return sign * res;
    }
 
    // Driver code
    public static void main2(String[] args)
    {
        char[] str = "-123".toCharArray();
       
        // Function call
        int val = myAtoi2(str);
        System.out.println(val);
    }
    
    
    static int myAtoi3(char[] str)
    {
        int sign = 1, base = 0, i = 0;
 
        // if whitespaces then ignore.
        while (str[i] == ' ')
        {
            i++;
        }
 
        // sign of number
        if (str[i] == '-' || str[i] == '+')
        {
            sign = 1 - 2 * (str[i++] == '-' ? 1 : 0);
        }
 
        // checking for valid input
        while (i < str.length
               && str[i] >= '0'
               && str[i] <= '9') {
 
            // handling overflow test case
            if (base > Integer.MAX_VALUE / 10
                || (base == Integer.MAX_VALUE / 10
                    && str[i] - '0' > 7))
            {
                if (sign == 1)
                    return Integer.MAX_VALUE;
                else
                    return Integer.MIN_VALUE;
            }
            base = 10 * base + (str[i++] - '0');
        }
        return base * sign;
    }
 
    // Driver code
    public static void main3(String[] args)
    {
        char str[] = " -123".toCharArray();
       
        // Function call
        int val = myAtoi3(str);
        System.out.printf("%d ", val);
    }
}
