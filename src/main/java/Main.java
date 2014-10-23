import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class Main {
    public static void print(Object in){
        System.out.println(in);
    }

    public static void main(String[] args) throws Exception {
        String a = "aaaaaaa<br>bbbbbbb<br />ccccccc<br/>ddddddddd<br>eeeeeee";
        if( a instanceof String){
            print("yes");
        }
        print(a);
        String b = brToN(a);
        print(b);
    }

    public static String brToN(String in){
        return in.replaceAll("<br>", "\n")
                .replaceAll("<br />", "\n")
                .replaceAll("<br/>", "\n");
    }
}