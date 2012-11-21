package roster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.R;
import contact.Contact;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 15/11/12
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class contactListAdapter extends ArrayAdapter<Contact> {

    private final Context context;
    private static ArrayList<Contact> values;

    public contactListAdapter(Context context, ArrayList<Contact> values) {
        super(context, R.layout.roster, values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Contact CurrItem;
        CurrItem = values.get(position);

        String CurrPresence;
        CurrPresence = CurrItem.getPresence();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.roster_item, parent, false);

        ImageView availabilityImage = (ImageView) rowView.findViewById(R.id.availabilityimage);
        TextView jid = (TextView) rowView.findViewById(R.id.rowjid);
        TextView accountType = (TextView) rowView.findViewById(R.id.accounttype);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.rowimage);

        jid.setText(CurrItem.getJID().split("@")[0]);

        accountType.setText((CurrItem.getJID().split("@")[1]).split("\\.")[0]);
        //accountType.setText("gmail");

        if (CurrPresence.equals("Available"))
            availabilityImage.setImageResource(R.drawable.green);
        else if (CurrPresence.equals("Busy"))
            availabilityImage.setImageResource(R.drawable.red);
        else if (CurrPresence.equals("Offline"))
            availabilityImage.setImageResource(R.drawable.gray);
        else
            availabilityImage.setImageResource(R.drawable.orange);

        imageView.setImageResource(R.drawable.icon);


        return rowView;
    }

    public void updateData(ArrayList<Contact> List) {
        values = List;
        this.notifyDataSetChanged();
    }

}
