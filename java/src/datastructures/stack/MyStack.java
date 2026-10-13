package datastructures.stack;

/*The most common stack implementation 
 * is using arrays*/
public class MyStack {
	public int arr[];
	public int top;
	public int capacity;

	public MyStack(int size) {
		arr = new int[size];
		capacity = size;
		top = -1;
	}
	public MyStack() {
		
	}
}