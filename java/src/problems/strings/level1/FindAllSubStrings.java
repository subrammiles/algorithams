package problems.strings.level1;

public class FindAllSubStrings {

	public static void method2(String str) {
		int n = str.length();
		for (int i = 0; i <= n; i++) {
			for (int j = i+1; j <= n; j++) {
				String x=str.substring(i,j);
				System.out.println(x);
			}
		}
	}

	/* Generate a substring using the previous substring */
	/// Function to print all (n * (n + 1)) / 2
	// subStrings of a given String s of length n.
	public static void method3(String s) {
		int n = s.length();

		/* Generate a substring using the previous substring */

		// Fix start index in outer loop.
		// Reveal new character in inner
		// loop till end of String.
		// Print till-now-formed String.
		for (int i = 0; i < n; i++) {
			char[] temp = new char[n+1];
			int tempindex = 0;
			
			System.out.println("<<<<<<<<<<<<<<<<< i "+i+">>>>>>>>>");

			for (int j = i; j < n; j++) {
				temp[tempindex++] = s.charAt(j);
				temp[tempindex] = '\0';
				System.out.println(" #j ="+j+"#");
				System.out.println(temp);
			}
		}
	}

	

	public static void main(String[] args) {

		method2("abc");

	}

	

}
