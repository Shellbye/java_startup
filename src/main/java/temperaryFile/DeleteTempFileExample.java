package temperaryFile;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-14
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.IOException;

public class DeleteTempFileExample
{
    public static void main(String[] args)
    {

        try{

            //create a temp file
            File temp = File.createTempFile("temptempfilefile", ".tmp");

            //delete temporary file when the program is exited
            temp.deleteOnExit();

            //delete immediate
            //temp.delete();

        }catch(IOException e){

            e.printStackTrace();

        }

    }
}