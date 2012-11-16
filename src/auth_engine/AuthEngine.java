package auth_engine;

//import com.sun.xml.internal.messaging.saaj.util.*;

//import java.awt.geom.Dimension2D;

//import EncodingEngine.Base64;

import Accounts.Account;
import Accounts.AccountsManager;
import XmlEngine.XmlDoc;
import android.util.Base64;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 6/11/12
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthEngine {
    public static  AuthEngine AUTH_ENGINE = new AuthEngine();
    private byte[] inBuffer = new byte[2000];

    public static AuthEngine getInstance()
    {
        return  AUTH_ENGINE;
    }

    public String getStreamTag(String JID)
    {
        try
        {
            HashMap<String,String> streamAttributes = new HashMap<String,String>();
            streamAttributes.put("xmlns:stream","http://etherx.jabber.org/streams");
            streamAttributes.put("from",JID);
            streamAttributes.put("xmlns","jabber:client");
            streamAttributes.put("to",JID.split("@")[1]);
            streamAttributes.put("version","1.0");

            XmlDoc Stream = new XmlDoc("stream:stream",streamAttributes);
            return(Stream.getDocument().split("\\/>")[0]+">");

        }
        catch (Exception e)
        {
            Log.d("XML Exception",e.toString());
        }
        return null;
    }

    public String getAuthTag(String JID,String password,String mechanism)
    {
        HashMap<String,String> authAttributes = new HashMap<String, String>();
        authAttributes.put("xmlns","urn:ietf:params:xml:ns:xmpp-sasl");
        authAttributes.put("mechanism",mechanism);

        String uid = '\0'+JID.split("@")[0]+'\0'+password; //Put ur password n jid;
       // String Hash = new String(com.sun.xml.internal.messaging.saaj.util.Base64.encode(uid.getBytes()));
        String Hash = new String(Base64.encode(uid.getBytes(),0));


        XmlDoc auth = new XmlDoc("auth",authAttributes,Hash);
        return(auth.getDocument());
    }

    public String getStartTlsTag()
    {
        HashMap<String,String> tlsAttributes = new HashMap<String, String>();
        tlsAttributes.put("xmlns","urn:ietf:params:xml:ns:xmpp-tls");

        XmlDoc tlsTag = new XmlDoc("starttls",tlsAttributes);

        return(tlsTag.getDocument());

    }

    public String getBindTag()
    {

        HashMap<String,String> bindAttributes = new HashMap<String, String>();
        bindAttributes.put("type","set");
        bindAttributes.put("id","tn281v37");

        XmlDoc bindTag = new XmlDoc("iq",bindAttributes);

        bindAttributes.clear();
        bindAttributes.put("xmlns","urn:ietf:params:xml:ns:xmpp-bind");

        bindTag.addElement("bind",bindAttributes);
        return bindTag.getDocument();

    }

    public String getSessTag(String id,String to)
    {
        HashMap<String,String> sessAttributes = new HashMap<String, String>();
        sessAttributes.put("id",id);
        sessAttributes.put("to",to);
        sessAttributes.put("type","set");

        XmlDoc sessTag = new XmlDoc("iq",sessAttributes);

        sessAttributes.clear();
        sessAttributes.put("xmlns","urn:ietf:params:xml:ns:xmpp-session");

        sessTag.addElement("session",sessAttributes);
        return sessTag.getDocument();
    }

    public  String getRosterReqTag(String JID,String id)
    {
        HashMap<String,String> reqRosterAttributes = new HashMap<String, String>();
        //reqRosterAttributes.put("from","JID");
        reqRosterAttributes.put("type","get");
        reqRosterAttributes.put("id",id);

        XmlDoc reqRosterTag = new XmlDoc("iq",reqRosterAttributes);

        reqRosterAttributes.clear();
        reqRosterAttributes.put("xmlns","jabber:iq:roster");

        reqRosterTag.addElement("query",reqRosterAttributes);
        return reqRosterTag.getDocument();
    }

    public boolean pingpongAuth(int acctID)
    {
        try
        {
            AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
            Account currAccount = ACCOUNTS_MANAGER.getAccount(acctID);
            InputStream inStream = currAccount.getInputStr();
            OutputStream outStream = currAccount.getOutputStr();
            String JID = currAccount.getJID();
            String password = currAccount.getPassword();


            PrintWriter pwOutStream = new PrintWriter(outStream,true);
            String srvInput;
            String toSrv;

            toSrv = getStreamTag(JID);

            pwOutStream.println(toSrv);
            Log.d("ToServer",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                Log.d("FromServer",srvInput);

                if(srvInput.contains("features"))
                    break;
            }


            toSrv = getAuthTag(JID,password,"PLAIN");
            pwOutStream.println(toSrv);
            Log.d("ToServer",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                Log.d("FromServer",srvInput);

                if(srvInput.contains("success"))
                    break;
            }

            toSrv = getBindTag();

            pwOutStream.println(toSrv);
            Log.d("ToServer",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                Log.d("FromServer",srvInput);

                if(srvInput.contains("jid"))
                    return true;
            }


        }
        catch (Exception e)
        {
            Log.d("XML Exception",e.toString());
        }

        return false;
    }



    public boolean gtalkAuth(int acctID)
    {
        try
        {
            AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
            Account currAccount = ACCOUNTS_MANAGER.getAccount(acctID);
            InputStream inStream = currAccount.getInputStr();
            OutputStream outStream = currAccount.getOutputStr();
            String JID = currAccount.getJID();
            String password = currAccount.getPassword();

            PrintWriter pwOutStream = new PrintWriter(outStream,true);
            String srvInput;
            String toSrv;


            toSrv = getStreamTag(JID);

            pwOutStream.println(toSrv);
            Log.d("Communication To Server",toSrv);

            inStream.read(inBuffer);
            do
            {
                srvInput = new String(inBuffer);
                Log.d("Communication From Server",srvInput);

                if(srvInput.contains("features"))
                    break;
            }while(inStream.read(inBuffer) > 0);

            toSrv = getAuthTag(JID,password,"PLAIN");
            pwOutStream.println(toSrv);
            Log.d("Communication To Server",toSrv);


            for(int i=0;i<20000;i++)
                inBuffer[i] = 0;

            inStream.read(inBuffer);
            do
            {
                srvInput = new String(inBuffer);
                Log.d("Communication From Server",srvInput);

                if(srvInput.contains("success"))
                    break;
            }while(inStream.read(inBuffer) > 0);


            toSrv = getStreamTag(JID);

            pwOutStream.println(toSrv);
            Log.d("Communication To Server",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                Log.d("Communication From Server",srvInput);

                if(srvInput.contains("features"))
                    break;
            }

            toSrv = getBindTag();

            pwOutStream.println(toSrv);
            Log.d("Communication To Server",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                Log.d("Communication From Server",srvInput);

                if(srvInput.contains("jid"))
                    break;
            }

            toSrv = getSessTag("id"+JID.split("@")[0],JID.split("@")[1]);
            // toSrv = getSessTag("sess_1",JID.split("@")[1]);

            pwOutStream.println(toSrv);
            Log.d("Communication To Server",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                Log.d("Communication From Server",srvInput);

                if(srvInput.contains("params"))
                    break;
            }

            toSrv = getRosterReqTag(JID,JID.split("@")[0]+"_roster");

            pwOutStream.println(toSrv);
            Log.d("Communication To Server",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                Log.d("Communication From Server",srvInput);

                //  if(srvInput.contains("/iq>"))
                //    break;
            }


        }
        catch (Exception e)
        {
            Log.d("Auth Exception",e.toString());
        }

        return false;
    }

    public static void pushAuthPacket(String packet, int AcctID)
    {

    }



}



