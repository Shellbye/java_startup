package third;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-14
 * Time: 上午10:21
 * To change this template use File | Settings | File Templates.
 */
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPOutputStream;

public class Serializer implements Serializable{

    public static void main (String args[]) {

        Serializer serializer = new Serializer();
        serializer.serializeAddress("wall street", "united state");
    }

    public void serializeAddress(String street, String country){

        Address address = new Address();
        address.setStreet(street);
        address.setCountry(country);

        try{

            FileOutputStream fos = new FileOutputStream("c:\\address.gz");
            GZIPOutputStream gz = new GZIPOutputStream(fos);

            ObjectOutputStream oos = new ObjectOutputStream(gz);

            oos.writeObject(address);
            oos.close();

            System.out.println("Done");

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}