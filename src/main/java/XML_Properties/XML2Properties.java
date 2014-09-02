package XML_Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-15
 * Time: 上午8:51
 * To change this template use File | Settings | File Templates.
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class XML2Properties {
    public static void main(String[] args) throws IOException
    {
        Properties props = new Properties();

        InputStream is = new FileInputStream("d:/email-configuration.xml_old");
        //load the xml_old file into properties format
        props.loadFromXML(is);

        String email = props.getProperty("email.support");

        System.out.println(email);

    }
}
