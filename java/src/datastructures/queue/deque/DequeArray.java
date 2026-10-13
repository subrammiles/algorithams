package datastructures.queue.deque;

public class DequeArray {
	  int queue[];
	  int front;
	  int rear;
	  int size;

	  public DequeArray(int size) {
		queue = new int[size];
	    front = -1;
	    rear = 0;
	    this.size = size;
	  }

	  boolean isFull() {
		  if((front == 0 && rear == size - 1)) {
            return true;			  
		  }else if(front == rear + 1) {
			  return true;
		  }else {
			  return false;
		  }	
	  }

	  boolean isEmpty() {
	     if(front == -1) {;
	     return true;
	    }else {
	    	return false;
	    }
	  }

	  void insertFront(int data) {
	    if (isFull()) {
	      System.out.println("Full");
	      return;
	    }else {
	    	if (front == -1) {
	    		//Queue is empty
	  	      front = 0;
	  	      rear = 0;
	  	    }
	  	    else if (front == 0){
	  	    	front = size - 1;
	  	       //So this will goes to array end	
	  	    }
	  	    else{ 
	  	    	front = front - 1;
	  	    }
	    	//Add the data
	  	    queue[front] = data;
	    }
	  }

	  void insertRear(int key) {
	    if (isFull()) {
	      System.out.println(" Full ");
	      return;
	    }else {
	    	if (front == -1) {
	    		//Queue is empty
	  	      front = 0;
	  	      rear = 0;
	  	    }
	  	    else if (rear == size - 1){
	  	    	//Real is at end pf the array.We should Start from 0th index
	  	    	rear = 0;
	  	    }
            else
            { 
            	rear = rear + 1;
            }
	  	    queue[rear] = key;
	    }
	  }

	  void deleteFront() {
	    if (isEmpty()) {
	      System.out.println("Empty");
	      return;
	    }else {
	    	// Deque has only one element
		    if (front == rear) {
		      front = -1;
		      rear = -1;
		    } else if (front == size - 1) {
		      front = 0;}
	         else
	         { front = front + 1;}
	    }
	  }

	  void deleteRear() {
	    if (isEmpty()) {
	      System.out.println(" Empty");
	      return;
	    }

	    if (front == rear) {
	      front = -1;
	      rear = -1;
	    } else if (rear == 0)
	      rear = size - 1;
	    else
	      rear = rear - 1;
	  }

	  int getFront() {
	    if (isEmpty()) {
	      System.out.println("Empty");
	      return -1;
	    }
	    return queue[front];
	  }

	  int getRear() {
	    if (isEmpty() || rear < 0) {
	      System.out.println("Empty");
	      return -1;
	    }
	    return queue[rear];
	  }
}
