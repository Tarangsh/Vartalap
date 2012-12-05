package accounts;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 4/12/12
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class storageWriter implements Runnable{

    public storageWriter()
    {

    }

    public void run()
    {
        AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
        ACCOUNTS_MANAGER.persistentSave();
    }
}
