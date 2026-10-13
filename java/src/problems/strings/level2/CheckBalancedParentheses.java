package problems.strings.level2;

public class CheckBalancedParentheses {
	
	public static boolean isValid(String str) {
		 
        if (str == null || str.length() == 0) {
            return true;
        }
 
        int count = 0;
        int i = 0;
        while (i < str.length()) {
            char ch = str.charAt(i);
            if (ch == '(') {
                count++;
            } else if (ch == ')') {
                if (count == 0)
                    return false;
                else
                    count--;
            }
            i++;
        }
 
        if(count != 0) 
            return false;
         
        return true;
    }
     
    public static void main(String[] args) {
        String str = "((BCD)AE)";
        System.out.println("isValid: " + isValid(str));
        str = ")(PH)N(X)";
        System.out.println("isValid: " + isValid(str));
    }
    
}
