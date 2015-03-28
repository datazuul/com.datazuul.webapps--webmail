package me.swax.webapps.swaxmail;

import me.swax.framework.communication.mail.account.MailAccount;
import me.swax.framework.communication.mail.client.MailClient;
import me.swax.framework.communication.mail.server.MailInServer;
import me.swax.framework.communication.mail.server.MailOutServer;
import me.swax.framework.communication.mail.server.MailInServer.Protocol;

import org.apache.wicket.Request;
import org.apache.wicket.authorization.strategies.role.Roles;

/**
 * @author Ralf Eichinger
 */
public class AuthenticatedWebSession extends
        org.apache.wicket.authentication.AuthenticatedWebSession
{

    private static final long serialVersionUID = 1L;

    /** mail-session of the logged in user */
    private MailClient mailClient = null;

    /**
     * @param request
     */
    public AuthenticatedWebSession(Request request)
    {
        super(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.wicket.authentication.AuthenticatedWebSession#authenticate(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public boolean authenticate(final String username, final String password)
    {
        String protocolName = "imap"; // TODO make configurable
        String hostname = "gmail.com"; // TODO make configurable

        // mail address
        String mailAddress = null;

        // mail in server
        MailInServer mailInServer = null;

        // mail out server
        MailOutServer mailOutServer = null;

        // mail account
        MailAccount mailAccount = null;

        // mail client
        MailClient mailClient = null;

        Protocol protocol = MailInServer.Protocol.get(protocolName);
        if (protocol != null)
        {
            mailInServer = new MailInServer(protocol, hostname);
            mailAccount = new MailAccount(mailAddress, username, password,
                    mailInServer, mailOutServer);
            if (!mailAccount.connect())
            {
                return false;
            }
            mailClient = new MailClient();
            // TODO here would be the hook for retrieving multiple mail accounts
            // from somewhere...
            mailClient.add(mailAccount);

            // login successful, save session objects:
            setMailClient(mailClient);

            return true;

        }
        return false;// TODO invalid protocol!
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.wicket.authentication.AuthenticatedWebSession#getRoles()
     */
    @Override
    public Roles getRoles()
    {
        if (isSignedIn())
        {
            // If the user is signed in, they have these roles
            return new Roles(Roles.USER);
        }
        return null;
    }

    public MailClient getMailClient()
    {
        return mailClient;
    }

    public void setMailClient(MailClient mailClient)
    {
        this.mailClient = mailClient;
    }
}
