package IO.InputBuffer;

import message.IncomingMessage;
import Presence.SetContactPresence;
import android.util.Log;
import IQ.IQProcessor;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: vineet
 * Date: 16/11/12
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReadStream implements  Runnable {

    InputStream is ;
    private static final String LOGTAG = "ReadStream";
    int accountID;

    public ReadStream(InputStream is ,int accountID) {
        this.is = is;
        this.accountID = accountID;
    }

    public void run() {
        while(true) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String xml = br.readLine();
                if(xml != null) {
                    StringReader stringReader = new StringReader(xml);
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser pullParser = factory.newPullParser();
                    pullParser.setInput(stringReader);
                    int eventType = pullParser.getEventType();
                    String tag = "default";
                    boolean done = false;
                    while(eventType!= XmlPullParser.END_DOCUMENT) {
                        if( (eventType == XmlPullParser.START_TAG) && (!done)){
                            tag = pullParser.getName();
                            Log.d(LOGTAG,"got tag " );
                            Log.d(LOGTAG,"The tag is " );
                            Log.d(LOGTAG,tag);
                            done = true;
                        }
                        eventType = pullParser.next();
                    }
                    if(tag.equalsIgnoreCase("presence")){
                        SetContactPresence.pushPresencePacket(xml,accountID);
                    } else if ( tag.equalsIgnoreCase("message")) {
                        IncomingMessage.pushMessagePacket(xml,accountID);
                    } else {
                        IQProcessor.pushPacket(xml, accountID);
                        //Log.d(LOGTAG,"calling relevant method with xml");
                        //Log.d(LOGTAG,xml);
                    }
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ie) {
                        Log.d(LOGTAG,"Interrupted Exception " + ie.toString());
                    }
                }
            } catch ( XmlPullParserException xppe) {
                Log.d(LOGTAG,"XmlPullParserException : " + xppe.toString());
            } catch ( IOException ioe) {
                Log.d(LOGTAG,"IOException : " + ioe.toString());
            }
        }
    }
}
