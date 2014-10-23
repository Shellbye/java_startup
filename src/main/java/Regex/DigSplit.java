package Regex;

/**
 * Created with IntelliJ IDEA.
 * User: shellbye.com@gmail.com
 * Date: 14-8-14
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public class DigSplit {
    public static void main(String[] args){
        String str = "教育经历：04年09-2008.07月 电子科技大学 计算机软件与理论 硕士 成都市";
        int n = 1;
        while(n < 10){
            System.out.println("=============正则作用次数：" + n + "================");
            String[] a = str.split("[\\D]+", n);
            for(int i=0;i<a.length;i++){
                System.out.println(i + " :" + a[i]);
            }
            n++;
        }
    }
}
