package me.swax.framework.communication.mail;

import java.util.ArrayList;
import java.util.Arrays;

import javax.mail.FetchProfile;
import javax.mail.MessagingException;

import me.swax.framework.communication.mail.util.EmailComparator;

/**
 * @author Ralf Eichinger
 */
public class Folder
{
    public static final int READ_ONLY = javax.mail.Folder.READ_ONLY;

    public static final int READ_WRITE = javax.mail.Folder.READ_WRITE;

    ArrayList<Folder> subFolders = new ArrayList<Folder>();

    // private static Logger LOG = LoggerFactory.getLogger(Folder.class);
    javax.mail.Folder folder = null;

    public Folder(javax.mail.Folder folder)
    {
        this.folder = folder;
    }

    public String getFullName()
    {
        return folder.getFullName();
    }

    public String getName()
    {
        return folder.getName();
    }

    public void close(boolean expunge)
    {
        try
        {
            folder.close(expunge);
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void open(int mode)
    {
        try
        {
            if (mode != Folder.READ_ONLY && mode != Folder.READ_WRITE)
            {
                throw new MessagingException("Invalid mode");
            }
            folder.open(mode);
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<Message> getMessages()
    {
        ArrayList<Message> result = null;
        try
        {
            javax.mail.Message[] msgs = folder.getMessages();
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.FLAGS);
            fp.add(FetchProfile.Item.CONTENT_INFO);
            folder.fetch(msgs, fp);

            // sort messages by date
            EmailComparator comp = new EmailComparator();
            Arrays.sort(msgs, comp);

            // fill them into Message
            for (javax.mail.Message message : msgs)
            {
                if (result == null)
                {
                    result = new ArrayList<Message>();
                }
                result.add(new Message(message));
            }
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public boolean isOpen()
    {
        return folder.isOpen();
    }

    public void add(Folder subFolder)
    {
        subFolders.add(subFolder);
    }

    public ArrayList<Folder> getSubFolders()
    {
        return subFolders;
    }

}
