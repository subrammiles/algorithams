package datastructures.stack.applications;

import java.util.Arrays;

import datastructures.stack.MyStack;
import datastructures.stack.MyStack2;

public class ReverseAStringUsingStack {
	
	static void reverseAString(String s) {
		int lengthOfString=s.length();
		MyStack2 mystack= new MyStack2(lengthOfString);
		for(int i=0;i<lengthOfString;i++) {
			push(mystack,s.charAt(i));
		}
		System.out.println(Arrays.toString(mystack.arr));
    	StringBuffer output =new StringBuffer();
    	for(int i=0;i<lengthOfString;i++) {
    		output.append(pop(mystack));
		}
		System.out.println(output.toString());
	}
	
	/* Add elements into stack */
	public static void push(MyStack2 myStack,char value) {
		if (isFull(myStack)) {
			myStack.top=myStack.top+1;
			myStack.arr[myStack.top]=value;
		}else {
			System.out.println("Stack is full");
		}
		
	}
	public static char pop(MyStack2 myStack) {
		if (isEmpty(myStack)) {
			System.out.println("Stack is empty");
			return 0;
		}else {
			return myStack.arr[myStack.top--];
		}
	}
	
	public static boolean isFull(MyStack2 myStack) {
		if(myStack.top != (myStack.capacity - 1)){
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isEmpty(MyStack2 myStack) {
		if(myStack.top <0){
			return true;
		}else {
			return false;
		}
	}
	
	public static int getStackCurrentSize(MyStack myStack) {
		return myStack.top+1;
	}
	
	public static void main(String[] args) {
		reverseAString("subbu");
	}

}
