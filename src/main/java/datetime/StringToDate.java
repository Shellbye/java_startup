package datetime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: shellbye.com@gmail.com
 */
public class StringToDate {
    public static void main(String[] arg) throws Exception{
        String timeString = "Wed, 06-Aug-2014 06:10:09 GMT";
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MMM-yyyy hh:mm:ss z", Locale.ENGLISH);
        Date date = (Date)sdf.parse(timeString);
        System.out.println(date.toString());
        // output,注意到这里的打印结果是使用的本地的时间格式，因为转化之后没有对Date进行设置
        // Wed Aug 06 14:10:09 CST 2014
    }
}
