package problems.strings.level1;

public class ReverseAString {

	static public String reverseString(String str) {
		System.out.println(str);
		if (str.isEmpty()) {
			/* if the above condition is true returns the same string */
			return str;
		} else {

			String temp1 = reverseString(str.substring(1));
			System.out.println(temp1);

			char temp2 = str.charAt(0);
			System.out.println(temp2);

			return temp1 + temp2;
		}
	}

	public static void getReverse(char[] str) {
		int n = str.length;
		int start = 0;
		for (int i = 0; i < n; i++) {
			if (str[i] == ' ' && i > 0) {
				reverse(str, start, i - 1);
				start = i + 1;
			} else if (i == n - 1) {
				reverse(str, start, i);
			}
		}
		reverse(str, 0, n - 1);
	}

	private static void reverse(char[] str, int start, int end) {
		while (start < end) {
			swap(str, start, end);
			start++;
			end--;
		}
	}

	private static void swap(char[] str, int start, int end) {
		char tmp = str[start];
		str[start] = str[end];
		str[end] = tmp;
	}
	
	private static void reverseAWordsInAString() {
		 char[] str = "This is a string" .toCharArray();
		        getReverse(str);
		 	        System.out.println(str);
	}

	public static void main(String[] args) {

		String resultantSting1 = reverseString("SUBBU");
		reverseAWordsInAString();

		// System.out.println(resultantSting1);

	}

}
