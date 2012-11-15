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

    public AccountsManager getInstance()
    {
        return ACCOUNTS_MANAGER;
    }

    public void addAccount(Account acct)
    {
        accountStore.add(acct);
    }

    public Account getAccount(String acctID)
    {
        for(Account currAcct: accountStore)
        {
            if(currAcct.accountID.equals(acctID))
                return currAcct;
        }
    }

    public InputStream getInputStr(String acctID)
    {
        for(Account currAcct: accountStore)
        {
            if(currAcct.accountID.equals(acctID))
                return currAcct.inStream;
        }
    }

    public OutputStream getOutStr(String acctID)
    {
        for(Account currAcct: accountStore)
        {
            if(currAcct.accountID.equals(acctID))
                return currAcct.outStream;
        }
    }

}
