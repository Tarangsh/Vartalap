package IO.OutputQueue;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
    PrintWriter pw ;

    public WriteStream(OutputStream os , int accountID ) {
        this.os = os;
        pw = new PrintWriter(os,true);
        writePackets = new ArrayList<String>();
        this.accountID = accountID;
    }

    public void run() {
        while( true) {
            String packet = null;
            synchronized (this) {
                if(writePackets.size() > 0) {
                    packet = writePackets.remove(0);
                }
            }
            if(packet!=null) {
                // write it

                    //os.write(packet.getBytes());
                    //os.flush();
                    pw.println(packet);
                    pw.flush();
                    Log.d(LOGTAG,"1 packet written to outputstream at system time : " + System.currentTimeMillis());
                    Log.d(LOGTAG,"packet written was : ");
                    Log.d(LOGTAG,packet);
                    Log.d(LOGTAG,"this was the packet written ");

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
