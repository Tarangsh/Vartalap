package auth_engine;

import android.util.Log;

import javax.net.ssl.SSLSocketFactory;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 15/11/12
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class Auth_Handler implements Runnable{
    String JID;
    String Password;

    public Auth_Handler(String jid, String pwd)
    {
         JID = jid;
         Password = pwd;
    }

    public void run()
    {
        try
        {
            AuthEngine AUTHENGINE = AuthEngine.getInstance();

            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            Socket socket = sslSocketFactory.createSocket("talk.google.com",5223);
            socket.setSoTimeout(0);
            socket.setKeepAlive(true);

            AUTHENGINE.gtalkAuth(socket,JID,Password);
            //AUTHENGINE.gtalkAuth(socket.getInputStream(),socket.getOutputStream(),Params[0],Params[1]);
            //   AUTHENGINE.pingpongAuth(socket.getInputStream(),socket.getOutputStream(),Params[0],Params[1]);
        }
        catch (Exception e)
        {
            Log.d("Auth Exception", e.toString());
        }
    }
}
