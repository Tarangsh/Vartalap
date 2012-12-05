package accounts;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 4/12/12
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class storageReader implements Runnable {

    public storageReader()
    {

    }

    public void run()
    {
        AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
        ACCOUNTS_MANAGER.prefetch();
    }
}
