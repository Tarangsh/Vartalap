package accounts;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import auth_engine.Auth_Handler;
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

    private static ArrayList<Account> displayData;
    private static ListView accountsList;
    public static DisplayAccountsActivity ACCOUNTS_ACTIVITY;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts);
        ACCOUNTS_ACTIVITY = this;

        AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
       // ACCOUNTS_MANAGER.testmethod();
        displayData = ACCOUNTS_MANAGER.getAccountStore();

        ListView listView = (ListView) findViewById(R.id.acctlist);
        accountsList = listView;

        accountsListAdapter adapter = new accountsListAdapter(DisplayAccountsActivity.this, displayData);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long id) {
                onListItemClick((ListView) adapterView, view, position, id);
            }
        });
    }

    public void onListItemClick(ListView view, View v, int position, long id) {
              Account currAccount = (Account)view.getItemAtPosition(position);
              int status = currAccount.getStatus();

              if(status== 1)
              {
                  currAccount.logout();
                  updateData();
              }
              else
              {
                  Auth_Handler t = new Auth_Handler(currAccount.getJID(),currAccount.getPassword());
                  new Thread(t).start();
              }

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

    public static void updateData() {
        accountsListAdapter adapter = (accountsListAdapter) accountsList.getAdapter();

        if(adapter != null)
            adapter.updateData(displayData);
        else
            Log.d("ADAPTERNULL","TRUE");
    }
}
