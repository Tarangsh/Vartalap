package accounts;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.example.R;
import login.DisplayLoginActivity;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 22/11/12
 * Time: 12:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayAccountsActivity extends ListActivity {

    private ArrayList<Account> displayData;
    private static ListView accountsList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts);

        AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
       // ACCOUNTS_MANAGER.testmethod();
        displayData = ACCOUNTS_MANAGER.getAccountStore();

        ListView listView = (ListView) findViewById(R.id.acctlist);
        accountsList = listView;

        accountsListAdapter adapter = new accountsListAdapter(DisplayAccountsActivity.this, displayData);

        listView.setAdapter(adapter);
    }

    public void onResume()
    {
        super.onResume();
        AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
        displayData = ACCOUNTS_MANAGER.getAccountStore();
        updateData();
    }

    public void addAccount(View view)
    {
        Intent intent = new Intent(this, DisplayLoginActivity.class);
        startActivity(intent);
    }

    public void updateData() {
        accountsListAdapter adapter = (accountsListAdapter) accountsList.getAdapter();

        if(adapter != null)
            adapter.updateData(displayData);
        else
            Log.d("ADAPTERNULL","TRUE");
    }
}
