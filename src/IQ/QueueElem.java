package IQ;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 19/11/12
 * Time: 5:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueueElem {
    String packet;
    int acctID;

    public QueueElem(String pack,int aid)
    {
        packet = pack;
        acctID = aid;
    }

    public String getPacket()
    {
        return packet;
    }

    public int getAcctID()
    {
        return acctID;
    }
}
