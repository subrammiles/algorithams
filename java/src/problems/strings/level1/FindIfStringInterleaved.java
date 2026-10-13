package problems.strings.level1;


/*
 Input: strings: "XXXXZY", "XXY", "XXZ"
Output: XXXXZY is interleaved of XXY and XXZ
The string XXXXZY can be made by 
interleaving XXY and XXZ
String:    XXXXZY
String 1:    XX Y
String 2:  XX  Z

Input: strings: "XXY", "YX", "X"
Output: XXY is not interleaved of YX and X
XXY cannot be formed by interleaving YX and X.
The strings that can be formed are YXX and XYX
 
 
 
 * */


public class FindIfStringInterleaved {
	
	
//	boolean isInterleaved(
//		    char A, char  B, char  C)
//		{
//		    // Base Case: If all strings are empty
//		    if (!(A.i || B==" " || C==""))
//		        return true;
//		 
//		    // If C is empty and any of the
//		    // two strings is not empty
//		    if (C == '\0')
//		        return false;
//		 
//		    // If any of the above mentioned
//		    // two possibilities is true,
//		    // then return true, otherwise false
//		    return ((*C == *A) && isInterleaved(
//		                              A + 1, B, C + 1))
//		           || ((*C == *B) && isInterleaved(
//		                                 A, B + 1, C + 1));
//}

//The main function that returns
//true if C is an interleaving of A
//and B, otherwise false. 
static boolean isInterleaved(String A, String B,
                          String C)
{
  
 // Find lengths of the two strings
 int M = A.length(), N = B.length();

 // Let us create a 2D table to store
 // solutions of subproblems. C[i][j]
 // will br true if C[0..i+j-1] is an
 // interleaving of A[0..i-1] and B[0..j-1].
 boolean IL[][] = new boolean[M + 1][N + 1];

 // IL is default initialised by false

 // C can be an interleaving of A and B
 // only if the sum of lengths of A and B
 // is equal to length of C
 if ((M + N) != C.length())
     return false;

 // Process all characters of A and B

 for(int i = 0; i <= M; i++)
 {
     for(int j = 0; j <= N; j++)
     {
          
         // Two empty strings have an
         // empty strings as interleaving
         if (i == 0 && j == 0)
             IL[i][j] = true;

         // A is empty
         else if (i == 0)
         {
             if (B.charAt(j - 1) ==
                 C.charAt(j - 1))
                 IL[i][j] = IL[i][j - 1];
          }

         // B is empty
         else if (j == 0)
         {
             if (A.charAt(i - 1) ==
                 C.charAt(i - 1))
                 IL[i][j] = IL[i - 1][j];
         }

         // Current character of C matches
         // with current character of A,
         // but doesn't match with current
         // character if B
         else if (A.charAt(i - 1) ==
                  C.charAt(i + j - 1) &&
                  B.charAt(j - 1) !=
                  C.charAt(i + j - 1))
             IL[i][j] = IL[i - 1][j];

         // Current character of C matches
         // with current character of B, but
         // doesn't match with current
         // character if A
         else if (A.charAt(i - 1) !=
                  C.charAt(i + j - 1) &&
                  B.charAt(j - 1) ==
                  C.charAt(i + j - 1))
             IL[i][j] = IL[i][j - 1];

         // Current character of C matches
         // with that of both A and B
         else if (A.charAt(i - 1) ==
                  C.charAt(i + j - 1) &&
                  B.charAt(j - 1) ==
                  C.charAt(i + j - 1))
             IL[i][j] = (IL[i - 1][j] ||
                         IL[i][j - 1]);
      }
 }
 return IL[M][N];
}

//Function to run test cases
static void test(String A, String B, String C)
{
 if (isInterleaved(A, B, C))
     System.out.println(C + " is interleaved of " +
                        A + " and " + B);
 else
     System.out.println(C + " is not interleaved of " +
                        A + " and " + B);
}

//Driver code
public static void main(String[] args)
{
 test("XXY", "XXZ", "XXZXXXY");
 test("XY", "WZ", "WZXY");
 test("XY", "X", "XXY");
 test("YX", "X", "XXY");
 test("XXY", "XXZ", "XXXXZY");
}


}
