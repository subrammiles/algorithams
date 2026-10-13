package datastructures.hashing.theory;

public class SimpleHashFunction {

	
	int  bookHashing(String bookTitle, int hashTableSize) {
		  return bookTitle.length() % hashTableSize;
	}
	
	void addBooks(){
		bookHashing("The Grapes of Wrath", 12);
		bookHashing("The Sound and the Fury", 12);
	}

	
}
