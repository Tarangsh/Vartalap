package login;

import accounts.AccountsManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import auth_engine.Auth_Handler;
import com.example.R;
import roster.DisplayRosterActivity;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 7/11/12
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayLoginActivity extends Activity{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        Button loginButton = (Button) findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                loginClick(v);
            }
        });
    }

    public void loginClick(View view)
    {
         try
         {
             //this.onStop();
             String UID;
             String password;
             //AuthEngine AUTHENGINE = AuthEngine.getInstance();

             UID = ((EditText)findViewById(R.id.inputname)).getText().toString();
             password = ((EditText)findViewById(R.id.inputpwd)).getText().toString();

             //UID = "tarang.s@directi.com";
             //password = "zP^Jxnx0";

             //UID = "dummy.android.chat@gmail.com";
             //password = "dummyand";

             //UID = "cadet.tarang@gmail.com";
             //password = "Schamuda09";


             // Intent intent = new Intent(this, DisplayRosterActivity.class);
            // startActivity(intent);

             AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();

             if(!(UID.trim().equals("")))
             {
                 Auth_Handler t = new Auth_Handler(UID,password);
                 new Thread(t).start();
                 Thread.sleep(3000);
             }




             Intent intent = new Intent(this, DisplayRosterActivity.class);
             startActivity(intent);
         }
         catch (Exception e)
         {
             Log.d("login Exception",e.toString());
         }
    }
}
