package directory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-14
 * Time: 上午11:26
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;

public class DisplayDirectoryAndFile{

    public static void main (String args[]) {

        displayIt(new File("d:\\mysite"));
    }

    public static void displayIt(File node){

        System.out.println(node.getAbsoluteFile());

        if(node.isDirectory()){
            String[] subNote = node.list();
            for(String filename : subNote){
                displayIt(new File(node, filename));
            }
        }

    }
}