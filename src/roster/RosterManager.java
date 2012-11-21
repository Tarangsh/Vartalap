package roster;

import Accounts.Account;
import Accounts.AccountsManager;
import contact.Contact;

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
        Contact contact = new Contact();
        contact.setJID(JID);
        contact.setAcctID(acctID);
        contact.setPresence(presence);
        contact.setImage(image);

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

}
