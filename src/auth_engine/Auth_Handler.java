package auth_engine;

import accounts.Account;
import accounts.AccountsManager;
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
    //AuthEngine AUTHENGINE = AuthEngine.getInstance();

    public Auth_Handler(String jid, String pwd)
    {
         JID = jid;
         Password = pwd;
    }

    public void run()
    {
        try
        {
            AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
            int currID;

            Account CurrAccount = new Account(JID);
            CurrAccount.setPassword(Password);

            currID = ACCOUNTS_MANAGER.addAccount(CurrAccount);
            ACCOUNTS_MANAGER.getAccount(currID).login();

        }
        catch (Exception e)
        {
            Log.d("Auth Exception", e.toString());
        }
    }
}
