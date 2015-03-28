package me.swax.webapps.swaxmail.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author ralf
 */
public class MailViewPanel extends Panel
{
    public Label allRecipients = new Label("allRecipients");
    public Label date = new Label("date");
    public Label from = new Label("from");
    public Label subject = new Label("subject");
    public Label text = new Label("text");
    
    public MailViewPanel(String id)
    {
        super(id);
        
        allRecipients.setEscapeModelStrings(true);
        add(allRecipients);
        
        date.setEscapeModelStrings(false);
        add(date);
        
        from.setEscapeModelStrings(true);
        add(from);
        
        subject.setEscapeModelStrings(false);
        add(subject);
        
        text.setEscapeModelStrings(false);
        add(text);
    }

}
