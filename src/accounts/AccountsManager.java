package accounts;

import android.util.Log;

import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 16/11/12
 * Time: 5:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccountsManager implements Serializable {

    static ArrayList<Account> accountStore = new ArrayList<Account>();

    static AccountsManager ACCOUNTS_MANAGER = new AccountsManager();
    static int idTracker = 0;

    private AccountsManager()
    {
         storageReader t = new storageReader();
         new Thread(t).start();
    }

    protected void finalize()
    {
       // storageWriter t = new storageWriter();
        //new Thread(t).start();

        persistentSave();
    }

    public static AccountsManager getInstance()
    {
        return ACCOUNTS_MANAGER;
    }

    static void prefetch()
    {
        try
        {
             ObjectInputStream os = new ObjectInputStream(new FileInputStream("Vartalap.ser"));
             Account currAccount = null;

             if(os != null)
             {
                  while((currAccount = (Account)os.readObject()) != null)
                  {
                       accountStore.add(currAccount);
                  }
             }
        }
        catch (Exception e)
        {
             Log.d("Vartalaap Persistent save exception:",e.toString());
        }
    }

    static void persistentSave()
    {
        try
        {
           // File F =
            FileOutputStream fs = new FileOutputStream("Vartalap.ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);

            for(Account currAccount: accountStore)
            {
                 os.writeObject(currAccount);
            }
            os.close();
        }
        catch (Exception e)
        {
            Log.d("Vartalaap Persistent save exception:",e.toString());
        }
    }

    public int addAccount(Account acct)
    {
        String currJID = acct.getJID();
        Boolean exists = false;
        int currAID = -1;

        for(Account currAcct:accountStore)
        {
             if(currAcct.getJID().equals(currJID))
             {
                 exists = true;
                 currAID = currAcct.getAccountID();
                 break;
             }
        }

        if(exists == false)
        {
            acct.setAccountID(idTracker++);
            accountStore.add(acct);
            currAID = acct.getAccountID();
        }

        return(currAID);
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
