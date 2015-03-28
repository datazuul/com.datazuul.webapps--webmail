package me.swax.webapps.swaxmail;

import me.swax.webapps.swaxmail.pages.ViewPage;
import me.swax.webapps.swaxmail.pages.SignInPage;

import org.apache.wicket.Page;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;

/**
 * @author Ralf Eichinger
 */
public class WicketApplication extends AuthenticatedWebApplication {

	/**
	 * Constructor.
	 */
	public WicketApplication() {

	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		return ViewPage.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return SignInPage.class;
	}

	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
		return AuthenticatedWebSession.class;
	}

}
