package datastructures.queue.linear.stackbased;

import java.util.Stack;

/*
 *Deque is costly
 * */

public class QueueFromStack {
	Stack<Integer> stack1=new Stack<Integer>();
	Stack<Integer> stack2=new Stack<Integer>();
	
	void enqueu(int data) {
		stack1.push(data);
	}
	void deque() {
		
		while(stack1.size()>=1)
		{
			Integer i=stack1.pop();
			stack2.push(i);
		}
		
		System.out.println(stack2);
		
		Integer i=stack2.pop();
		System.out.println("Eleemnt deque opearion "+i);
		stack2.forEach((element)->{
			stack1.push(element);
		});
		
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
