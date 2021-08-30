import java.util.StringTokenizer;

public class KeyValue {

    private String key, value;

    KeyValue(String key_value){
        StringTokenizer st = new StringTokenizer(key_value,"=");
        if (st.hasMoreTokens()) {
            this.key = st.nextToken();
            this.value = st.nextToken();
        }
    }

    KeyValue(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return this.key;
    }

    public String getValue(){
        return this.value;
    }
}

