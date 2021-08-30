import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Reservation implements Comparable<Reservation>{
    private String roomNum;
    private ArrayList<String> ALT = new ArrayList<>();

    public Reservation(String[] info){
        this.roomNum = info[0];
        this.ALT.addAll(Arrays.asList(info).subList(1, info.length));
    }
    public Reservation(){//nullReservation
    }
    public String getRoomNum(){
        return this.roomNum;
    }
    public ArrayList<String> getALT() { return this.ALT;}
    public void setALT(int index,String str){
        this.ALT.set(index,str);
    }

    @Override
    public int compareTo(Reservation o) {
        return this.roomNum.compareTo(o.roomNum);
    }

    public void printReservationList(ArrayList<Reservation> ALR){
        Collections.sort(ALR);
        for (Reservation reservation:ALR) {
            System.out.println(reservation.roomNum);
            System.out.print("오전 : ");
            for (int i = 0;i<reservation.ALT.size();i+=2) {
                if(reservation.ALT.get(i).equals("possible"))
                System.out.print("6/"+((i/2)+1)+" ");
            }
            System.out.println();
            System.out.print("오후 : ");
            for (int i = 1;i<reservation.ALT.size();i+=2) {
                if(reservation.ALT.get(i).equals("possible"))
                    System.out.print("6/"+((i/2)+1)+" ");
            }
            System.out.println();
        }
    }
}

