package roster;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.R;
import contact.Contact;
import login.GlobalContext;
import message.SendActivity;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 8/11/12
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayRosterActivity extends ListActivity {

    private ArrayList<Contact> displayData;
    private static ListView contactList;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roster);

        RosterManager ROSTER_MANAGER = RosterManager.getInstance();
        ROSTER_MANAGER.testMethod();
        displayData = ROSTER_MANAGER.getContactsList();

        ListView listView = (ListView) findViewById(R.id.rosterlist);
        contactList = listView;

        contactListAdapter adapter = new contactListAdapter(DisplayRosterActivity.this, displayData);

        GlobalContext.setContext(getApplicationContext());

        if(listView != null)
        {
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView adapterView, View view, int position, long id) {
                    onListItemClick((ListView) adapterView, view, position, id);
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
        RosterManager ROSTER_MANAGER = RosterManager.getInstance();
        displayData = ROSTER_MANAGER.getContactsList();
        updateData();
    }


    public void onListItemClick(ListView view, View v, int position, long id) {
        RosterManager ROSTER_MANAGER = RosterManager.getInstance();
        Contact currContact =  (Contact)view.getItemAtPosition(position);
        SendActivity.startChat(currContact.getAcctID(),currContact.getJID());

        //ROSTER_MANAGER.testMethod2();
        //displayData = ROSTER_MANAGER.getContactsList();
        //updateData();
       // Intent intent = new Intent(ChatApplication.getAppContext(), ChatBox.class);
       // intent.putExtra("buddyid", rosterItem.getBareJID());
     //   intent.putExtra("accountUID", rosterItem.getAccount());
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
    }

    public void updateData() {
        contactListAdapter adapter = (contactListAdapter) contactList.getAdapter();
        adapter.updateData(displayData);
    }
}
