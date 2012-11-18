package message;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import login.GlobalContext;

import java.util.ArrayList;

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
    private  static  ArrayList<MessageActivityData> chats = new ArrayList<MessageActivityData>();

    public static void startChat(int accountID , String to) {
        MessageActivityData activityData = getMessageActivityData(accountID, to);
        if(activityData == null) {
            MessageActivityData mData = new MessageActivityData();
            mData.from = to;
            mData.accountID = accountID;
            mData.message = "";
            activityData = mData;
            chats.add(mData);
        }
        MessageActivity.setActivityData(activityData);
        Intent intent = new Intent(GlobalContext.getContext(),MessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        GlobalContext.getContext().startActivity(intent);
    }

    public static MessageActivityData getMessageActivityData(int accountID , String from) {
        MessageActivityData activityData = null;
        for(int i = 0 ; i < chats.size() ; i++) {
            if(chats.get(i).from.equalsIgnoreCase(from) && chats.get(i).accountID.equals(accountID)) {
                activityData = chats.get(i);
                break;
            }
        }
        return  activityData;
    }

}
