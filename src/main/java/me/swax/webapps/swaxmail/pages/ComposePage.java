package me.swax.webapps.swaxmail.pages;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AuthorizeInstantiation("USER")
public class ComposePage extends BasePage {
	private static Logger LOG = LoggerFactory.getLogger(ComposePage.class);

	private String cc;

	private String subject;

	private String text;

	private String to;

	public ComposePage() {
		super("Email schreiben");
		LOG.info(">> ComposePage");

		Form form = new Form("form") {
			public final void onSubmit() {
				System.out.println("click");
			}
		};

		form.add(new RequiredTextField("txtTo", new PropertyModel(this, "to")));
		form.add(new TextField("txtCc", new PropertyModel(this, "cc")));
		form.add(new RequiredTextField("txtSubject", new PropertyModel(this,
				"subject")));
		form.add(new TextArea("txtText", new PropertyModel(this, "text")));

		add(form);
	}

	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            the cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
}
