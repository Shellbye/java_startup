package third;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-14
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class printStdIn{

    public static void main (String args[]) {

        try{
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));

            String input;

            while((input=br.readLine())!="z"){
                System.out.println(input);
            }

        }catch(IOException io){
            io.printStackTrace();
        }
    }
}