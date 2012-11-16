package Accounts;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 6/11/12
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Account {

    int accountID;
    String JID;
    String password;
    String token;
    InputStream inStream;
    OutputStream outStream;
    Socket socket;



    public Account(String jID)
    {
        JID = jID;
    }

    public void setAccountID(int aid)
    {
        accountID = aid;
    }

    public void setPassword(String pwd)
    {
        password = pwd;
    }

    public void setToken(String t)
    {
        token = t;
    }

    public void login()
    {
        if(JID.contains("gmail"))
        {

        }
        else
        {

        }
    }
}
