package third;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-14
 * Time: 上午10:01
 * To change this template use File | Settings | File Templates.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZipFile1
{
    private static final String OUTPUT_GZIP_FILE = "d:\\file1.gz";
    private static final String SOURCE_FILE = "d:\\testing.txt";


    public static void main( String[] args )
    {
        GZipFile1 gZip = new GZipFile1();
        gZip.gzipIt();
    }

    /**
     * GZip it
//     * @param zipFile output GZip file location
     */
    public void gzipIt(){

        byte[] buffer = new byte[1024];

        try{

            GZIPOutputStream gzos =
                    new GZIPOutputStream(new FileOutputStream(OUTPUT_GZIP_FILE));

            FileInputStream in =
                    new FileInputStream(SOURCE_FILE);

            int len;
            while ((len = in.read(buffer)) > 0) {
                gzos.write(buffer, 0, len);
            }

            in.close();

            gzos.finish();
            gzos.close();

            System.out.println("Done");

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

}