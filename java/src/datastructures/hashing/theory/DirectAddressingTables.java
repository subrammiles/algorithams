package datastructures.hashing.theory;

public class DirectAddressingTables {

	static class Student {
		int rollNumber;
		String name;
		int marks;

		public Student(int rollNumber, int marks, String name) {
			this.rollNumber = rollNumber;
			this.name = name;
			this.marks = marks;
		}
	}

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
		Student[] grade1 = new Student[6];
		insert(grade1, new Student(1, 78, "Subbu"));
		insert(grade1, new Student(2, 88, "D Trump"));
		insert(grade1, new Student(3, 92, "Steve Jobs"));
		insert(grade1, new Student(5, 91, "LadyGaga"));
		Student student = search(grade1, 3);
        printInformation(3, student);
  
        // delete student record with roll number 36
        delete(grade1, 1);
        
        student = search(grade1, 1);
        
        // This will print "No student found" as we deleted
        // the record
        printInformation(1, student);
	}
}
