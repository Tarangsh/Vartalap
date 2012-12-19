package roster;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.example.R;
import contact.Contact;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 8/11/12
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayRosterActivity extends Activity{

    private ArrayList<Contact> displayData;
    private static ListView contactList;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roster);

        RosterManager ROSTER_MANAGER = RosterManager.getInstance();

        ListView listView = (ListView) findViewById(R.id.rosterlist);
        contactList = listView;

        //DerAdapter adapter = new DerAdapter(this, DisplayData);
        //listView.setAdapter(adapter);
    }
}
