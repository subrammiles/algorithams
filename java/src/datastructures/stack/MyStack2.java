package datastructures.stack;

/*The most common stack implementation 
 * is using arrays*/
/*Stores char data type*/
public class MyStack2 {
	public char arr[];
	public int top;
	public int capacity;

	public MyStack2(int size) {
		arr = new char[size];
		capacity = size;
		top = -1;
	}
}