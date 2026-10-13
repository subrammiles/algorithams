package datastructures.stack.applications;

import java.util.Arrays;

import datastructures.stack.MyStack;

/*
 postfix evaluation: -4
 * */
public class EvaluationOfPostfixExpression {
	// Method to evaluate value of a postfix expression
    static void evaluatePostfix(String exp)
    {
        //create a stack
    	MyStack stack=new MyStack(exp.length()); 
        // Scan all characters one by one
        for(int i=0;i<exp.length();i++)
        {
            char c=exp.charAt(i);
             
            // If the scanned character is an operand (number here),
            // push it to the stack.
            if(Character.isDigit(c))
            push(stack,c - '0');
             
            //  If the scanned character is an operator, pop two
            // elements from stack apply the operator
            else
            {
                int val1 = pop(stack);
                int val2 = pop(stack);
                 
                switch(c)
                {
                    case '+':
                    push(stack,val2+val1);
                    break;
                    case '-':
                    push(stack,val2- val1);
                    break;
                    case '/':
                    push(stack,val2/val1);
                    break;
                    case '*':
                    push(stack,val2*val1);
                    break;
              }
            }
        }
        System.out.println(stack.top);
        System.out.println(Arrays.toString(stack.arr));
        
    }
    

	/* Add elements into stack */
	public static void push(MyStack myStack,int value) {
		if (isFull(myStack)) {
			myStack.top=myStack.top+1;
			myStack.arr[myStack.top]=value;
		}else {
			System.out.println("Stack is full");
		}
		
	}
	public static int pop(MyStack myStack) {
		if (isEmpty(myStack)) {
			System.out.println("Stack is empty");
			return 0;
		}else {
			int value=myStack.arr[myStack.top];
			myStack.top--;
			return value;
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
     
    // Driver program to test above functions
    public static void main(String[] args)
    {
        String exp="231*+9-";
        evaluatePostfix(exp);
       
    }
}
