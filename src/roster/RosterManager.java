package roster;

import accounts.Account;
import accounts.AccountsManager;
import android.util.Log;
import contact.Contact;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: tarang
 * Date: 15/11/12
 * Time: 6:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class RosterManager {
    private ArrayList<Contact> contactsList = new ArrayList<Contact>();

    public static RosterManager ROSTER_MANAGER = new RosterManager();

    public static RosterManager getInstance() {
        return ROSTER_MANAGER;
    }

    public void insert(String JID, int acctID, String presence, String image)
    {
        boolean exists = false;

        Contact contact = new Contact();
        contact.setJID(JID);
        contact.setAcctID(acctID);
        contact.setPresence(presence);
        contact.setImage(image);

        for(Contact currContact: contactsList)
        {
            if(currContact.getJID().equals(JID))
                exists = true;
        }

        if(exists == false)
            contactsList.add(contact);
    }

    public void reset() {
        contactsList.clear();
    }

    public void pushRosterPacket(String packet, String acctID)
    {
        //insert in the list from here
    }

    public ArrayList<Contact> getContactsList()
    {
        return contactsList;
    }

    public void updatePresence(String jid)
    {

    }

    public void testMethod()
    {
        Contact currContact;
        AccountsManager ACCOUNTS_MANAGER = AccountsManager.getInstance();
        Account currAcct = new Account("tarang@gmail.com");
        ACCOUNTS_MANAGER.addAccount(currAcct);

        for(int i=0;i<20;i++)
        {
            currContact =  new Contact();
            currContact.setAcctID(0);
            currContact.setJID("JID"+i+"@gmail.com");
            currContact.setPresence("Available");
            currContact.setImage("//home//tarang//IdeaProjects//directi//Vartalap//res//drawable-hdpi//icon.png");

            contactsList.add(currContact);
        }


    }

    public void testMethod2()
    {
        Contact currContact;
        reset();

        for(int i=0;i<10;i++)
        {
            currContact =  new Contact();
            currContact.setAcctID(0);
            currContact.setJID("JID"+i+"@outlook.com");
            currContact.setPresence("Available");
            currContact.setImage("//home//tarang//IdeaProjects//directi//Vartalap//res//drawable-hdpi//icon.png");

            contactsList.add(currContact);
        }


    }

    public void processRosterList(String rosterListStanza)
    {
        try
        {
            String Sample = "<iq id=\"tarang_roster\" type=\"result\"><query xmlns=\"jabber:iq:roster\"><item jid=\"aaron.dp@directi.com\" name=\"aaron.dp@directi.com\" subscription=\"both\"><group></group></item><item jid=\"gaurav.m@directi.com\" name=\"gaurav.m@directi.com\" subscription=\"both\"><group>Android Team</group></item><item jid=\"gaurav.mi@directi.com\" name=\"gaurav.mi@directi.com\" subscription=\"both\"><group>Android Team</group></item><item jid=\"hitesh.g@directi.com\" name=\"hitesh.g@directi.com\" subscription=\"both\"><group></group></item><item jid=\"kashif.ra@directi.com\" name=\"kashif.ra@directi.com\" subscription=\"both\"><group>Android Team</group></item><item jid=\"kinnari.g@directi.com\" name=\"kinnari.g@directi.com\" subscription=\"both\"><group></group></item><item jid=\"manoj.n@directi.com\" name=\"manoj.n@directi.com\" subscription=\"both\"><group></group></item><item jid=\"naresh.k@directi.com\" name=\"naresh.k@directi.com\" subscription=\"both\"><group>Android Team</group></item><item jid=\"pawan.m@directi.com\" name=\"pawan.m@directi.com\" subscription=\"both\"><group>Android Team</group></item><item jid=\"prerna.ar@directi.com\" name=\"prerna.ar@directi.com\" subscription=\"both\"><group>Android Team</group></item><item jid=\"rajat.g@directi.com\" name=\"rajat.g@directi.com\" subscription=\"both\"><group></group></item><item jid=\"rakesh.k@directi.com\" name=\"rakesh.k@directi.com\" subscription=\"both\"><group>other contacts</group></item><item jid=\"reema.v@directi.com\" name=\"reema.v@directi.com\" subscription=\"both\"><group></group></item><item jid=\"sandeep.k@directi.com\" name=\"sandeep.k@directi.com\" subscription=\"both\"><group></group></item><item jid=\"vineet.p@directi.com\" name=\"vineet.p@directi.com\" subscription=\"both\"><group>Android Team</group></item><item jid=\"vipin.t@directi.com\" name=\"vipin.t@directi.com\" subscription=\"both\"><group>Android Team</group></item><item jid=\"vrunda.b@directi.com\" name=\"vrunda.b@directi.com\" subscription=\"both\"><group></group></item></query></iq>";
            StringReader stringReader = new StringReader(Sample);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            XmlPullParser pullParser = factory.newPullParser();
            pullParser.setInput(stringReader);

            int eventType = pullParser.getEventType();
            String tag ;
            String message = "";
            String contactJID = "";

            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                  tag = pullParser.getName();

                  if(tag != null && tag.equals("item"))
                  {
                      int count = pullParser.getAttributeCount();
                      int iter = 0;

                      while(iter < count)
                      {
                           if(pullParser.getAttributeName(iter).equalsIgnoreCase("jid"))
                           {
                               contactJID = pullParser.getAttributeValue(iter);
                               insert(contactJID,0,"Available","");
                           }

                           iter++;
                      }
                  }

                  eventType = pullParser.next();
            }

            //runOnUiThread(new Runnable() {
              //  public void run() {

//stuff that updates ui

               // }
            //});

            DisplayRosterActivity.updateData();


        }
        catch (Exception e)
        {
            Log.d("Vartalap Exception Roster IQ Processing:",e.toString());
        }

    }

}
