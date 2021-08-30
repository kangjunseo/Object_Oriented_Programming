import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class TravelInfoRequest {
    public static void main(String[] args) {

        KeyValue[] KVarray = new KeyValue[7];
        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.YEAR)+"-0"+(cal.get(Calendar.MONTH)+1)+"-0"+cal.get(Calendar.DATE);
        KVarray[0]= new KeyValue("date",date);

        try {
            File file = new File("properties.txt");
            Scanner sc2 = new Scanner(file);
            for (int i =1; i <KVarray.length; i++){
                KVarray[i]=new KeyValue(sc2.nextLine());
            }
            KeyValue start = null;
            KeyValue depart = null;

            File file2 = new File("template_file.txt");
            Scanner sc = new Scanner(file2);
            StringTokenizer st = null;

            PrintWriter pw = new PrintWriter("output.txt");

            while(sc.hasNextLine()) {
                String str = sc.nextLine();
                st = new StringTokenizer(str,"{");
                if (st.hasMoreTokens()&&!str.equals("<add info>")) {
                    pw.print(st.nextToken());
                }
                while (st.hasMoreTokens()) {
                    String rest = st.nextToken();
                    String[] restArray=rest.split("}");
                    switch (restArray[0]) {
                        case "date":
                            for (KeyValue keyValue : KVarray) {
                                if (keyValue.getKey().equals("date")) {
                                    pw.print(keyValue.getValue());
                                }
                            }
                            break;
                        case "inquiry":
                            for (KeyValue keyValue : KVarray) {
                                if (keyValue.getKey().equals("inquiry")) {
                                    pw.print(keyValue.getValue());
                                }
                            }
                            break;
                        case "name":
                            for (KeyValue keyValue : KVarray) {
                                if (keyValue.getKey().equals("name")) {
                                    pw.print(keyValue.getValue());
                                }
                            }
                            break;
                        case "thisparty":
                            for (KeyValue keyValue : KVarray) {
                                if (keyValue.getKey().equals("thisparty")) {
                                    pw.print(keyValue.getValue());
                                }
                            }
                            break;
                        case "startcountry":
                            for (KeyValue keyValue : KVarray) {
                                if (keyValue.getKey().equals("startcountry")) {
                                    pw.print(keyValue.getValue());
                                    start = keyValue;
                                }
                            }
                            break;
                        case "departcountry":
                            for (KeyValue keyValue : KVarray) {
                                if (keyValue.getKey().equals("departcountry")) {
                                    pw.print(keyValue.getValue());
                                    depart = keyValue;
                                }
                            }
                            break;
                        case "email":
                            for (KeyValue keyValue : KVarray) {
                                if (keyValue.getKey().equals("email")) {
                                    pw.print(keyValue.getValue());
                                }
                            }
                            break;
                    }
                    for (int i = 1; i<restArray.length;i++){
                        pw.print(restArray[i]);
                    }
                }if (!str.equals("<add info>")){ pw.println();}
                else{Countries nullCnt = new Countries("");
                    Countries[] cntArray =nullCnt.countries();
                    double slat=0,slng=0,dlat=0,dlng=0;
                    for(Countries countries : cntArray){
                        if(countries.getCountry().equals(start.getValue())){
                            slat =countries.getLat();
                            slng =countries.getLon();
                        }else if(countries.getCountry().equals(depart.getValue())){
                            dlat =countries.getLat();
                            dlng =countries.getLon();
                        }
                    }
                    Distance startCnt = new Distance(start.getValue(),slat,slng);
                    Distance departCnt = new Distance(depart.getValue(),dlat,dlng);
                    pw.println(Distance.getDistance(startCnt,departCnt));
                }
            }
            pw.close();
        }catch (FileNotFoundException e){
            e.getStackTrace();
        }
    }
}

