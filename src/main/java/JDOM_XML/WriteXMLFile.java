package JDOM_XML;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-14
 * Time: 下午5:41
 * To change this template use File | Settings | File Templates.
 */
import java.io.FileWriter;
import java.io.IOException;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class WriteXMLFile {
    public static void main(String[] args) {

        try {

            Element company = new Element("company");
            Document doc = new Document(company);
            doc.setRootElement(company);

            Element staff = new Element("staff");
            staff.setAttribute(new Attribute("id", "1"));
            staff.addContent(new Element("firstname").setText("yong"));
            staff.addContent(new Element("lastname").setText("mook kim"));
            staff.addContent(new Element("nickname").setText("mkyong"));
            staff.addContent(new Element("salary").setText("199999"));

            doc.getRootElement().addContent(staff);

            Element staff2 = new Element("staff");
            staff2.setAttribute(new Attribute("id", "2"));
            staff2.addContent(new Element("firstname").setText("low"));
            staff2.addContent(new Element("lastname").setText("yin fong"));
            staff2.addContent(new Element("nickname").setText("fong fong"));
            staff2.addContent(new Element("salary").setText("188888"));

            doc.getRootElement().addContent(staff2);

            // new XMLOutputter().output(doc, System.out);
            XMLOutputter xmlOutput = new XMLOutputter();

            // display nice nice
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter("d:\\filejdom.xml"));

            System.out.println("File Saved!");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
}