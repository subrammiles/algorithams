package problems.strings.level4;

public class AllCombinations {
	
	
	
	static void combo(String prefix, String s)
	  {
	    int N = s.length();
	    
//	    if(prefix.length()==2) {
//	    	System.out.println(prefix);
//	    }
	    
	    System.out.println(prefix);
	    for (int i = 0 ; i < N ; i++) {
	       String newPrefix=prefix + s.charAt(i);
	       String newString=s.substring(i+1);
	      
	        combo(newPrefix, newString);
	    }
	  }
	

	public static void main(String[] args) {
		
		combo("", "ABC");
	}

}
