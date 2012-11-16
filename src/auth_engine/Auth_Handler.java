package auth_engine;

import Accounts.Account;
import Accounts.AccountsManager;
import android.util.Log;

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
            //AuthEngine AUTHENGINE = AuthEngine.getInstance();
            AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
            int currID;



          /*  SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            Socket socket = sslSocketFactory.createSocket("talk.google.com",5223);
            socket.setSoTimeout(0);
            socket.setKeepAlive(true);
            */
            Account CurrAccount = new Account(JID);
            CurrAccount.setPassword(Password);

            currID = ACCOUNTS_MANAGER.addAccount(CurrAccount);



           // AUTHENGINE.gtalkAuth(socket,JID,Password);
            //AUTHENGINE.gtalkAuth(socket.getInputStream(),socket.getOutputStream(),Params[0],Params[1]);
            //   AUTHENGINE.pingpongAuth(socket.getInputStream(),socket.getOutputStream(),Params[0],Params[1]);
        }
        catch (Exception e)
        {
            Log.d("Auth Exception", e.toString());
        }
    }
}
