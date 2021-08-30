import java.util.ArrayList;
import java.util.Collections;

public class Professor implements Comparable<Professor>{
    private final String name, department,professorId,classNum;

    public Professor(String name, String department, String professorId, String classNum){
        this.name = name;
        this.department = department;
        this.professorId = professorId;
        this.classNum = classNum;
    }

    public String getName(){ return this.name;}
    public String getProfessorId(){ return this.professorId;}


    @Override
    public int compareTo(Professor o) {
        return this.classNum.compareTo(o.classNum);
    }

    public void printProfessorArrayList(ArrayList<Professor> ALP){
        Collections.sort(ALP);
        for (Professor professor:ALP) {
            System.out.println("name: "+professor.name+", department: "+professor.department+", professorId: "+professor.professorId+", classNum: "+professor.classNum);
        }
    }
}

