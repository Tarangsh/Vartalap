package IQ;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 16/11/12
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class IQProcessor {

    static IQProcessor IQ_PROCESSOR = new IQProcessor();
    static Queue<String> IQ_QUEUE = new LinkedList<String>();

    public static IQProcessor getInstance()
    {
        return IQ_PROCESSOR;
    }

    public static void pushIQPacket(String packet,int acctID)
    {

    }
}
