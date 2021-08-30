import java.util.ArrayList;
import java.util.Collections;

public class Student implements Comparable<Student>{
    private String name;
    private final String department,studentId,phoneNum;

    public Student(String name, String department, String studentId, String phoneNum){
        this.name = name;
        this.department = department;
        this.studentId = studentId;
        this.phoneNum = phoneNum;
    }

    public String getName(){ return this.name;}
    public String getDepartment(){ return this.department;}
    public String getStudentId(){ return this.studentId;}
    public String getPhoneNum(){ return this.phoneNum;}

    public void setName(String name){ this.name = name;}

    @Override
    public int compareTo(Student student) {
        return this.name.compareTo(student.name);
    }

    public void printStudentArrayList(ArrayList<Student> ALS){
        Collections.sort(ALS);
        for (Student student:ALS) {
            System.out.println("name: "+student.name+", department: "+student.department+", studentId: "+student.studentId+", phoneNum: "+student.phoneNum);
        }
    }
}
