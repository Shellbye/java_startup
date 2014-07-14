package third;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-13
 * Time: 下午5:08
 * To change this template use File | Settings | File Templates.
 */
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Serializer0 {

    public static void main (String args[]) {

        Serializer0 serializer = new Serializer0();
        serializer.serializeAddress("wall street", "united state");
    }

    public void serializeAddress(String street, String country){

        Address address = new Address();
        address.setStreet(street);
        address.setCountry(country);

        try{

            FileOutputStream fout = new FileOutputStream("c:\\address.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(address);
            oos.close();
            System.out.println("Done");

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}