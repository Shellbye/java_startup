package datetime;

import java.util.*;
import java.text.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-6
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
public class TestDate {
    public static void print(Object in){
        System.out.println(in);
    }
    public static void main(String[] args) {
        Date now = new Date();
        final SimpleDateFormat sdf =
                new SimpleDateFormat("E, dd-MMM-yyyy hh:mm:ss z");

// Give it to me in GMT time.
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        sdf.setDateFormatSymbols(new DateFormatSymbols(Locale.ENGLISH));
        System.out.println("GMT time: " + sdf.format(now));
        print(now);
        Calendar cal = Calendar.getInstance();

        DateFormat d1 = DateFormat.getDateInstance(); //默认语言（汉语）下的默认风格（MEDIUM风格，比如：2008-6-16 20:54:53）
        String str1 = d1.format(now);
        DateFormat d2 = DateFormat.getDateTimeInstance();
        String str2 = d2.format(now);
        DateFormat d3 = DateFormat.getTimeInstance();
        String str3 = d3.format(now);
        DateFormat d4 = DateFormat.getInstance(); //使用SHORT风格显示日期和时间
        String str4 = d4.format(now);

        DateFormat d5 = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL); //显示日期，周，时间（精确到秒）
        String str5 = d5.format(now);
        DateFormat d6 = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG); //显示日期。时间（精确到秒）
        String str6 = d6.format(now);
        DateFormat d7 = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT); //显示日期，时间（精确到分）
        String str7 = d7.format(now);
        DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到分）
        String str8 = d8.format(now);//与SHORT风格相比，这种方式最好用





        System.out.println("用Date方式显示时间: " + now);//此方法显示的结果和Calendar.getInstance().getTime()一样


        System.out.println("用DateFormat.getDateInstance()格式化时间后为：" + str1);
        System.out.println("用DateFormat.getDateTimeInstance()格式化时间后为：" + str2);
        System.out.println("用DateFormat.getTimeInstance()格式化时间后为：" + str3);
        System.out.println("用DateFormat.getInstance()格式化时间后为：" + str4);

        System.out.println("用DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL)格式化时间后为：" + str5);
        System.out.println("用DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG)格式化时间后为：" + str6);
        System.out.println("用DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)格式化时间后为：" + str7);
        System.out.println("用DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM)格式化时间后为：" + str8);
    }

}
