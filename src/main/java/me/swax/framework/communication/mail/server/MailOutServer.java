package me.swax.framework.communication.mail.server;

/**
 * @author Ralf Eichinger
 */
public class MailOutServer {
	public enum Protocol {
		SMTP("smtp");

		private String name;

		private Protocol(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public static Protocol get(String name) {
			Protocol result = null;
			for (Protocol p : values()) {
				if (p.getName().equals(name)) {
					result = p;
				}
			}
			return result;
		}
	};

	private Protocol protocol;
	private String hostname;

	public MailOutServer(Protocol protocol, String hostname) {
		super();
		this.hostname = hostname;
		this.protocol = protocol;
	}

	public String getHostname() {
		return this.hostname;
	}

	public Protocol getProtocol() {
		return protocol;
	}
}
