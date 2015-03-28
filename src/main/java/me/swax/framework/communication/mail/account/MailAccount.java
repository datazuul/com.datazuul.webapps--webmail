package me.swax.framework.communication.mail.account;

import java.util.ArrayList;

import me.swax.framework.communication.mail.Folder;
import me.swax.framework.communication.mail.Session;
import me.swax.framework.communication.mail.Store;
import me.swax.framework.communication.mail.server.MailInServer;
import me.swax.framework.communication.mail.server.MailOutServer;

/**
 * @author Ralf Eichinger
 */
public class MailAccount {
	private String mailAddress;
	private MailOutServer mailOutServer;
	private MailInServer mailInServer;
	private Folder currentFolder;
	private Session mailSession;
	private Store mailStore;
	private String username;
	private String password;
	private int currentMsgNum;

	public MailAccount(String mailAddress, String username, String password,
			MailInServer mailInServer, MailOutServer mailOutServer) {
		// mail address
		if (mailAddress == null && mailInServer != null) {
			if (username.indexOf("@") == -1) {
				mailAddress = username + "@" + mailInServer.getHostname();
			} else {
				mailAddress = username;
			}
		}
		this.mailAddress = mailAddress;

		// mail username
		this.username = username;

		// mail password
		this.password = password;

		// mail in server
		this.mailInServer = mailInServer;

		// mail out server
		if (mailOutServer != null) {
			this.mailOutServer = mailOutServer;
		} else {
			this.mailOutServer = new MailOutServer(MailOutServer.Protocol.SMTP, mailInServer
					.getHostname());
		}

		// mail session
		this.mailSession = new Session(this.mailOutServer);
	}

	public boolean connect() {
		boolean result = false;
		// mail store
		this.mailStore = mailSession.getStore(mailInServer.getProtocol().getName(), mailInServer
				.getHostname(), username, password);
		result = this.mailStore.connect();
		return result;
	}

	public Folder getCurrentFolder() {
		return currentFolder;
	}

	public Folder setCurrentFolder(String foldername) {
		Folder result = null;
		Folder folder = getCurrentFolder();
		if (folder != null && folder.isOpen()) {
			folder.close(false);
		}
		folder = mailStore.getFolder(foldername);
		folder.open(Folder.READ_WRITE);
		result = folder;
		currentFolder = folder;
		return result;
	}

	public ArrayList<Folder> getFolders() {
		return this.mailStore.getFolders();
	}

	// to be moved??? ====================================

	// public Message createNewMessage() {
	// return new MimeMessage(this.mailSession);
	// }
	//
	// public String getUsername() {
	// return username;
	// }
	//
	// public String getHost() {
	// return getMailInServer().getHostname();
	// }
	//
	// public int getCurrentMsgNum() {
	// return currentMsgNum;
	// }
	//
	// public void setCurrentMsgNum(int i) {
	// this.currentMsgNum = i;
	// }
	//
	// public String getMailAddress() {
	// return mailAddress;
	// }
	//
	// public MailInServer getMailInServer() {
	// return mailInServer;
	// }
	//
	// public MailOutServer getMailOutServer() {
	// return mailOutServer;
	// }
}
