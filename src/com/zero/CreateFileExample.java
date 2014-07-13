package com.zero; /**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-11
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.IOException;

public class CreateFileExample
{
    public static void main( String[] args )
    {
        try {

            File file = new File("D:\\newfile.txt");

            if (file.createNewFile()){
                System.out.println("File is created!");
            }else{
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
