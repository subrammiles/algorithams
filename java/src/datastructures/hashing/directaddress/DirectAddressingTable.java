package datastructures.hashing.directaddress;


/*We took one array to implement this.And we used array index as the Student roll number*/
public class DirectAddressingTable {
	

	public static Student search(Student[]  grade, int rollNumber) {
		return grade[rollNumber];
	}

	public static void delete(Student[] grade, int rollNumber) {
		grade[rollNumber] = null;
	}

	public static void printInformation(int rollNumber, Student student) {
		if (student != null) {
			System.out.println("Roll number " + rollNumber + " - Name: " + student.name + ", Marks: "
					+ student.marks);
		}else {
			System.out.println("No student found");
		}	
	}

	public static void insert(Student[] grade, Student student) {
		grade[student.rollNumber] = student;
	}

	public static void main(String[] args) {
		Student[] grade8 = new Student[6];
		insert(grade8, new Student(1, 78, "Subbu"));
		insert(grade8, new Student(2, 88, "D Trump"));
		insert(grade8, new Student(3, 92, "Steve Jobs"));
		insert(grade8, new Student(5, 91, "LadyGaga"));
		Student student = search(grade8, 3);
        printInformation(3, student);
  
        // delete student record with roll number 36
        delete(grade8, 1);
        
        student = search(grade8, 1);
        
        // This will print "No student found" as we deleted
        // the record
        printInformation(1, student);
	}

}
