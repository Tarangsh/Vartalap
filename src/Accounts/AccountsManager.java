package Accounts;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 16/11/12
 * Time: 5:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccountsManager {

    ArrayList<Account> accountStore = new ArrayList<Account>();

    static AccountsManager ACCOUNTS_MANAGER = new AccountsManager();
    static int idTracker = 0;

    public AccountsManager getInstance()
    {
        return ACCOUNTS_MANAGER;
    }

    public void addAccount(Account acct)
    {
        acct.setAccountID(idTracker++);
        accountStore.add(acct);
    }

    public Account getAccount(int acctID)
    {
        for(Account currAcct: accountStore)
        {
            if(currAcct.accountID == acctID)
                return currAcct;
        }
        return null;
    }

    public InputStream getInputStr(int acctID)
    {
        for(Account currAcct: accountStore)
        {
            if(currAcct.accountID == acctID)
                return currAcct.inStream;
        }
        return null;
    }

    public OutputStream getOutStr(int acctID)
    {
        for(Account currAcct: accountStore)
        {
            if(currAcct.accountID == acctID)
                return currAcct.outStream;
        }
        return null;
    }

}
