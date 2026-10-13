package datastructures.stack.applications;

import java.util.Arrays;

import datastructures.stack.MyStack;

/*Create a Stack  of Capacity=5 and push 1 to 5*/
public class A_StackOperations {

	/* Add elements into stack */
	public static void push(MyStack myStack,int value) {
		if (isFull(myStack)) {
			myStack.top=myStack.top+1;
			myStack.arr[myStack.top]=value;
		}else {
			System.out.println("Stack is full");
		}
		
	}
	public static void pop(MyStack myStack) {
		if (isEmpty(myStack)) {
			System.out.println("Stack is empty");
		}else {
			myStack.top=myStack.top-1;
		}
		
	}
	
	public static boolean isFull(MyStack myStack) {
		if(myStack.top != (myStack.capacity - 1)){
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isEmpty(MyStack myStack) {
		if(myStack.top == - 1){
			return true;
		}else {
			return false;
		}
	}
	
	public static int getStackCurrentSize(MyStack myStack) {
		return myStack.top+1;
	}

	public static void main(String[] args) {
		MyStack stack1 = new MyStack(5);
		push(stack1,1);
		push(stack1,2);
		push(stack1,3);
		push(stack1,4);
		push(stack1,5);
		pop(stack1);
		System.out.println(Arrays.toString(stack1.arr));
		System.out.println(stack1.top);
	}
}
