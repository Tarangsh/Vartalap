package message;

import accounts.AccountsManager;
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
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class MakeMessageStanza {

    private static final String LOGTAG = "MakeMessageStanza";

    public static String getMessageStanza(String message , int accountID,String to) {
        Log.d(LOGTAG,"inside get message stanza");
         String xml = "";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element element = document.createElement("message");
            element.setAttribute("from", AccountsManager.getInstance().getAccount(accountID).getJID());
            element.setAttribute("type", "chat");
            element.setAttribute("xml:lang","en");
            element.setAttribute("to",to);
            Long myLong = new Long(Math.round(Math.random()*1000000));
            element.setAttribute("id", myLong.toString());
            Element bodyElement = document.createElement("body");
            bodyElement.setTextContent(message);
            element.appendChild(bodyElement);
            document.appendChild(element);
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
