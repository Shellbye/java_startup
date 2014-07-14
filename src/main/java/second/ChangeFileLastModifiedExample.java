package second;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-13
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeFileLastModifiedExample
{
    public static void main(String[] args)
    {

        try{

            File file = new File("C:\\logfile.txt");

            //print the original last modified date
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            System.out.println("Original Last Modified Date : "
                    + sdf.format(file.lastModified()));

            //set this date
            String newLastModified = "01/31/1998";

            //need convert the above date to milliseconds in long value
            Date newDate = sdf.parse(newLastModified);
            file.setLastModified(newDate.getTime());

            //print the latest last modified date
            System.out.println("Lastest Last Modified Date : "
                    + sdf.format(file.lastModified()));

        }catch(ParseException e){

            e.printStackTrace();

        }

    }
}
