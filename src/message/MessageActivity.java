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

/**
 * Created with IntelliJ IDEA.
 * User: vineet
 * Date: 16/11/12
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageActivity extends Activity implements View.OnClickListener {

    String message;
    int accountID;
    String to;
    private static final String LOGTAG = "MessageActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);
        Button sendButton = (Button) findViewById(R.id.sendbutton);
        sendButton.setOnClickListener(this);
        Intent intent = getIntent();
        accountID = intent.getIntExtra(SendActivity.ACCOUNT_ID,-1);
        to = intent.getStringExtra(SendActivity.TO);
    }

    public void onClick(View view ) {
        String xml = MakeMessageStanza.getMessageStanza(message,accountID,to);
        try {
            HandleIO.sendPacket(xml,accountID);
        } catch (UntrackedAccountException uae) {
            Log.d(LOGTAG,"Untracked account exception");
        }
    }
}