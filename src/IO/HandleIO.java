package IO;

import Accounts.AccountsManager;
import IO.InputBuffer.ReadStream;
import IO.OutputQueue.WriteStream;
import android.util.Log;
import message.MakeMessageStanza;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: vineet
 * Date: 16/11/12
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class HandleIO {

    public class AccountInfo {
        ReadStream rs;
        WriteStream ws;
        int accountID ;
        public AccountInfo( ReadStream rs , WriteStream ws , int accountID) {
            this.rs = rs;
            this.ws = ws;
            this.accountID = accountID;
        }
    }

    private static final String LOGTAG = "HandleIO";

    private static ArrayList<AccountInfo> accounts = new ArrayList<AccountInfo>();

    private static HandleIO mainObject = new HandleIO();

    public static void trackAccount(int accountID) {
        InputStream is = AccountsManager.getInstance().getInputStr(accountID);
        OutputStream os =  AccountsManager.getInstance().getOutStr(accountID);
        ReadStream rs = new ReadStream(is,accountID);
        Thread t1 = new Thread(rs);
        t1.start();
        WriteStream ws = new WriteStream(os,accountID);
        Thread t2 = new Thread(ws);
        t2.start();
        accounts.add(mainObject.new AccountInfo(rs,ws,accountID));
        // start a thread each for is and os.
    }

    public static void sendPacket(String xml , int accountID) throws UntrackedAccountException {
        AccountInfo ai = getAccountInfo(accountID);
        if(ai == null) {
            throw new UntrackedAccountException("this account is untracked : accountID = " + accountID);
        } else {
            WriteStream ws = ai.ws;
            ws.addPacket(xml);
        }
    }

    private static AccountInfo getAccountInfo(int accountID) {
        for(int i = 0 ; i < accounts.size() ; i++) {
            if(accounts.get(i).accountID == accountID) {
                return accounts.get(i);
            }
        }
        return null;
    }
}
