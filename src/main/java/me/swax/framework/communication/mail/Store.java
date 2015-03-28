package me.swax.framework.communication.mail;

import java.util.ArrayList;

import javax.mail.MessagingException;

/**
 * @author Ralf Eichinger
 */
public class Store {
	private javax.mail.Store store = null;

	public Store(javax.mail.Store store) {
		this.store = store;
	}

	public boolean connect() {
		boolean result = false;
		try {
			store.connect();
			result = true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public Folder getFolder(String foldername) {
		Folder result = null;
		try {
			result = new Folder(store.getFolder(foldername));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<Folder> getFolders() {
		ArrayList<Folder> result = null;
		try {
			javax.mail.Folder[] folders = store.getDefaultFolder().list();
			result = new ArrayList<Folder>();
			for (javax.mail.Folder folder : folders) {
                Folder f = new Folder(folder);
                if ((folder.getType() & javax.mail.Folder.HOLDS_FOLDERS) != 0) {
                    javax.mail.Folder[] subFolders = folder.list();
                    for (javax.mail.Folder subFolder : subFolders) {
                        Folder subF = new Folder(subFolder);
                        f.add(subF);
                    }
                }
				result.add(f);
			}
            
            folders = store.getPersonalNamespaces();
            for (javax.mail.Folder folder : folders) {
                result.add(new Folder(folder));
            }
            
            folders = store.getSharedNamespaces();
            for (javax.mail.Folder folder : folders) {
                result.add(new Folder(folder));
            }
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
