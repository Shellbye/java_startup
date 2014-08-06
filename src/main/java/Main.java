import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class Main {
    public static void print(Object in){
        System.out.println(in);
    }

    public static void main(String[] args) throws Exception {
        String a = "asdf=123;asdfa";
        String b = "adfadfadfadf";
        String[] aa = a.split(";");
        String[] bb = b.split(";");
        print(aa.length);
        print(bb.length);
    }
}