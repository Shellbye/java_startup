package com.zero;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-11
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.IOException;

public class FilePathExample2
{
    public static void main( String[] args )
    {
        try {

            String filename = "testing.txt";
            String finalfile = "";
            String workingDir = System.getProperty("user.dir");
            String a = File.separator;

            finalfile = workingDir + File.separator + filename;

            System.out.println("Final filepath : " + finalfile);
            File file = new File(finalfile);

            if (file.createNewFile()){
                System.out.println("Done");
            }else{
                System.out.println("File already exists!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
