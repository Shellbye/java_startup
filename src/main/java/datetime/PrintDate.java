package datetime;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: shellbye.com@gmail.com
 * Date: 14-8-20
 * Time: 下午3:49
 * To change this template use File | Settings | File Templates.
 */
public class PrintDate {
    public static void main(String[] arg){
        Date now = new Date();
        Long t = now.getTime();
        System.out.println(t);
        Date then = new Date(t);
        System.out.println(then);
    }
}
