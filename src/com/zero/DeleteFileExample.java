package com.zero;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-12
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;

public class DeleteFileExample
{
    public static void main(String[] args)
    {
        try{

            File file = new File("javaio-appendfile.txt");

            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation is failed.");
            }

        }catch(Exception e){

            e.printStackTrace();

        }

    }
}
