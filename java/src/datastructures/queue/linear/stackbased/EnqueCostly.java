package datastructures.queue.linear.stackbased;

import java.util.Stack;

public class EnqueCostly {
	
	Stack<Integer> stack1=new Stack<Integer>();
	Stack<Integer> stack2=new Stack<Integer>();
	
	void enqueu(int data) {
		
		stack1.push(data);
		
		while(stack1.size()>=1)
		{
			Integer i=stack1.pop();
			stack2.push(i);
		}
	   
		while(stack2.size()>=1)
		{
			Integer i=stack2.pop();
			stack1.push(i);
		}	
	}
	void deque() {
		
		Integer i=stack1.pop();
		System.out.println("Eleemnt deque opearion "+i);
		
	}
	
	void printStack() {
		System.out.println(stack1);
	}

	public static void main(String[] args) {
		//Stack LIFO   and Queue FIFO
		QueueFromStack queue=new QueueFromStack();
		queue.enqueu(1);
		queue.enqueu(23);
		queue.enqueu(45);
		queue.enqueu(36);
		queue.enqueu(37);
		queue.printStack();
		queue.deque();		
	}


}
