package IQ;

import android.util.Log;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 16/11/12
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class IQParser implements Runnable {

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    LinkedBlockingDeque<String> packetQueue;

    public IQParser(LinkedBlockingDeque<String> IQ_QUEUE)
    {
            packetQueue = IQ_QUEUE;
    }

    public void run()
    {
        try
        {
        String currPacket;
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom;

        while(true)
        {
            currPacket = packetQueue.take();

            Log.d("INCOMING PACKETS",currPacket);

            dom = db.parse(currPacket);
        }
        }
        catch (Exception e)
        {
            Log.d("IQ Exception",e.toString());
        }
    }
}
