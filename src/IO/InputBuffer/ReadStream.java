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
                StringReader stringReader = new StringReader(xml);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser pullParser = factory.newPullParser();
                pullParser.setInput(stringReader);
                int eventType = pullParser.getEventType();
                String tag = "default";
                int empty = 0;
                boolean done = false;
                while(eventType!= XmlPullParser.END_DOCUMENT) {
                    empty++;
                    if( (eventType == XmlPullParser.START_TAG) && (!done)){
                        tag = pullParser.getName();
                        Log.d(LOGTAG,"got tag " );
                        Log.d(LOGTAG,"The tag is " );
                        Log.d(LOGTAG,tag);
                        done = true;
                    }
                    eventType = pullParser.next();
                }
                if(empty <= 1) {
                    try {
                    Thread.sleep(10);
                    } catch (InterruptedException ie) {
                        Log.d(LOGTAG,"Interrupted Exception " + ie.toString());
                    }
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
            } catch ( XmlPullParserException xppe) {
                Log.d(LOGTAG,"XmlPullParserException : " + xppe.toString());
            } catch ( IOException ioe) {
                Log.d(LOGTAG,"IOException : " + ioe.toString());
            }
            /*
 } catch ( ParserConfigurationException pce ) {
     Log.d(LOGTAG , "Parser Configuration Exception : " + pce.toString());
 } catch ( SAXException saxe) {
     Log.d(LOGTAG , "SAX Exception : " + saxe.toString());
 } catch ( TransformerConfigurationException tce) {
     Log.d(LOGTAG,"Transformer Configuration Exception : " + tce.toString());
 } catch ( TransformerException te) {
     Log.d(LOGTAG,"Transformer Exception : " + te.toString());
 } catch (IOException ioe) {
     Log.d(LOGTAG,"IO Exception ");
 }
            */
        }
    }
}
