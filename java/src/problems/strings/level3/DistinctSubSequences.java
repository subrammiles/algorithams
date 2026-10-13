package problems.strings.level3;

import java.util.HashSet;
import java.util.Set;

public class DistinctSubSequences {
	
	 static Set<String> subSequences=new HashSet<String>();
	public static void main(String[] args) {
		String myString="aaa";
		subSequences(myString,"");
		
		System.out.print(subSequences.size());
	}
	
	private static void subSequences(String str,String result) {
		
		if(str.length()==0) {
			subSequences.add(result);
			return;
		}
		String s1=str.substring(1);
		String s2=result+str.charAt(0);
		subSequences(s1,s2);
		subSequences(s1,result);
	}

}
