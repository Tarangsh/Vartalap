package IQ;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 16/11/12
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class IQProcessor {

    static IQProcessor IQ_PROCESSOR = new IQProcessor();
    static LinkedBlockingDeque<String> IQ_QUEUE = new LinkedBlockingDeque<String>();

    public static IQProcessor getInstance()
    {
        return IQ_PROCESSOR;
    }

    public static void pushIQPacket(String packet,int acctID)
    {
         IQ_QUEUE.add(packet);
    }

    public void startProcessing()
    {
         Thread parser = new Thread(new IQParser(IQ_QUEUE));
         parser.start();
    }
}
