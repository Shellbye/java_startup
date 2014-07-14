package third;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-13
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
 */
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Deserializer{

    public static void main (String args[]) {

        Deserializer deserializer = new Deserializer();
        Address address = deserializer.deserialzeAddress();
        System.out.println(address);
    }

    public Address deserialzeAddress(){

        Address address;

        try{

            FileInputStream fin = new FileInputStream("c:\\address.ser");
            ObjectInputStream ois = new ObjectInputStream(fin);
            address = (Address) ois.readObject();
            ois.close();

            return address;

        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}