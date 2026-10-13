package algorithms.greedy;

/*Every house in the colony has at most one pipe going
 *  into it and at most one pipe going out of it. 
 *  Tanks and taps are to be installed in a manner such that 
 *  every house with one outgoing pipe but no incoming pipe gets a 
 *  tank installed on its roof and every house with only an incoming 
 *  pipe and no outgoing pipe gets a tap.’
Given two integers n and p denoting the number of houses and the 
number of pipes. The connections of pipe among the houses contain 
three input values: a_i, b_i, d_i denoting the pipe of diameter
 d_i from house a_i to house b_i, find out the efficient solution 
 for the network.
The output will contain the number of pairs of tanks and taps t 
installed in first line and the next t lines contain three integers: house number of tank, house number of tap and the minimum diameter of pipe between them.
*/
import java.util.*; 

class WaterConnectionProblem { 
	
	// number of houses and number 
	// of pipes 
	static int n, p; 

	// Array rd stores the 
	// ending vertex of pipe 
	static int rd[] = new int[1100]; 

	// Array wd stores the value 
	// of diameters between two pipes 
	static int wt[] = new int[1100]; 

	// Array cd stores the 
	// starting end of pipe 
	static int cd[] = new int[1100]; 

	// arraylist a, b, c are used 
	// to store the final output 
	static List <Integer> a = 
			new ArrayList<Integer>(); 
				
	static List <Integer> b = 
			new ArrayList<Integer>(); 
				
	static List <Integer> c = 
			new ArrayList<Integer>(); 
	
	static int ans; 
	
	static int dfs(int w) 
	{ 
		if (cd[w] == 0) 
			return w; 
		if (wt[w] < ans) 
			ans = wt[w]; 
			
		return dfs(cd[w]); 
	} 

	// Function to perform calculations. 
	static void solve(int arr[][]) 
	{ 
		int i = 0; 
	
		while (i < p) 
		{ 
			
			int q = arr[i][0]; 
			int h = arr[i][1]; 
			int t = arr[i][2]; 
			
			cd[q] = h; 
			wt[q] = t; 
			rd[h] = q; 
			i++; 
		} 
		
		a=new ArrayList<Integer>(); 
		b=new ArrayList<Integer>(); 
		c=new ArrayList<Integer>(); 
		
		for (int j = 1; j <= n; ++j) 
		
			/*If a pipe has no ending vertex 
			but has starting vertex i.e is 
			an outgoing pipe then we need 
			to start DFS with this vertex.*/
			if (rd[j] == 0 && cd[j]>0) { 
				ans = 1000000000; 
				int w = dfs(j); 
				
				// We put the details of 
				// component in final output 
				// array 
				a.add(j); 
				b.add(w); 
				c.add(ans); 
			} 
			
		System.out.println(a.size()); 
		
		for (int j = 0; j < a.size(); ++j) 
			System.out.println(a.get(j) + " "
				+ b.get(j) + " " + c.get(j)); 
	} 

	// main function 
	public static void main(String args[]) 
	{ 
		n = 9; 
		p = 6; 
		
		// set the value of the araray 
		// to zero 
		for(int i = 0; i < 1100; i++) 
			rd[i] = cd[i] = wt[i] = 0; 
		
		int arr[][] = { { 7, 4, 98 }, 
						{ 5, 9, 72 }, 
						{ 4, 6, 10 }, 
						{ 2, 8, 22 }, 
						{ 9, 7, 17 }, 
						{ 3, 1, 66 } }; 
		solve(arr); 
	} 
} 

//This code is contributed by Arnab Kundu 

