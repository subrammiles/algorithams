package datastructures.queue.circular.arraybased;



import java.util.Arrays;

public class Queue {
	private int size, front, rear;
	private  int[] queue;
	 
	Queue(int size)
	{
	    this.size = size;
	    this.front = this.rear = -1;
	    queue= new int[size];
	}
	 
	
	 void enQueue(int data)
	{
	    if((front == 0 && rear == size - 1))
	    {
	    	/*Queue is full*/
	    	/*
	    	 * 12, 45 ,56 ,78
	    	 * 
	    	 * F           R
	    	 * 
	    	 * */
	        System.out.print("Queue is Full");
	    }else if( rear == (front - 1) % (size - 1)) {
	    	/*Queue is full*/
	    	/*
	    	 * 12, 45 ,56 ,78
	    	 * 
	    	 *      R   F
	    	 * 
	    	 * */
	    	//real=1
	    	//(2-1)%(4-1)=1%3=1
	    	System.out.print("Queue is Full");
	    	
	    }
	    // condition for empty queue.
	    else if(front == -1)
	    {
	        front = 0;
	        rear = 0;
	        queue[rear]= data;
	    }
	 
	    else if(rear == size - 1 && front != 0)
	    {   /*Queue is full*/
	    	/*
	    	 *  __, 45 ,56 ,78
	    	 * 
	    	 *      F       R
	    	 * 
	    	 * */
	        rear = 0;
	        queue[rear]= data;
	    }
	 
	    else
	    {
	        rear = (rear + 1);
	        
	        if(front <= rear)
	        {
	            queue[rear]= data;
	        }
	        else
	        {//update the old value
	            queue[rear]= data;
	        }
	    }
	}
	 
	public int  deQueue()
	{
	    int dequeElement;
	    if(front == -1)
	    {
	        System.out.print("Queue is Empty");
	        return -1;
	    }
	 
	    dequeElement = queue[front];
	 
	    // only one element present
	    if(front == rear)
	    {
	        front = -1;
	        rear = -1;
	    }
	 
	    else if(front == size - 1)
	    {
	        front = 0;
	    }
	    else
	    {
	        front = front + 1;
	    }
	     
	    return dequeElement;
	}
	 
	
	
	
	public void print()
	{
	     
	    if(front == -1)
	    {
	        System.out.print("Queue is Empty");
	        return;
	    }
	 
	    
	 
	    if(rear >= front)
	    {
	     
	        // Loop  elements from front to rear.
	        for(int i = front; i <= rear; i++)
	        {
	            System.out.print(queue[i]);
	            System.out.print(" ");
	        }
	        System.out.println();
	    }
	 
	    // If rear crossed the max index and indexing has started in loop
	    else
	    {
	         
	        
	        //Loop  front to max size or last index
	        for(int i = front; i < size; i++)
	        {
	            System.out.print(queue[i]);
	            System.out.print(" ");
	        }
	 
	        
	        // Loop from 0th index till rear 
	        for(int i = 0; i <= rear; i++)
	        {
	            System.out.print(queue[i]);
	            System.out.print(" ");
	        }
	        System.out.println();
	    }
	
	}
	

	public void printArray() {
		System.out.println(Arrays.toString(queue));	
	}	
	
}
