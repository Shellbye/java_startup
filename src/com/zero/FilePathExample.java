package com.zero;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-11
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.IOException;

public class FilePathExample
{
    public static void main( String[] args )
    {
        try {

            String filename = "testing.txt";
            String finalfile = "";
            String workingDir = System.getProperty("user.dir");

            String your_os = System.getProperty("os.name").toLowerCase();
            if(your_os.indexOf("win") >= 0){
                finalfile = workingDir + "\\" + filename;
            }else if(your_os.indexOf( "nix") >=0 || your_os.indexOf( "nux") >=0){
                finalfile = workingDir + "/" + filename;
            }else{
                finalfile = workingDir + "{others}" + filename;
            }

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