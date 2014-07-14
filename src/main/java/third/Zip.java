package third;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-13
 * Time: 下午6:38
 * To change this template use File | Settings | File Templates.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip
{
    public static void main( String[] args )
    {
        byte[] buffer = new byte[1024];

        try{

            FileOutputStream fos = new FileOutputStream("C:\\MyFile.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze= new ZipEntry("spy.log");
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream("C:\\logfile.txt");

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
            zos.closeEntry();

            //remember close it
            zos.close();

            System.out.println("Done");

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}