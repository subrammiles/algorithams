package datastructures.queue.linear.arraybased;

import java.util.Arrays;

public class Queue {
	int[] queue;
    int real,front;
    
    
    Queue(int size){
    	queue=new int[size];
    	real=-1;
    	front=-1;
    }
    
    
    public boolean isQueueEmpty() {
    	// If both the front and rear are  -1
    	if(front==-1&& real==-1) {
    		return true;
    	}
    	return false;
    }
    
    
    public boolean isQueueFull() {
    	if(real==queue.length-1)
    	{
    		return true;
    	}
    	return false;
    }
    
    
    
    public void enque(int x) {
    	if(isQueueFull()) {
    		System.out.println("Queue is full");
    	}else {	
    		if(front==-1) {
    			front=0;
    		}
    		real=real+1;
    		queue[real]=x;
    	}
    }
    
    public void deque() {
    	if(isQueueEmpty()) {
    		System.out.println("Queue is empty");
    	}else {
    		System.out.println(queue[front]+" is consumed");
    		queue[front]=0;
           if(front==real) {
        	   
           }else {
    		front=real=-1;	
    	}
           }
    }
    
    public void printQueue() {
    	System.out.println(Arrays.toString(queue));
    }
    
}
