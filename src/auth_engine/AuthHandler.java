package auth_engine;

import accounts.Account;
import accounts.AccountsManager;
import android.os.AsyncTask;
import android.util.Log;
import roster.RosterManager;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 9/11/12
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthHandler extends AsyncTask<String,Long,String> {

    String rosterData;

    protected void onPreExecute() {
        super.onPreExecute();
    }


    protected String doInBackground(String... Params)
    {
        try
        {
            String JID = Params[0];
            String Password = Params[1];

            AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
            int currID;

            Account CurrAccount = new Account(JID);
            CurrAccount.setPassword(Password);

            currID = ACCOUNTS_MANAGER.addAccount(CurrAccount);
            rosterData = ACCOUNTS_MANAGER.getAccount(currID).login();
        }
        catch (Exception e)
        {
            Log.d("Vartalap Auth Exception",e.toString());
        }
        return null;
    }

    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(null);
        //updateProgressBar(values[0]);
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        RosterManager ROSTER_MANAGER = new RosterManager();
        ROSTER_MANAGER.processRosterList(rosterData);
    }


}
