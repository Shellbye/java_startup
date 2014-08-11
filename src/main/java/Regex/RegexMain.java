package Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: shellbye.com@gmail.com
 */
public class RegexMain {
    public static void main(String[] arg){
        String s = "PositionCheck.asp?PositionExtID=CC637609929J90250014000&Version=1";
        Pattern pattern = Pattern.compile("PositionExtID=(.*)&");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
