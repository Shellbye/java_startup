package third;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-14
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZipFile
{
    private static final String INPUT_GZIP_FILE = "d:\\file1.gz";
    private static final String OUTPUT_FILE = "d:\\file1.log";

    public static void main( String[] args )
    {
        GZipFile gZip = new GZipFile();
        gZip.gunzipIt();
    }

    /**
     * GunZip it
     */
    public void gunzipIt(){

        byte[] buffer = new byte[1024];

        try{

            GZIPInputStream gzis =
                    new GZIPInputStream(new FileInputStream(INPUT_GZIP_FILE));

            FileOutputStream out =
                    new FileOutputStream(OUTPUT_FILE);

            int len;
            while ((len = gzis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            gzis.close();
            out.close();

            System.out.println("Done");

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}