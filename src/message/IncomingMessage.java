package message;

import android.content.Intent;
import android.util.Log;
import login.GlobalContext;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created with IntelliJ IDEA.
 * User: vineet
 * Date: 16/11/12
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class IncomingMessage {

    private static final String LOGTAG = "IncomingMessage";

    public static void pushMessagePacket(String xml , int accountID) {
        // update message activity
        // display notification
        try {
            String message = "";
            String from = "";
            StringReader stringReader = new StringReader(xml);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser pullParser = factory.newPullParser();
            pullParser.setInput(stringReader);
            int eventType = pullParser.getEventType();
            String tag ;
            while(eventType!= XmlPullParser.END_DOCUMENT) {
                if( (eventType == XmlPullParser.START_TAG)){
                    tag = pullParser.getName();
                    if(tag.equalsIgnoreCase("message")) {
                        int count = pullParser.getAttributeCount();
                        int iter = 0;
                        while(iter < count) {
                            if(pullParser.getAttributeName(iter).equalsIgnoreCase("from")) {
                                from = pullParser.getAttributeValue(iter);
                                break;
                            }
                            iter++;
                        }
                        while(eventType != XmlPullParser.TEXT) {
                            eventType = pullParser.next();
                        }
                        message = pullParser.getText();
                        break;
                    }
                }
                eventType = pullParser.next();
            }
            MessageActivityData activityData = SendActivity.getMessageActivityData(accountID, from);
            if(activityData==null) {
                MessageActivityData mData = new MessageActivityData();
                mData.from = from;
                mData.accountID = accountID;
                mData.message = "";
                activityData = mData;
            }
            Intent intent = new Intent(GlobalContext.getContext(),MessageActivity.class);

            String eol = System.getProperty("line.separator");
            activityData.message = activityData.message.concat(eol);
            activityData.message = activityData.message.concat(activityData.from);
            activityData.message = activityData.message.concat(" : ");
            activityData.message = activityData.message.concat(message);
            MessageActivity.setActivityData(activityData);
            GlobalContext.getContext().startActivity(intent);
        } catch ( XmlPullParserException xppe) {
            Log.d(LOGTAG,"XmlPullParserException : " + xppe.toString());
        } catch ( IOException ioe) {
            Log.d(LOGTAG,"IOException : " + ioe.toString());
        }

    }
}
