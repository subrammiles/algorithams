package datastructures.linkedlist;

public class MyDoubleLL {
	int value;
	public MyDoubleLL next;
	public MyDoubleLL previous;
	
	public MyDoubleLL(int value) {
	   this.value=value;	
	}
	
	public MyDoubleLL(int value,MyDoubleLL next,MyDoubleLL previous) {
		this.value=value;
		this.previous=previous;
		this.next=next;
	}
	

}
