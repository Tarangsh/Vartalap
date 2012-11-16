package IO.OutputQueue;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: vineet
 * Date: 15/11/12
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class WriteStream implements  Runnable {

    OutputStream os ;
    ArrayList<String> writePackets;
    private static final String LOGTAG = "WriteStream";
    int accountID;

    public WriteStream(OutputStream os , int accountID ) {
        this.os = os;
        writePackets = new ArrayList<String>();
        this.accountID = accountID;
    }

    public void run() {
        while( true) {
            String packet = null;
            // see if some unoutputed packet is here , if there write.
            synchronized (this) {
                if(writePackets.size() > 0) {
                    packet = writePackets.remove(0);
                }
            }
            if(packet!=null) {
                // write it
                try {
                    os.write(packet.getBytes());
                } catch(IOException ioe) {
                    Log.d(LOGTAG , "IO Exception");
                }
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ie) {
                    Log.d(LOGTAG,"Interrupted Exception") ;
                }
            }
        }
    }

    public void addPacket(String xml) {
        // adds a packet here .
        writePackets.add(xml);
    }
}
