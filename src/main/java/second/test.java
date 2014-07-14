package second;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-13
 * Time: 下午2:55
 * To change this template use File | Settings | File Templates.
 */
import java.io.DataInputStream;
import java.io.FileInputStream;

public class test {
    public static void main (String args[]) {

        try{

            DataInputStream dis =
                    new DataInputStream (
                            new FileInputStream ("c:\\logfile.txt"));

            byte[] datainBytes = new byte[dis.available()];
            dis.readFully(datainBytes);
            dis.close();

            String content = new String(datainBytes, 0, datainBytes.length);

            System.out.println(content);

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
