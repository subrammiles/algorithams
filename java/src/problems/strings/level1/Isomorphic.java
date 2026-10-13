package problems.strings.level1;

import java.util.Arrays;

public class Isomorphic {

	public static boolean isISoMorphic(String s1, String s2) {

		return false;
	}

	public static boolean checkIsomorphic(String str1, String str2) {
		int lenStr1 = str1.length();
		int lenStr2 = str2.length();
		if (lenStr1 != lenStr2)
			return false;

		int[] mappedArray = new int[256];
		Boolean[] markedArray = new Boolean[256];
		Arrays.fill(mappedArray, -1);
		Arrays.fill(markedArray, Boolean.FALSE);
		for (int i = 0; i < lenStr1; i++) {
			if (mappedArray[str1.charAt(i)] == -1) {
				if (markedArray[str2.charAt(i)] == true) {
					return false;
				}
				markedArray[str2.charAt(i)] = true;
				mappedArray[str1.charAt(i)] = str2.charAt(i);
			} else if (mappedArray[str1.charAt(i)] != str2.charAt(i)) {
				return false;
			}
				
		}
		return true;
	}

	public static void main(String[] args) {
		isISoMorphic("paper", "title");
	}

}







 






