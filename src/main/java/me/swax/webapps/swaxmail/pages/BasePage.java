package me.swax.webapps.swaxmail.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author Ralf Eichinger
 */
public class BasePage extends WebPage {

	public BasePage(String pageTitle) {
		super();
		
		// Seitentitel
        add(new Label("title", pageTitle));
	}

}
