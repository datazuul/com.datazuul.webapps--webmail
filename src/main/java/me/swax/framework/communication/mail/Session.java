package me.swax.framework.communication.mail;

import java.util.Properties;

import javax.mail.NoSuchProviderException;
import javax.mail.URLName;

import me.swax.framework.communication.mail.server.MailInServer;
import me.swax.framework.communication.mail.server.MailOutServer;

/**
 * @author Ralf Eichinger
 */
public class Session {
	private javax.mail.Session session;

	public Session(MailOutServer mailOutServer) {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", mailOutServer.getHostname());
		this.session = javax.mail.Session.getDefaultInstance(props, null);
		this.session.setDebug(false);
	}

	public Store getStore(String protocolName, String hostnameIn, String username, String password) {
		URLName url = new URLName(protocolName, hostnameIn, -1, MailInServer.FOLDER_INBOX,
				username, password);
		Store result = null;
		try {
			result = new Store(session.getStore(url));
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
