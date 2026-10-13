package problems.strings.level3;

import java.util.Arrays;

public class NaivePatternSearch {
	
	static void search(String txt, String pattern)
    {
		int N = txt.length();
        int M = pattern.length();
       
        /* A loop to slide pat one by one */
        for (int i = 0; i <= N - M; i++) {
            int j;
            /* For current index i, check for pattern match */
            for (j = 0; j < M; j++) {
                if (txt.charAt(i + j) != pattern.charAt(j))
                    break;
            }
            if (j == M) {
            	// if pat[0...M-1] = txt[i, i+1, ...i+M-1]
            	System.out.println("Pattern found at index " + i);
            } 
                
        }
    }

	public static void main(String[] args ) {
		
		search("asdfghjk","asd");
		System.out.println();
	}
	}

