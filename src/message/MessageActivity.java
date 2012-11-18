package message;

import IO.HandleIO;
import IO.UntrackedAccountException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.R;
import org.w3c.dom.Text;

/**
 * Created with IntelliJ IDEA.
 * User: vineet
 * Date: 16/11/12
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageActivity extends Activity implements View.OnClickListener {

    static MessageActivityData data;
    private static final String LOGTAG = "MessageActivity";
    static MessageActivity instance ;

    public MessageActivity() {
        super();
        instance = this;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);
        Button sendButton = (Button) findViewById(R.id.sendbutton);
        sendButton.setOnClickListener(this);
        TextView prevMessage = (TextView) findViewById(R.id.prevMessages);
        prevMessage.setText(data.message);
        TextView toName = (TextView) findViewById(R.id.toname);
        toName.setText(data.from);
    }

    public void onClick(View view ) {
        String newMessage = "";
        EditText currentMessage = (EditText) findViewById(R.id.currentMessage);
        newMessage = newMessage.concat(currentMessage.getText().toString());
        TextView prevMessage = (TextView) findViewById(R.id.prevMessages);
        String eol = System.getProperty("line.separator");
        data.message = data.message.concat(eol);
        data.message = data.message.concat(" Me :");
        data.message = data.message.concat(newMessage);
        data.message = data.message.concat(eol);
        prevMessage.setText(data.message);
        String xml = MakeMessageStanza.getMessageStanza(newMessage,data.accountID,data.from);
        try {
            HandleIO.sendPacket(xml,data.accountID);
        } catch (UntrackedAccountException uae) {
            Log.d(LOGTAG,"Untracked account exception");
        }
    }

    public static void setActivityData(MessageActivityData mData) {
        data = mData;
        EditText prevMessage = (EditText) instance.findViewById(R.id.prevMessages);
        prevMessage.setText(data.message);
        TextView toName = (TextView) instance.findViewById(R.id.toname);
        toName.setText(data.from);
        // set data in UI
    }
}