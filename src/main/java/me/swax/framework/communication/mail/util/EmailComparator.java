package me.swax.framework.communication.mail.util;

import java.util.Comparator;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * @author Ralf Eichinger
 */
public class EmailComparator implements Comparator<Object> {

	/**
	 * Compare the two fields of the message you want to sort by. If message1
	 * comes first, return -1, if equal, return 0, if message2 comes first,
	 * return 1.
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) {
		Message msg1 = (Message) o1;
		Message msg2 = (Message) o2;
		Date date1 = null;
		Date date2 = null;
		// date
		try {
			date1 = (msg1.getSentDate() != null) ? msg1.getSentDate() : msg1
					.getReceivedDate();
			date2 = (msg2.getSentDate() != null) ? msg2.getSentDate() : msg2
					.getReceivedDate();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("sorting date1=" + date1 + " and date2=" + date2);

		if (date1 == null || date2 == null) {
			if (date1 == null)
				return 1;
			if (date2 == null)
				return -1;
		} else {
			if (date1.after(date2)) {
				//System.out.println("date1 after date2");
				return -1;
			} else if (date1.before(date2)) {
				//System.out.println("date1 before date2");
				return 1;
			} else {
				//System.out.println("date1 equals date2");
				return 0;
			}
		}
		return 0;
	}

}
