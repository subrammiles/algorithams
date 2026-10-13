package datastructures.stack.operations;


/*
 Major operations should contains the 
 1.Push the element to stack
 2.Pop the element
 3.is empty check
 * */

public class Stack {
	private char arr[];//This stack stores characters
	private int top;
	private int size;
	
	
	public Stack(int size) {
		this.arr = new char[size];
		this.size = size;
		this.top = -1;
	}
	
	
	public boolean isStackEmpty() {
		if(top==-1) {
			return true;
		}
		return false;
	}
	
	public boolean isStackFull() {
		if(top>=size) {
			System.out.println("Stack is full");
			return true;
		}
		return false;
		
	}
	
	public boolean push(char c) {
		if(isStackFull()) {
			return false;
		}else {
			if(isStackEmpty()) {
				top=0;
				arr[top]=c;
				top=top+1;
			}else {
				arr[top]=c;
				top=top+1;
			}
			return true;	
		}
	}
	
	public void printStack() {
		System.out.print(arr);
	}
	
}

