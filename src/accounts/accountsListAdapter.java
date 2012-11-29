package accounts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 22/11/12
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class accountsListAdapter extends ArrayAdapter<Account>{

    private final Context context;
    private static ArrayList<Account> values;

    public accountsListAdapter(Context context, ArrayList<Account> values)
    {
        super(context, R.layout.accounts, values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Account CurrItem;
        CurrItem = values.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.accounts_item, parent, false);

        ImageView statusImage = (ImageView) rowView.findViewById(R.id.status_image);
        ImageView userImage = (ImageView) rowView.findViewById(R.id.uimage);
        TextView acctJID = (TextView) rowView.findViewById(R.id.acctjid);
        ImageView acctImage = (ImageView) rowView.findViewById(R.id.typeimage);

        acctJID.setText(CurrItem.getJID().split("@")[0]);

        if(CurrItem.getStatus() == 1)
            statusImage.setImageResource(R.drawable.green);
        else
            statusImage.setImageResource(R.drawable.gray);


        if (CurrItem.getJID().split("@")[1].split("\\.")[0].equals("gmail"))
            acctImage.setImageResource(R.drawable.gtalk2);
        else
            acctImage.setImageResource(R.drawable.pingponglogo);

        userImage.setImageResource(R.drawable.accounts_icon);


        return rowView;
    }

    public void updateData(ArrayList<Account> List)
    {
        values = List;
        this.notifyDataSetChanged();
    }
}
