package javaIO;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: shellbye.com@gmail.com
 * Date: 14-8-6
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
public class RedirectConsole {
    public static void main(String[] args) throws Exception {
        File f = new File("c:\\output1.txt");
        PrintStream out = new PrintStream(new FileOutputStream(f));
        PrintStream old_out = System.out;
        System.setOut(out);

        test();

        System.setOut(old_out);



        String sCurrentLine;
        BufferedReader br = null;

        br = new BufferedReader(new FileReader(f));

        while ((sCurrentLine = br.readLine()) != null) {
            System.out.print(sCurrentLine);
        }

        f.delete();
    }

    public static void test(){
        System.out.print("0");
    }
}
