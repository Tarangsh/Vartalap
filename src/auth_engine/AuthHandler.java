package auth_engine;

import android.os.AsyncTask;
import android.util.Log;

import javax.net.ssl.SSLSocketFactory;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 9/11/12
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthHandler extends AsyncTask<String,Long,String> {

    protected void onPreExecute() {
        super.onPreExecute();
        //displayProgressBar("Downloading...");
    }


    protected String doInBackground(String... Params)
    {
        try
        {
            AuthEngine AUTHENGINE = AuthEngine.getInstance();

            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            Socket socket = sslSocketFactory.createSocket("talk.google.com",5223);
            socket.setSoTimeout(0);
            socket.setKeepAlive(true);

            AUTHENGINE.gtalkAuth(socket,Params[0],Params[1]);
            //AUTHENGINE.gtalkAuth(socket.getInputStream(),socket.getOutputStream(),Params[0],Params[1]);
         //   AUTHENGINE.pingpongAuth(socket.getInputStream(),socket.getOutputStream(),Params[0],Params[1]);

            return null;
        }
        catch (Exception e)
        {
            Log.d("Auth Exception",e.toString());
        }
        return null;
    }

    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(null);
        //updateProgressBar(values[0]);
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //dismissProgressBar();
    }


}
