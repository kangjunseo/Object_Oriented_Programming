import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Countries {
    private String country;
    private double lat, lng;
    Countries(String country){
        StringTokenizer st = new StringTokenizer(country,",");
        if (st.hasMoreTokens()){
            this.country=st.nextToken();
            this.lat=Double.parseDouble(st.nextToken());
            this.lng=Double.parseDouble(st.nextToken());
        }
    }

    public String getCountry() {
        return country;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lng;
    }



    public Countries[] countries() {
        Countries[] CountryArray = new Countries[0];
        try {
            File file = new File("Countries.csv");
            Scanner sc = new Scanner(file);
            CountryArray = new Countries[245];

            for (int i = 0; i < CountryArray.length; i++) {
                CountryArray[i] = new Countries(sc.nextLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return CountryArray;
    }
}

