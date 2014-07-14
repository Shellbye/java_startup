package first;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-9
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class DoubleEncoding {
    public static void main(String[] args) throws Exception {
        byte[] encoding1 = "你好吗".getBytes("UTF-8");
        String string1 = new String(encoding1, "ISO8859-1");
        for (byte b : encoding1) {
            System.out.printf("%2x ", b);
        }
        System.out.println();
        byte[] encoding2 = string1.getBytes("UTF-8");
        for (byte b : encoding2) {
            System.out.printf("%2x ", b);
        }
        System.out.println();
    }
}
