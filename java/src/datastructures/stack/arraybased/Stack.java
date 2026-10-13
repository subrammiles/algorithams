package datastructures.stack.arraybased;


/*The most common stack implementation 
 * is using arrays*/

/*
 Stack should contain
 1.A data structure to store the data
 2.Should contain the capacity
 * */

public class Stack {
	public char arr[];//This stack stores characters
	public int top;
	public int size;

	public Stack(int size) {
		this.arr = new char[size];
		this.size = size;
		this.top = -1;
	}
	
}

/*
 |       |
 |       |
 |       |
 |       |
 |       |
 |       |
 |       |
 |_______| 

            top=-1
 */
 