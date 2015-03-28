package me.swax.framework.communication.mail.client;

import java.util.ArrayList;

import me.swax.framework.communication.mail.account.MailAccount;

/**
 * @author Ralf Eichinger
 */
public class MailClient {
	protected ArrayList<MailAccount> mailAccounts = null;

	public MailClient() {
		mailAccounts = new ArrayList<MailAccount>();
	}

	public MailAccount getCurrentMailAccount() {
		return this.mailAccounts.get(0);
	}

	public void add(MailAccount mailAccount) {
		mailAccounts.add(mailAccount);
	}

	// to be moved??? =============================================

	// public Message getMessage(int mailId) {
	// Message result = null;
	// Folder currentFolder = getCurrentFolder();
	// try {
	// result = new Message(currentFolder.getMessage(mailId));
	// } catch (MessagingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return result;
	// }
	//
	//
	// public String getMessageText(int msgId) {
	// Message message = getMessage(msgId);
	// return message.getText();
	// }
}
