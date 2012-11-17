package login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import auth_engine.Auth_Handler;
import com.example.R;
import message.MakeMessageStanza;

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

           //  new AuthHandler().execute(UID,password);

             Auth_Handler t = new Auth_Handler(UID,password);
             new Thread(t).start();

            /* Socket sock = new Socket("10.10.1.31",5222);
             Intent intent = new Intent(this, DisplayRosterActivity.class);
             startActivity(intent);

             ((TextView)findViewById(R.id.status)).setText("Signing In.....");


             if(AUTHENGINE.pingpongAuth(sock.getInputStream(),sock.getOutputStream(),UID+"@directi.com",password)==true)
             {
                 //Intent intent = new Intent(this, DisplayRosterActivity.class);
                 //startActivity(intent);
             }
             else
             {
                 ((TextView)findViewById(R.id.status)).setText("Wrong Credentials! Please Re-enter....");
             }*/
         }
         catch (Exception e)
         {
             Log.d("login Exception",e.toString());
         }
    }
}
