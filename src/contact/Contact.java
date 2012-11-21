package contact;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 15/11/12
 * Time: 6:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class Contact {
    String JID;
    int acctID;
    String presence;
    String image;

    public void setJID(String jid)
    {
        JID = jid;
    }

    public void setAcctID(int aid)
    {
        acctID = aid;
    }

    public void setPresence(String p)
    {
        presence = p;
    }

    public void setImage(String img)
    {
        image = img;
    }

    public String getJID()
    {
        return JID;
    }

    public int getAcctID()
    {
        return acctID;
    }

    public String getPresence()
    {
        return presence;
    }

    public String getImage()
    {
        return image;
    }
}
