package me.swax.webapps.swaxmail.pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.swax.framework.communication.mail.Folder;
import me.swax.framework.communication.mail.Message;
import me.swax.framework.communication.mail.account.MailAccount;
import me.swax.framework.communication.mail.client.MailClient;
import me.swax.framework.communication.mail.server.MailInServer;
import me.swax.webapps.swaxmail.AuthenticatedWebSession;
import me.swax.webapps.swaxmail.panels.MailViewPanel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AuthorizeInstantiation("USER")
public class ViewPage extends BasePage
{
    private static Logger LOG = LoggerFactory.getLogger(ViewPage.class);

    private final static SimpleDateFormat df = new SimpleDateFormat(
            "dd.MM.yyyy HH:mm");

    MailClient mailClient = null;

    MailAccount currentMailAccount = null;

    ArrayList<Message> items = null;

    MailViewPanel mailViewPanel = new MailViewPanel("mailViewPanel");

    public ViewPage()
    {
        super("Posteingang");
        LOG.info(">> ViewPage");

        mailClient = ((AuthenticatedWebSession) getSession()).getMailClient();
        currentMailAccount = mailClient.getCurrentMailAccount();

        Folder currentFolder = currentMailAccount.getCurrentFolder();
        if (currentFolder == null)
        {
            currentFolder = currentMailAccount
                    .setCurrentFolder(MailInServer.FOLDER_INBOX);
        }
        LOG.debug("Folder: " + currentFolder.getFullName());

        // List of mails
        items = currentFolder.getMessages();
        PageableListView<Message> listView = new PageableListView<Message>(
                "items", new PropertyModel<List<Message>>(this, "items"), 25)
        {
            public void populateItem(final ListItem<Message> listItem)
            {
                final Message message = listItem.getModelObject();

                // Subject
                // - link
                Link subjectLink = new Link("subjectLink")
                {
                    @Override
                    public void onClick()
                    {
                        Message selectedMessage = (Message) getParent()
                                .getDefaultModelObject();
                        // MailClient mailClient = ((AuthenticatedWebSession)
                        // getSession())
                        // .getMailClient();
                        // String text =
                        // mailClient.getMessageText(selectedMessage.getId());
                        mailViewPanel.setDefaultModel(new CompoundPropertyModel(selectedMessage));
                    }

                };
                // - text
                String subject = (message.getSubject() != null) ? message
                        .getSubject() : "No Subject";
                subjectLink.add(new Label("subjectText", subject));
                listItem.add(subjectLink);

                // Sender
                listItem.add(new Label("from", message.getFrom()));

                // Date
                String formattedDate = "";
                Date date = message.getDate();
                if (date != null)
                {
                    formattedDate = df.format(date);
                }
                listItem.add(new Label("date", formattedDate));

                // Sender
                listItem
                        .add(new Label("size", "" + (message.getSize() / 1024)));

                // action links
                listItem.add(new ActionPanel("actions", listItem.getModel()));

                // Flags
                // unread?
                Image flagRead = new Image("flagRead");
                Image flagUnread = new Image("flagUnread");
                Image flagAnswered = new Image("flagAnswered");
                if (!message.hasBeenRead())
                {
                    flagRead.setVisible(false);
                    listItem.add(new AttributeModifier("class", true,
                            new Model("u")));
                }
                else
                {
                    flagUnread.setVisible(false);
                }
                if (message.hasBeenAnswered())
                {
                    flagRead.setVisible(false);
                    flagUnread.setVisible(false);
                }
                else
                {
                    flagAnswered.setVisible(false);
                }
                listItem.add(flagAnswered);
                listItem.add(flagRead);
                listItem.add(flagUnread);

                // attachment?
                Image flagAttachment = new Image("flagAttachment");
                if (!message.hasAttachment())
                {
                    flagAttachment.setVisible(false);
                }
                listItem.add(flagAttachment);

                // deleted?
                if (message.isStatusDeleted())
                {
                    listItem.add(new AttributeModifier("class", true,
                            new Model("d")));
                }
            }
        };
        add(new PagingNavigator("navigator", listView));
        add(listView);

        // List of folders
        ArrayList<Folder> list = currentMailAccount.getFolders();
        ListView folderList = new ListView("folders", list)
        {
            public void populateItem(final ListItem item)
            {
                final Folder folder = (Folder) item.getModelObject();

                // - link
                AjaxFallbackLink folderLink = new AjaxFallbackLink("folderLink")
                {
                    @Override
                    public void onClick(AjaxRequestTarget target)
                    {
                        Folder folder = (Folder) getParent()
                                .getDefaultModelObject();
                        currentMailAccount.setCurrentFolder(folder
                                .getFullName());
                        // if (target != null)
                        // {
                        // target.addComponent(ViewPage.this);
                        // }
                        setResponsePage(ViewPage.class);
                    }

                };
                // - text
                folderLink.add(new Label("name", folder.getName()));
                item.add(folderLink);

                ArrayList<Folder> subFolders = folder.getSubFolders();
                ListView subFolderList = new ListView("subFolders", subFolders)
                {
                    public void populateItem(final ListItem item)
                    {
                        final Folder subFolder = (Folder) item.getModelObject();

                        // - link
                        AjaxFallbackLink folderLink = new AjaxFallbackLink(
                                "folderLink",
                                new CompoundPropertyModel<Folder>(subFolder))
                        {
                            @Override
                            public void onClick(AjaxRequestTarget target)
                            {
                                Folder subFolder = (Folder) getParent()
                                        .getDefaultModelObject();
                                currentMailAccount.setCurrentFolder(subFolder
                                        .getFullName());
                                // if (target != null)
                                // {
                                // target.addComponent(ViewPage.this);
                                // }
                                setResponsePage(ViewPage.class);
                            }

                        };
                        // - text
                        folderLink.add(new Label("name", subFolder.getName()));
                        item.add(folderLink);
                    }
                };
                item.add(subFolderList);
                if (subFolders.isEmpty())
                {
                    subFolderList.setVisible(false);
                }
            }
        };
        add(folderList);

        // View of selected mail
        add(mailViewPanel);
    }

    class ActionPanel extends Panel
    {
        private static final long serialVersionUID = 1L;

        /**
         * @param id
         *            component id
         * @param model
         *            model for contact
         */
        public ActionPanel(String id, IModel model)
        {
            super(id, model);

            Message message = (Message) model.getObject();

            Link deleteLink = new Link("delete")
            {
                public void onClick()
                {
                    Message selectedMessage = (Message) ActionPanel.this
                            .getDefaultModelObject();
                    selectedMessage.setStatusDeleted(true);
                    setResponsePage(ViewPage.class);
                }
            };
            if (message.isStatusDeleted())
            {
                deleteLink.setVisible(false);
            }
            add(deleteLink);

            Link undeleteLink = new Link("undelete")
            {
                public void onClick()
                {
                    Message selectedMessage = (Message) ActionPanel.this
                            .getDefaultModelObject();
                    selectedMessage.setStatusDeleted(false);
                    setResponsePage(ViewPage.class);
                }
            };
            if (!message.isStatusDeleted())
            {
                undeleteLink.setVisible(false);
            }
            add(undeleteLink);
        }
    }

}
