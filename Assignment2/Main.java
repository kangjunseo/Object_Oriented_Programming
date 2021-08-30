import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        ArrayList<Student> ALS = new ArrayList<>();
        ArrayList<Professor> ALP = new ArrayList<>();
        ArrayList<Reservation> ALR = new ArrayList<>();

        try {
            Scanner SS = new Scanner(new File("student.csv"));
            SS.nextLine();
            while(SS.hasNextLine()){
                String[] studentInfo = SS.nextLine().split(",");
                Student freshman = new Student(studentInfo[0],studentInfo[1],studentInfo[2],studentInfo[3]);
                ALS.add(freshman);
            }

            Scanner SP = new Scanner(new File("professor.csv"));
            SP.nextLine();
            while(SP.hasNextLine()){
                String[] professorInfo = SP.nextLine().split(",");
                Professor professor = new Professor(professorInfo[0],professorInfo[1],professorInfo[2],professorInfo[3]);
                ALP.add(professor);
            }

            Scanner SR = new Scanner(new File("reservation.csv"));
            SR.nextLine();
            while(SR.hasNextLine()){
                String[] reservationInfo = SR.nextLine().split(",");
                Reservation reservation = new Reservation(reservationInfo);
                ALR.add(reservation);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Main.Menu MM = new Menu();
        try {
            MM.MenuPrint(ALS,ALP,ALR);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RuntimeException e1){
            System.out.println("Unexpected RuntimeException happened. Restart the program.");
            e1.printStackTrace();
        }
    }
    static class Menu{
        public void MenuPrint(ArrayList<Student> ALS, ArrayList<Professor> ALP, ArrayList<Reservation> ALR) throws FileNotFoundException {
            int MenuNum = 0;

            Scanner sc = new Scanner(System.in);
            String reservationFirstLine = "roomNum,6/1 a.m.,6/1 p.m.,6/2 a.m.,6/2 p.m.,6/3 a.m.,6/3 p.m.,6/4 a.m.,6/4 p.m.,6/5 a.m.,6/5 p.m.,6/6 a.m.,6/6 p.m.,6/7 a.m.,6/7 p.m.";
            while (MenuNum!=7) {
                System.out.println("==== 강의실 대여 및 인적사항 조회====\n1. 전체 예약 현황 조회\n2. 호실 예약 현황 조회\n3. 예약하기 및 예약 취소하기");
                System.out.println("4. 나의 예약 조회\n5. 학생 인적사항 변경\n6. 교수 인적사항 조회\n7. 종료");
                MenuNum = sc.nextInt();
                sc.nextLine();
                switch (MenuNum) {
                    case 1 -> {
                        System.out.println("[1. 전체 예약 현황 조회]");
                        Reservation nullReservation = new Reservation();
                        nullReservation.printReservationList(ALR);
                    }
                    case 2 -> {
                        System.out.println("[2. 호실 예약 현황 조회]");
                        String targetNum = sc.nextLine();
                        for (Reservation reservation : ALR) {
                            if (reservation.getRoomNum().equals(targetNum)) {
                                System.out.print("가능한 시간: ");
                                for (int i = 0; i < reservation.getALT().size(); i++) {
                                    if (reservation.getALT().get(i).equals("possible")) {
                                        if (i % 2 == 0) {
                                            System.out.print("6/" + ((i / 2) + 1) + "오전 ");
                                        } else {
                                            System.out.print("6/" + ((i / 2) + 1) + "오후 ");
                                        }
                                    }
                                }
                                System.out.println();
                            }
                        }
                    }
                    case 3 -> {
                        System.out.println("[3. 예약하기 및 취소하기]\n1. 예약하기\n2. 예약 취소하기");
                        int subMenu3Num = sc.nextInt();
                        sc.nextLine();
                        if (subMenu3Num == 1) {
                            int marker = 0;
                            System.out.println("이름, 아이디, 호실 번호, 시간, 사유 입력");
                            try {
                                String[] reserve = sc.nextLine().split(" ");
                                for (Student student : ALS) {
                                    if (student.getName().equals(reserve[0])) {
                                        marker++;
                                    }
                                }
                                for (Professor professor : ALP) {
                                    if (professor.getName().equals(reserve[0])) {
                                        marker++;
                                    }
                                }
                                if (marker == 0) {
                                    throw new Exception();
                                }
                                marker = 0;
                                for (Student student : ALS) {
                                    if (student.getStudentId().equals(reserve[1])) {
                                        marker++;
                                    }
                                }
                                for (Professor professor : ALP) {
                                    if (professor.getProfessorId().equals(reserve[1])) {
                                        marker++;
                                    }
                                }
                                if (marker == 0) {
                                    throw new Exception();
                                }
                                marker = 0;
                                Reservation selectedRoom = null;
                                String[] reserve2 = reserve[2].split("호");
                                for (Reservation reservation : ALR) {
                                    if (reservation.getRoomNum().equals(reserve2[0])) {
                                        marker++;
                                        if (marker != 0) {
                                            selectedRoom = reservation;
                                            break;
                                        }
                                    }
                                }
                                if (marker == 0) {
                                    throw new Exception();
                                }
                                String[] reserve3 = reserve[3].split("");
                                if (reserve3[4].equals("전")) {
                                    for (Reservation reservation : ALR) {
                                        if (reservation.equals(selectedRoom)) {
                                            if (reservation.getALT().get(Integer.parseInt(reserve3[2]) * 2 - 2).equals("possible")) {
                                                reservation.getALT().set(Integer.parseInt(reserve3[2]) * 2 - 2, "impossible");

                                                //set reservation.csv
                                                BufferedWriter bw = new BufferedWriter(new FileWriter("reservation.csv"));
                                                bw.write(reservationFirstLine);
                                                bw.newLine();
                                                for (Reservation reservation1 : ALR) {
                                                    bw.write(reservation1.getRoomNum() + ",");
                                                    for (String str : reservation1.getALT()) {
                                                        bw.write(str + ",");
                                                    }
                                                    bw.newLine();
                                                }
                                                bw.flush();
                                                bw.close();
                                                //set reservationRecord.csv
                                                BufferedWriter bw1 = new BufferedWriter(new FileWriter("reservationRecord.csv", true));
                                                for (String str : reserve) {
                                                    bw1.write(str + ",");
                                                }
                                                bw1.newLine();
                                                bw1.flush();
                                                bw1.close();

                                                System.out.println(reserve[2] + " " + reserve[3] + "에 예약되었습니다.");
                                            } else {
                                                throw new Exception();
                                            }
                                        }
                                    }
                                } else {
                                    for (Reservation reservation : ALR) {
                                        if (reservation.equals(selectedRoom)) {
                                            if (reservation.getALT().get(Integer.parseInt(reserve3[2]) * 2 - 1).equals("possible")) {
                                                reservation.getALT().set(Integer.parseInt(reserve3[2]) * 2 - 1, "impossible");

                                                //set reservation.csv
                                                BufferedWriter bw = new BufferedWriter(new FileWriter("reservation.csv"));
                                                bw.write(reservationFirstLine);
                                                bw.newLine();
                                                for (Reservation reservation1 : ALR) {
                                                    bw.write(reservation1.getRoomNum() + ",");
                                                    for (String str : reservation1.getALT()) {
                                                        bw.write(str + ",");
                                                    }
                                                    bw.newLine();
                                                }
                                                bw.flush();
                                                bw.close();
                                                //set reservationRecord.csv
                                                BufferedWriter bw1 = new BufferedWriter(new FileWriter("reservationRecord.csv", true));
                                                for (String str : reserve) {
                                                    bw1.write(str + ",");
                                                }
                                                bw1.newLine();
                                                bw1.flush();
                                                bw1.close();

                                                System.out.println(reserve[2] + " " + reserve[3] + "에 예약되었습니다.");
                                            } else {
                                                throw new Exception();
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("예약 실패!");
                            }
                        } else if (subMenu3Num == 2) {
                            System.out.println("이름, 아이디, 호실번호, 시간 입력");
                            String[] CancelReserve = sc.nextLine().split(" ");
                            Scanner SRR = new Scanner(new File("reservationRecord.csv"));
                            ArrayList<String[]> ALR_Record1 = new ArrayList<>();
                            while(SRR.hasNextLine()){
                                String[] strings = SRR.nextLine().split(",");
                                ALR_Record1.add(strings);
                            }
                            for (String[] strings : ALR_Record1) {
                                if (strings[0].equals(CancelReserve[0]) && strings[1].equals(CancelReserve[1]) && strings[2].equals(CancelReserve[2]) && strings[3].equals(CancelReserve[3])) {

                                    ALR_Record1.remove(strings);
                                    try {
                                        BufferedWriter bw = new BufferedWriter(new FileWriter("reservationRecord.csv"));
                                        for (String[] strings1 : ALR_Record1) {
                                            for (String str : strings1) {
                                                bw.write(str + ",");
                                            }
                                            bw.newLine();
                                        }
                                        bw.flush();
                                        bw.close();
                                        System.out.println("예약이 취소되었습니다.");
                                        //reservation impossible to possible
                                        for(Reservation reservation:ALR){
                                            if (CancelReserve[2].equals(reservation.getRoomNum())){
                                                String[] temp = CancelReserve[3].split("");
                                                if (temp[4].equals("전")){
                                                    reservation.setALT(Integer.parseInt(temp[2])*2-2,"possible");
                                                }else{
                                                    reservation.setALT(Integer.parseInt(temp[2])*2-1,"possible");
                                                }
                                            }
                                        }
                                        //update reservation.csv
                                        BufferedWriter bw1 = new BufferedWriter(new FileWriter("reservation.csv"));
                                        bw1.write(reservationFirstLine);
                                        bw1.newLine();
                                        for (Reservation reservation1 : ALR) {
                                            bw1.write(reservation1.getRoomNum() + ",");
                                            for (String str : reservation1.getALT()) {
                                                bw1.write(str + ",");
                                            }
                                            bw1.newLine();
                                        }
                                        bw1.flush();
                                        bw1.close();
                                        break;
                                    } catch (IOException e) {
                                        System.out.println("IOException happened");
                                        e.printStackTrace();
                                    }
                                } else {
                                    System.out.println("예약 목록에 없습니다.");
                                }
                            }
                        } else {
                            System.out.println("잘못된 입력입니다. 메뉴 번호부터 다시 눌러주세요.");
                        }
                    }
                    case 4 -> {
                        System.out.println("[4. 나의 예약조회]\n이름과 아이디(학번 또는 교직원 번호)입력");
                        String[] CheckReservation = sc.nextLine().split(" ");
                        Scanner SRR = new Scanner(new File("reservationRecord.csv"));
                        ArrayList<String[]> ALR_Record2 = new ArrayList<>();
                        while(SRR.hasNextLine()){
                            String[] strings = SRR.nextLine().split(",");
                            ALR_Record2.add(strings);
                        }
                        int check =0;
                        for(String[] strings:ALR_Record2){
                            if(strings[0].equals(CheckReservation[0])&&strings[1].equals(CheckReservation[1])){
                                System.out.println("이름: "+strings[0]+", 아이디: "+strings[1]+", 호실번호: "+strings[2]+", 시간: "+strings[3]+", 예약사유: "+strings[4]);
                                check++;
                            }
                        }
                        if(check==0){
                            System.out.println("없는 예약입니다. 이름과 아이디를 다시 확인해주세요.");
                        }
                    }
                    case 5 -> {
                        System.out.println("[5. 학생 인적사항 변경]\n1. 인적사항 조회\n2. 이름 변경\n3. 학생 삭제");
                        int subMenu5Num = sc.nextInt();
                        sc.nextLine();
                        switch (subMenu5Num) {
                            case 1:
                                System.out.println("1. 인적사항 조회");
                                Student nullStudent = new Student(null, null, null, null);
                                nullStudent.printStudentArrayList(ALS);
                                break;
                            case 2:
                                System.out.println("2. 이름 변경");
                                String targetName = sc.nextLine();
                                int count = 0;
                                for (Student student : ALS) {
                                    if (targetName.equals(student.getName())) {
                                        System.out.println("어떤 이름으로 변경하시겠습니까?");
                                        String replaceName = sc.nextLine();
                                        student.setName(replaceName);
                                        //update to csv file
                                        try {
                                            BufferedWriter bw = new BufferedWriter(new FileWriter("student.csv"));
                                            bw.write("name,department,studentId,phoneNum");
                                            bw.newLine();
                                            for (Student student1 : ALS) {
                                                bw.write(student1.getName() + "," + student1.getDepartment() + "," + student1.getStudentId() + "," + student1.getPhoneNum());
                                                bw.newLine();
                                            }
                                            bw.flush();
                                            bw.close();
                                            System.out.println("이름 변경이 완료되었습니다.");
                                            count = 1;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                if (count == 0) {
                                    System.out.println("없는 이름입니다. 다시 확인해주세요.");
                                }
                                break;
                            case 3:
                                System.out.println("3. 학생 삭제");
                                count = 0;
                                String deleteName = sc.nextLine();
                                for (Student student : ALS) {
                                    if (deleteName.equals(student.getName())) {
                                        ALS.remove(student);
                                        //update to csv file
                                        try {
                                            BufferedWriter bw = new BufferedWriter(new FileWriter("student.csv"));
                                            bw.write("name,department,studentId,phoneNum");
                                            bw.newLine();
                                            for (Student student1 : ALS) {
                                                bw.write(student1.getName() + "," + student1.getDepartment() + "," + student1.getStudentId() + "," + student1.getPhoneNum());
                                                bw.newLine();
                                            }
                                            bw.flush();
                                            bw.close();
                                            System.out.println("삭제되었습니다.");
                                            count = 1;
                                            break;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                if (count == 0) {
                                    System.out.println("없는 이름입니다. 다시 확인해주세요.");
                                }
                                break;
                            default:
                                System.out.println("잘못된 입력입니다. 메뉴 번호부터 다시 눌러주세요.");
                        }
                    }
                    case 6 -> {
                        System.out.println("[6. 교수 인적사항 조회]");
                        Professor nullProfessor = new Professor(null, null, null, null);
                        nullProfessor.printProfessorArrayList(ALP);
                    }
                    case 7 -> System.out.println("[7. 종료]\n종료합니다.");
                    default -> System.out.println("Press the right number.");
                }
            }
        }
    }
}

