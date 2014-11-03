package mongoDb;

/**
 * Created by shellbye on 11/3/14.
 */
public class MultiTest {
    public static void main(String args[]) {
        String[] ss = new String[]{"a","b","c","d","e","f"};
        for(String s : ss){
            MRunner R1 = new MRunner(s);
            R1.start();
        }
    }
}
