package temperaryFile;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-14
 * Time: 上午10:26
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.IOException;

public class CreateTempFileExample
{
    public static void main(String[] args)
    {

        try{

            //create a temp file
            File temp = File.createTempFile("temp-file-name", ".tmp");

            System.out.println("Temp file : " + temp.getAbsolutePath());

        }catch(IOException e){

            e.printStackTrace();

        }

    }
}