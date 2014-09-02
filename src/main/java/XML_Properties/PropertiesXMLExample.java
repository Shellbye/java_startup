package XML_Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-15
 * Time: 上午8:35
 * To change this template use File | Settings | File Templates.
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesXMLExample
{
    public static void main(String[] args) throws IOException
    {
        Properties props = new Properties();
        props.setProperty("email.support", "donot-spam-me@nospam.com");

        //where to store?
        OutputStream os = new FileOutputStream("d:\\email-configuration2.xml_old");

        //store the properties detail into a pre-defined XML file
        props.storeToXML(os, "Support Email","UTF-8");

        System.out.println("Done");
    }
}