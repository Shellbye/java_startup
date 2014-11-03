package mongoDb;

import net.mindview.util.CountingGenerator;

/**
 * Created by shellbye on 11/3/14.
 */
public class MRunner implements  Runnable {
    private Thread t;
    private String threadName;

    MRunner(String s){
        threadName = s;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );
    }

    public void start ()
    {
        System.out.println("Starting " +  threadName );
        if (t == null)
        {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
