package me.swax.framework.communication.mail;

/**
 * @author Ralf Eichinger
 */
public class MailPart {
	private String attachmentType;
	private String description;
	private String filename;
	private String id;
	private int messageNum;
	private int partNum;
	private String text;

	public MailPart(int messageNum, int partNum) {
		this.attachmentType = null;
		this.description = null;
		this.filename = null;
		this.id = messageNum + "/" + partNum;
		this.messageNum = messageNum;
		this.partNum = partNum;
		this.text = null;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getMessageNum() {
		return messageNum;
	}

	public int getPartNum() {
		return partNum;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}
}
