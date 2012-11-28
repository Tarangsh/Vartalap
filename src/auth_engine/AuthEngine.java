package auth_engine;

//import com.sun.xml.internal.messaging.saaj.util.*;

//import java.awt.geom.Dimension2D;

//import EncodingEngine.Base64;

import XmlEngine.XmlDoc;
import accounts.Account;
import accounts.AccountsManager;
import android.util.Base64;
import android.util.Log;
import roster.RosterManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 6/11/12
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthEngine {
    public static  AuthEngine AUTH_ENGINE = new AuthEngine();
    private byte[] inBuffer = new byte[40000];
    static HashMap<Integer,LinkedBlockingDeque<String>> AUTH_REGISTER = new HashMap<Integer, LinkedBlockingDeque<String>>();

    public static AuthEngine getInstance()
    {
        return  AUTH_ENGINE;
    }

    public static HashMap<Integer,LinkedBlockingDeque<String>>  getAuthRegister()
    {
        return  AUTH_REGISTER;
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

        String eol = System.getProperty("line.separator");

        XmlDoc auth = new XmlDoc("auth",authAttributes,Hash.split(eol)[0]);

        String retval = "<auth xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\" mechanism=\"PLAIN\">"+Hash+"</auth>";
        //String retval = "<auth xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\" mechanism=\"PLAIN\">"+"AHRlY2hpZS50YW5nbwBTY2hhbXVkYTA5"+"</auth>";

        //return(auth.getDocument());
        return retval;
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
        Log.d("Vartalap","inside Pingpong auth");
        try
        {
            RosterManager ROSTER_MANAGER = RosterManager.getInstance();

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
            //HandleIO.sendPacket(toSrv,acctID);
            Log.d("ToServer",toSrv);
            Log.d("Vartalap Communication To Server",toSrv);


               while(inStream.read(inBuffer) > 0)
             {
                 srvInput = new String(inBuffer);
                 Log.d("FromServer",srvInput);

                 Log.d("Vartalap Communication From Server",srvInput);

                 if(srvInput.contains("features"))
                     break;
             }


            toSrv = getAuthTag(JID,password,"PLAIN");
            pwOutStream.println(toSrv);
            //HandleIO.sendPacket(toSrv,acctID);
            Log.d("Vartalap Communication To Server",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                Log.d("FromServer",srvInput);

                Log.d("Vartalap Communication From Server",srvInput);

                if(srvInput.contains("success"))
                    break;
            }

            toSrv = getBindTag();
            pwOutStream.println(toSrv);
            Log.d("Vartalap Communication To Server",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                Log.d("FromServer",srvInput);

                Log.d("Vartalap Communication From Server",srvInput);

                if(srvInput.contains("jid"))
                    break;
            }


            toSrv = getSessTag("id"+JID.split("@")[0],JID.split("@")[1]);
            // toSrv = getSessTag("sess_1",JID.split("@")[1]);

            pwOutStream.println(toSrv);
            Log.d("Vartalap Communication To Server",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                Log.d("Vartalap Communication From Server",srvInput);

                if(srvInput.contains("/iq>"))
                    break;
            }

            toSrv = getRosterReqTag(JID,JID.split("\\.")[0]+"_roster");

            pwOutStream.println(toSrv);
            Log.d("Vartalap Communication To Server",toSrv);
            srvInput="";

            while(inStream.read(inBuffer) > 0)
            {
                //srvInput = srvInput + (new String(inBuffer));
                srvInput = new String(inBuffer);
                Log.d("Vartalap Communication From Server",srvInput);


                if(srvInput.contains("/iq>"))
                    break;
            }

            ROSTER_MANAGER.processRosterList(srvInput);



            //pwOutStream.println(toSrv);
            //HandleIO.sendPacket(toSrv,acctID);
            //Log.d("ToServer",toSrv);
            /*
            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                Log.d("FromServer",srvInput);

                if(srvInput.contains("jid"))
                    return true;
            }
              */


        }
        catch (Exception e)
        {
            Log.d("XML Exception",e.toString());
        }

        return false;
    }



    public boolean gtalkAuth(int acctID)
    {
        Log.d("Vartalap","inside gtalkauth");
        try
        {
            LinkedBlockingDeque<String> readSrvQueue = new LinkedBlockingDeque<String>();
            AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
            Account currAccount = ACCOUNTS_MANAGER.getAccount(acctID);

           // AUTH_REGISTER.put((Integer)(acctID),readSrvQueue);
            String JID = currAccount.getJID();
            String password = currAccount.getPassword();

            InputStream inStream = currAccount.getInputStr();
            OutputStream outStream = currAccount.getOutputStr();
            PrintWriter pwOutStream = new PrintWriter(outStream,true);

            String srvInput;
            String toSrv;


            toSrv = getStreamTag(JID);
            pwOutStream.println(toSrv);
           // HandleIO.sendPacket(toSrv,acctID);
            Log.d("Vartalap Communication To Server",toSrv);


            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                Log.d("Vartalap Communication From Server",srvInput);


                if(srvInput.contains("features"))
                    break;
            }


            toSrv = getAuthTag(JID,password,"PLAIN");
            pwOutStream.println(toSrv);
            Log.d("Vartalap Communication To Server",toSrv);


            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                Log.d("Vartalap Communication From Server",srvInput);


                if(srvInput.contains("success"))
                    break;
            }

            toSrv = getStreamTag(JID);
            pwOutStream.println(toSrv);
            Log.d("Vartalap Communication To Server",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                Log.d("Vartalap Communication From Server",srvInput);


                if(srvInput.contains("features"))
                    break;
            }


            toSrv = getBindTag();

            pwOutStream.println(toSrv);
            Log.d("Vartalap Communication To Server",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                Log.d("Vartalap Communication From Server",srvInput);

                if(srvInput.contains("jid"))
                    break;
            }


            toSrv = getSessTag("id"+JID.split("@")[0],JID.split("@")[1]);
            // toSrv = getSessTag("sess_1",JID.split("@")[1]);

            pwOutStream.println(toSrv);
            Log.d("Vartalap Communication To Server",toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                Log.d("Vartalap Communication From Server",srvInput);

                if(srvInput.contains("/iq>"))
                    break;
            }



            toSrv = getRosterReqTag(JID,JID.split("\\.")[0]+"_roster");

            pwOutStream.println(toSrv);
            Log.d("Vartalap Communication To Server",toSrv);
            // srvInput="";

            while(inStream.read(inBuffer) > 0)
            {
                //srvInput = srvInput + (new String(inBuffer));
                srvInput = new String(inBuffer);
                Log.d("Vartalap Communication From Server",srvInput);


                  if(srvInput.contains("/iq>"))
                    break;
            }

            /*do
            {
                srvInput = readSrvQueue.take();
                Log.d("Vartalap Communication From Server",srvInput);

                if(srvInput.contains("features"))
                    break;
            }while(true);



            toSrv = getAuthTag(JID,password,"PLAIN");
            HandleIO.sendPacket(toSrv,acctID);
            Log.d("Vartalap Communication To Server",toSrv);

            do
            {
                srvInput = readSrvQueue.take();
                Log.d("Vartalap Communication From Server",srvInput);

                if(srvInput.contains("success"))
                    break;
                else if(srvInput.contains("failure"))
                {
                    Exception e = new Exception("Authentication Failure");
                    throw e;
                }
            }while(true);


            toSrv = getStreamTag(JID);
            HandleIO.sendPacket(toSrv,acctID);
            Log.d("Vartalap Communication To Server",toSrv);

            do
            {
                srvInput = readSrvQueue.take();
                Log.d("Vartalap Communication From Server",srvInput);

                if(srvInput.contains("features"))
                    break;
            }while(true);


            toSrv = getBindTag();
            HandleIO.sendPacket(toSrv,acctID);
            Log.d("Vartalap Communication To Server",toSrv);

            do
            {
                srvInput = readSrvQueue.take();
                Log.d("Vartalap Communication From Server",srvInput);

                if(srvInput.contains("jid"))
                    break;
            }while(true);


            toSrv = getSessTag("id"+JID.split("@")[0],JID.split("@")[1]);
            HandleIO.sendPacket(toSrv,acctID);
            Log.d("Vartalap Communication To Server",toSrv);

            do
            {
                srvInput = readSrvQueue.take();
                Log.d("Vartalap Communication From Server",srvInput);

                if(srvInput.contains("params"))
                    break;
            }while(true);


            toSrv = getRosterReqTag(JID,JID.split("@")[0]+"_roster");
            HandleIO.sendPacket(toSrv,acctID);
            Log.d("Vartalap Communication To Server",toSrv);


            do
            {
                srvInput = readSrvQueue.take();
                Log.d("Vartalap Communication From Server",srvInput);

                if(srvInput.contains("paramsjjjjj"))
                    break;
            }while(true);
               */

            return true;
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



