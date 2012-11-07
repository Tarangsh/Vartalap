package AuthEngine;

//import com.sun.xml.internal.messaging.saaj.util.*;

//import java.awt.geom.Dimension2D;

//import EncodingEngine.Base64;

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

    public boolean pingpongAuth(InputStream inStream, OutputStream outStream,String JID,String password)
    {
        try
        {
            PrintWriter pwOutStream = new PrintWriter(outStream,true);
            String srvInput;
            String toSrv;

            toSrv = getStreamTag(JID);

            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                if(srvInput.contains("features"))
                    break;
            }


            toSrv = getAuthTag(JID,password,"PLAIN");
            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

                if(srvInput.contains("success"))
                    break;
            }

            toSrv = getBindTag();

            pwOutStream.println(toSrv);
            System.out.println(toSrv);

            while(inStream.read(inBuffer) > 0)
            {
                srvInput = new String(inBuffer);
                System.out.println(srvInput);

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



}



