package accounts;

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

    private AccountsManager()
    {}

    public static AccountsManager getInstance()
    {
        return ACCOUNTS_MANAGER;
    }

    public int addAccount(Account acct)
    {
        acct.setAccountID(idTracker++);
        accountStore.add(acct);

        return(acct.getAccountID());
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

    public Account getAccount(String jid)
    {
        for(Account currAcct: accountStore)
        {
            if(currAcct.JID.equals(jid))
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

    public ArrayList<Account> getAccountStore()
    {
        return accountStore;
    }

    public void testmethod()
    {
         Account act = new Account("jid1@gmail.com");
         ACCOUNTS_MANAGER.addAccount(act);

        Account act2 = new Account("jid2@gmail.com");
        ACCOUNTS_MANAGER.addAccount(act2);
    }

}
