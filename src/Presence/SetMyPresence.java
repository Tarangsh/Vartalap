package Presence;

import IO.HandleIO;
import IO.UntrackedAccountException;
import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: vineet
 * Date: 16/11/12
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class SetMyPresence {

    private static final String LOGTAG = "SetMyPresence";

    public static void setPresence ( Presence presence , int accountID) {
         // make a presence stanza and call HandleIO.sendPacket(..)
        try {
            HandleIO.sendPacket(makeStanza(accountID), accountID);
        } catch (UntrackedAccountException uae) {
            Log.d(LOGTAG,"Fatal error : Untracked account");
        }
    }

    private static String makeStanza(int accountID ) {
        Log.d(LOGTAG, "inside make presence stanza");
        String xml = "";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element element = document.createElement("presence");
            element.setAttribute("xml:lang","en");
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter sw = new StringWriter();
            t.transform(new DOMSource(document), new StreamResult(sw));
            xml = sw.toString();
            Log.d(LOGTAG,xml);
        } catch ( ParserConfigurationException pce) {
            Log.d(LOGTAG,"ParserConfigurationException + " + pce.toString());
        } catch (TransformerConfigurationException tce) {
            Log.d(LOGTAG,"TransformerConfigurationException" + tce.toString());
        } catch (TransformerException te) {
            Log.d(LOGTAG,"Transformer Exception " + te.toString());
        }
        return xml;
    }
}
