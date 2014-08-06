package datetime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * User: shellbye.com@gmail.com
 */
public class DateToString {
    public static void main(String[] arg){
        Date now = new Date();
        // 设置显示的日期格式，具体格式信息参考下面官方文档，用这个，几乎可以把时间格式化为任何样子
        // http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        // 这里的第二个参数是指用英文的时间格式来格式化时间，如果没有参数就会默认使用本地设置
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss z", Locale.ENGLISH);
        // 设置时区，GMT（Greenwich Mean Time 格林尼治标准时间），
        // CST（多个含义，请参考：http://baike.baidu.com/subview/638082/14687089.htm#viewPageContent）
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println("GMT time: " + sdf.format(now));
        // output
        // GMT time: Wed, 06-Aug-2014 06:10:09 GMT
    }
}
