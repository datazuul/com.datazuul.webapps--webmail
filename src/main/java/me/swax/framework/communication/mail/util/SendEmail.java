package me.swax.framework.communication.mail.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	private String message_recip = "";
	private String message_subject = "";
	private String message_bcc = "";
	private String message_cc = "";
	private String message_body = "";
	private String message_from = "";
	private String mailserver_host = "";

	/** Java Mail Session Object */
	private Session session;

	private Message mesg;

	public synchronized void doSend() {
		Properties props = new Properties();

		props.put("mail.smtp.host", this.getMailserver_host());

		session = Session.getDefaultInstance(props, null);
		session.setDebug(true);

		try {
			mesg = new MimeMessage(session);

			// From
			mesg.setFrom(new InternetAddress(this.getMessage_from()));

			// To
			InternetAddress toAddress = new InternetAddress(this
					.getMessage_recip());
			mesg.addRecipient(Message.RecipientType.TO, toAddress);

			// CC
			if (!"".equals(this.getMessage_cc())) {
				InternetAddress ccAddress = new InternetAddress(this
						.getMessage_cc());
				mesg.addRecipient(Message.RecipientType.CC, ccAddress);
			}

			// BCC
			if (!"".equals(this.getMessage_bcc())) {
				InternetAddress bccAddress = new InternetAddress(this
						.getMessage_bcc());
				mesg.addRecipient(Message.RecipientType.BCC, bccAddress);
			}

			// Betreff
			mesg.setSubject(this.getMessage_subject());

			// Content der Nachricht
			mesg.setText(this.getMessage_body());
			// charset ??

			Transport.send(mesg);

		} catch (MessagingException ex) {
			ex.printStackTrace();
		}

	}

	public String getMessage_body() {
		return message_body;
	}

	public void setMessage_body(String message_body) {
		this.message_body = message_body;
	}

	public String getMessage_bcc() {
		return message_bcc;
	}

	public void setMessage_bcc(String message_bcc) {
		this.message_bcc = message_bcc;
	}

	public String getMessage_cc() {
		return message_cc;
	}

	public void setMessage_cc(String message_cc) {
		this.message_cc = message_cc;
	}

	public String getMessage_from() {
		return message_from;
	}

	public void setMessage_from(String message_from) {
		this.message_from = message_from;
	}

	public String getMessage_recip() {
		return message_recip;
	}

	public void setMessage_recip(String message_recip) {
		this.message_recip = message_recip;
	}

	public String getMessage_subject() {
		return message_subject;
	}

	public void setMessage_subject(String message_subject) {
		this.message_subject = message_subject;
	}

	public String getMailserver_host() {
		return mailserver_host;
	}

	public void setMailserver_host(String mailserver_host) {
		this.mailserver_host = mailserver_host;
	}

}
