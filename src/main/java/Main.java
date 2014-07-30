import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class Main {

    public static void main(String[] args) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://shellbye.com");
        HttpResponse response = httpclient.execute(httpGet);
        System.out.println(response.toString());
        httpGet.releaseConnection();

        HttpGet httpGet2 = new HttpGet("http://shellbye.com");
        HttpResponse response2 = httpclient.execute(httpGet2);
        System.out.println(response2.toString());
        httpGet2.releaseConnection();

        HttpGet httpGet3 = new HttpGet("http://shellbye.com");
        HttpResponse response3 = httpclient.execute(httpGet3);
        System.out.println(response3.toString());
        httpGet3.releaseConnection();
    }
}