package datastructures.hashing.keyvalue;

/*In this implementation we can add only 5 students.*/
/*We are taking 2 arrays.One array will store the Students
 names and other will save the student marks.
 Names and marks should be stores at the same index
 in the arrays. 
 */

public class ArrayBasedKeyValue {
    String[] studentNames=new String[5];
    int[] studentMarkes=new int[]{-1,-1,-1,-1,-1};
    private int getMarks(String name){
         for(int i=0;i<studentNames.length;i++){
            if(name.equals(studentNames[i])){
             return studentMarkes[i];    
            }
         }
        return 0;
    }


    private void  addMarks(String name,int marks){
        boolean isMarkesAdded=false;
        for(int i=0;i<studentMarkes.length;i++){
            if(studentMarkes[i]==-1){
                studentNames[i]=name;
                studentMarkes[i]=marks;
                isMarkesAdded=true;
            }
        }
        if(!isMarkesAdded){
            System.out.println("Failed to add marks");
        }
    }

    private void addData(){
        
        studentNames[0]="Subrahmanyam";
        studentMarkes[0]=88;
        studentNames[1]="TrumpD";
        studentMarkes[1]=78;
        studentNames[2]="Jack";
        studentMarkes[2]=71;
    }
    public static void  main(String[] args) { 
        ArrayBasedKeyValue hash=new ArrayBasedKeyValue();
        hash.addData();
        hash.addMarks("Putin",71);
        hash.addMarks("Rose",51);
        hash.addMarks("Raju",41);
        System.out.println("Putin marks = "+Integer.toString(hash.getMarks("Putin")));
    }
}
