package com.zero;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-12
 * Time: 下午4:16
 * To change this template use File | Settings | File Templates.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFileExample {
    public static void main(String[] args) {
        try {

            String content = "This is the content to write into file";

            File file = new File("c://filename.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
