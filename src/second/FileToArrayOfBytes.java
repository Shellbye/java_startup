package second;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-13
 * Time: 下午4:28
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileToArrayOfBytes
{
    public static void main( String[] args )
    {
        FileInputStream fileInputStream=null;

        File file = new File("C:\\testing.txt");

        byte[] bFile = new byte[(int) file.length()];

        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

            for (int i = 0; i < bFile.length; i++) {
                System.out.print((char)bFile[i]);
                System.out.println();
            }

            System.out.println("Done");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
