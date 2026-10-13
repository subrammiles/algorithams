package problems.strings.level3;

import java.util.ArrayList;
import java.util.List;

public class SubSequence {
	
	
	
	// Declare a global list
    static List<String> subSequencesList = new ArrayList<>();
 
    // Creating a public static Arraylist such that
    // we can store values
    // IF there is any question of returning the
    // we can directly return too// public static
    // ArrayList<String> al = new ArrayList<String>();
    public static void main(String[] args)
    {
        
        findsubsequences(); // Calling a function
        
    }
    
    private  static void findsubsequences() {
    	 String s = "abc";
    	 method1(s, "");
    	 System.out.println(subSequencesList);
    };
    
    
    private static String  removeFirstCharacter(String tempString) {
    	return tempString.substring(1);
    }
    
    private static Character  getFirstCharacter(String tempString) {
    	return tempString.charAt(0);
    }
    
 
    private static void method1(String tempString,String ans)
    {
        if (tempString.length() == 0) {
        	subSequencesList.add(ans);
            return;
        }
        String remainingStrAfterPick=removeFirstCharacter(tempString);
        String newAnswerString=ans + getFirstCharacter(tempString);
        /*Pick the first character of tempString */
        method1(remainingStrAfterPick,newAnswerString);
        /*Skip  the first character of tempString */
        method1(remainingStrAfterPick, ans);
    }
}
