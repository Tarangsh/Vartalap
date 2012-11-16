package IO.InputBuffer;

import Message.IncomingMessage;
import Presence.SetContactPresence;
import android.util.Log;
import auth_engine.AuthEngine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import IQ.IQProcessor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.StringWriter;
import java.util.InputMismatchException;

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
            PushbackInputStream pbis = new PushbackInputStream(is);
            try {
                int r = pbis.read();
                if(r==-1) {
                    pbis.unread(-1);
                    try {
                        Thread.sleep(10);
                    } catch ( InterruptedException ie) {
                        Log.d(LOGTAG,"Interrupted exception");
                    }
                }  else {
                    pbis.unread(r);
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    try {
                        DocumentBuilder db = dbf.newDocumentBuilder();
                        Document d = db.parse(is);
                        Element e = d.getDocumentElement();
                        String tag = e.getTagName();
                        DOMSource source = new DOMSource(d);
                        StringWriter stringWriter = new StringWriter();
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        transformer.transform(source, new StreamResult(stringWriter));
                        String xml = stringWriter.toString();
                        /*
                                AuthEngine.pushAuthPacket(
                                */
                        if(tag.equalsIgnoreCase("presence")){
                            SetContactPresence.pushPresencePacket(xml,accountID);
                        } else if ( tag.equalsIgnoreCase("message")) {
                            IncomingMessage.pushMessagePacket(xml,accountID);
                        } else if (tag.equalsIgnoreCase("iq"))  {
                            IQProcessor.pushIQPacket(xml,accountID);
                        }
                    } catch ( ParserConfigurationException pce ) {
                        Log.d(LOGTAG , "Parser Configuration Exception ");
                    } catch ( SAXException saxe) {
                        Log.d(LOGTAG , "SAX Exception ");
                    } catch ( TransformerConfigurationException tce) {
                        Log.d(LOGTAG,"Transformer Configuration Exception");
                    } catch ( TransformerException te) {
                        Log.d(LOGTAG,"Transformer Exception");
                    }
                }
            } catch (IOException ioe) {
                Log.d(LOGTAG,"IO Exception ");
            }
        }
        // do polling
        //if(is.available())
        /*
       call 1 :
       auth_engine : AuthEngine.pushAuthPacket(xml,accountID) ;
       call 2 :
       Roster : RosterManager.pushRosterPacket(xml,accountID);
       call 3 :
       Presence : SetContactPresence.pushPresencePacket(xml,accountID);
       call 4 :
       Message : IncomingMessage.pushMessagePacket(xml,accountID);
        */
    }
}
