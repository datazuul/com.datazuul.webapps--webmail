package me.swax.webapps.swaxmail.pages;

import org.apache.wicket.PageParameters;

/**
 * Simple sign in page.
 * It extends SignInPage, a base class which provides standard
 * functionality for typical log-in pages
 *
 * @author Ralf Eichinger
 */
public class SignInPage extends
		org.apache.wicket.authentication.pages.SignInPage {

	/**
	 * Constructor.
	 */
	public SignInPage() {
	}

	/**
	 * Constructor.
	 * 
	 * @param parameters
	 *            Parameters to page
	 */
	public SignInPage(PageParameters parameters) {
		super(parameters);
	}
}
