package com.zero;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-13
 * Time: 下午12:59
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.text.SimpleDateFormat;

public class GetFileLastModifiedExample
{
    public static void main(String[] args)
    {
        File file = new File("c:\\logfile.txt");

        System.out.println("Before Format : " + file.lastModified());

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        System.out.println("After Format : " + sdf.format(file.lastModified()));
    }
}
