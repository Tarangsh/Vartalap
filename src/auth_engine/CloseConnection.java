package auth_engine;

import accounts.Account;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 5/12/12
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class CloseConnection implements Runnable{

    Account currAccount;

    public CloseConnection(Account act)
    {
          currAccount = act;
    }

    public void run()
    {
        currAccount.logout();
    }
}
