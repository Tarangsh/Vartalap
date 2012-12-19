package IQ;

import android.util.Log;
import auth_engine.AuthEngine;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
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
    LinkedBlockingDeque<QueueElem> packetQueue;

    public IQParser(LinkedBlockingDeque<QueueElem> IQ_QUEUE)
    {
            packetQueue = IQ_QUEUE;
    }

    public void run()
    {
        try
        {
            QueueElem currElem;
            String currPacket;
            int currAcct;
            DocumentBuilder db = dbf.newDocumentBuilder();
            HashMap<Integer,LinkedBlockingDeque<String>> REGISTER = AuthEngine.getAuthRegister();
            LinkedBlockingDeque currQueue;

            Document dom;

            while(true)
            {
                currElem = packetQueue.take();
                currAcct = currElem.getAcctID();
                currPacket = currElem.getPacket();

                Log.d("INCOMING PACKETS",currPacket);

                if(REGISTER.containsKey(currAcct))
                {
                     REGISTER.get(currAcct).add(currPacket);
                }
                else
                {
                    Exception e = new Exception("Acct not Registered in AUTH_REGISTER");
                    throw e;
                }


               // dom = db.parse(currPacket);
            }
        }
        catch (Exception e)
        {
            Log.d("IQ Exception",e.toString());
        }
    }
}
