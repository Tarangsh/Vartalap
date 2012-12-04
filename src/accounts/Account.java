package accounts;

import android.util.Log;
import auth_engine.AuthEngine;
import roster.RosterManager;

import javax.net.ssl.SSLSocketFactory;
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
    int status;



    public Account(String jID)
    {
        JID = jID;
        accountID = -1;
        status = 0;
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

    public int getAccountID()
    {
        return accountID;
    }

    public String getJID()
    {
        return JID;
    }

    public String getPassword()
    {
        return password;
    }

    public InputStream getInputStr()
    {
        return inStream;
    }

    public OutputStream getOutputStr()
    {
        return outStream;
    }

    public int getStatus()
    {
        return status;
    }

    public void login()
    {
        Log.d("Vartalap","Login Clicked");
        try
        {
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            if(JID.contains("gmail"))
            {
                Log.d("Vartalap","Gtalk Auth");

                Socket socket = sslSocketFactory.createSocket("talk.google.com",5223);
                socket.setSoTimeout(0);
                socket.setKeepAlive(true);

                inStream = socket.getInputStream();
                outStream = socket.getOutputStream();

              //  IQProcessor IQ_PROCESSOR = IQProcessor.getInstance();

                //HandleIO.trackAccount(accountID);
               // IQ_PROCESSOR.startProcessing();

                if(AuthEngine.getInstance().gtalkAuth(accountID) == true)
                    status = 1;
            }
            else
            {
                Log.d("Vartalap","pingpong auth");
                Socket socket = new Socket("10.10.1.31",5222);
               // sslSocketFactory.createSocket("10.10.1.31",5222);
                socket.setSoTimeout(0);
                socket.setKeepAlive(true);

                inStream = socket.getInputStream();
                outStream = socket.getOutputStream();

                //IQProcessor IQ_PROCESSOR = IQProcessor.getInstance();

                //HandleIO.trackAccount(accountID);
                //IQ_PROCESSOR.startProcessing();

                if(AuthEngine.getInstance().pingpongAuth(accountID) == true)
                    status = 1;
            }
        }
        catch(Exception e)
        {
            Log.d("Login Exception",e.toString());
        }
    }

    public void logout()
    {
        try
        {
            RosterManager ROSTER_MANAGER = RosterManager.getInstance();
           // PrintWriter pwOutStream = new PrintWriter(outStream,true);
         //   pwOutStream.println("</stream:stream>");

            if(socket != null)
                socket.close();

            socket = null;
            inStream = null;
            outStream = null;
            status = 0;

            ROSTER_MANAGER.logoutRoster(accountID);
        }
        catch(Exception e)
        {
            Log.d("Logout Exception",e.toString());
        }
    }


}
