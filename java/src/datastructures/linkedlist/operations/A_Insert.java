package datastructures.linkedlist.operations;

import datastructures.linkedlist.MyDoubleLL;
import datastructures.linkedlist.MySingleLL;

public class A_Insert {

	public static MySingleLL insertSingleLinkedList() {
		MySingleLL linkedList=new MySingleLL(1);
		linkedList.next=new MySingleLL(2);
		linkedList.next.next=new MySingleLL(3);
		linkedList.next.next.next=new MySingleLL(4);
		return linkedList;
	}
	
	public static MyDoubleLL insertDoubleLinkedList() {
		MyDoubleLL linkedList=new MyDoubleLL(1);
		MyDoubleLL secondNode=new MyDoubleLL(2);
		MyDoubleLL thirdNode=new MyDoubleLL(2);
		linkedList.next=secondNode;
		secondNode.previous=linkedList;
		thirdNode.previous=secondNode;
		return linkedList;
	}
	
	public static void main(String[] args) {
		insertSingleLinkedList();
	}
}
