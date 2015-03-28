package me.swax.framework.communication.mail;

import java.io.IOException;
import java.util.Date;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;

import me.swax.framework.communication.mail.util.MailUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ralf Eichinger
 */
public class Message
{
    private static Logger LOG = LoggerFactory.getLogger(Message.class);

    private javax.mail.Message message = null;

    public Message(javax.mail.Message message)
    {
        this.message = message;
    }

    public int getMessageNumber()
    {
        return message.getMessageNumber();
    }

    public String getFrom()
    {
        String result = null;
        try
        {
            result = (message.getFrom() != null) ? message.getFrom()[0]
                    .toString() : "";
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public Date getDate()
    {
        Date result = null;
        try
        {
            result = message.getSentDate();
            if (result == null)
            {
                result = message.getReceivedDate();
            }
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public String getSubject()
    {
        String result = null;
        try
        {
            result = message.getSubject();
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public String getText()
    {
        String result = null;
        try
        {
            String contentType = message.getContentType();
            LOG.info("getText(): message contentType = '" + contentType + "'");
            // if it s plain text
            if (contentType.toUpperCase().startsWith("TEXT/PLAIN"))
            {
                // This is a standard plain text message.
                // e.g.: 'TEXT/PLAIN; charset=iso-8859-1'
                String text = message.getContent().toString();
                result = MailUtils.txt2html(text);
            }
            else if (contentType.toUpperCase().startsWith("MULTIPART/MIXED"))
            {
                // This is a standard multipart message.
                // e.g.: 'multipart/MIXED; boundary="-=_reichinger2-149ad1873"'
                MimeMultipart m = (MimeMultipart) message.getContent();
                for (int i = 0; i < m.getCount(); i++)
                {
                    BodyPart bp = m.getBodyPart(i);
                    String bpContentType = bp.getContentType();
                    LOG.info("getText(): body part " + i + " contentType = '"
                            + bpContentType + "'");
                    if (bpContentType.toUpperCase().startsWith("TEXT/PLAIN"))
                    {
                        if (result == null)
                        {
                            result = bp.getContent().toString();
                        }
                        else
                        {
                            result += bp.getContent().toString();
                        }
                    }
                }
            }
            else
            {
                result = "Unknown Type: " + contentType;
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public String getAllRecipients()
    {
        String result = null;
        try
        {
            result = (message.getAllRecipients() != null) ? message
                    .getAllRecipients()[0].toString() : "";
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public boolean hasAttachment()
    {
        // if (m instanceof MimeMessage && ((MimeMessage)
        // m).getContentType().toUpperCase().startsWith("MULTIPART/"))
        // {

        boolean result = false;
        try
        {
            if (message.getContent() instanceof Multipart)
            {
                result = true;
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public boolean isStatusDeleted()
    {
        boolean result = false;
        try
        {
            result = message.isSet(Flags.Flag.DELETED);
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public boolean hasBeenRead()
    {
        boolean result = false;
        try
        {
            result = message.isSet(Flags.Flag.SEEN);
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public void setStatusDeleted(boolean deleted)
    {
        try
        {
            message.setFlag(Flags.Flag.DELETED, deleted);
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean hasBeenAnswered()
    {
        boolean result = false;
        try
        {
            result = message.isSet(Flags.Flag.ANSWERED);
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public int getSize()
    {
        int result = -1;
        try
        {
            result = message.getSize();
        }
        catch (MessagingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
