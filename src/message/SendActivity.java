package message;

import android.content.Context;
import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: vineet
 * Date: 16/11/12
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class SendActivity {

    public static final String TO = "To";
    public static final String ACCOUNT_ID = "ACCOUNT_ID" ;

    public static void startChat(Context context , int accountID , String to) {
        Intent intent = new Intent(context,MessageActivity.class);
        intent.putExtra(TO,to);
        intent.putExtra(ACCOUNT_ID,accountID);
        context.startActivity(intent);
        // prevent multiple instances when user does back and clicks the same roster item again .
    }

}
