package com.zero;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-13
 * Time: 上午9:05
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;

public class RenameFileExample
{
    public static void main(String[] args)
    {

        File oldfile =new File("c:\\oldfile.txt");
        File newfile =new File("c:\\newfile.txt");

        if(oldfile.renameTo(newfile)){
            System.out.println("Rename succesful");
        }else{
            System.out.println("Rename failed");
        }

    }
}
