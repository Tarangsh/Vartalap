package Accounts;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 6/11/12
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Account {

    String accountID;
    String JID;
    InputStream inStream;
    OutputStream outStream;

    public Account(String aID,String jID)
    {
        accountID = aID;
        JID = jID;
        inStream = null;
        outStream = null;
    }

    public void login()
    {

    }

    public void auth()
    {

    }
}
