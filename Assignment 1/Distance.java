public class Distance {
    private String name;
    private double lat,lng;
    Distance(String name, double lat, double lng){
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
    public String writeDistance(){
        return "Country : "+this.name+"\nlatitude = "+this.lat+"\nlongitude = "+this.lng+"\n--------------------";
    }
    public static String getDistance(Distance a,Distance b){
        double distance = Math.pow(Math.pow((a.lng-b.lng),2)+Math.pow((a.lat- b.lat),2),0.5);
        return "Distance of\n"+a.writeDistance()+"\n"+b.writeDistance()+"\nis\n"+distance;
    }
}
