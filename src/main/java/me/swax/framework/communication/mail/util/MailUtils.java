package me.swax.framework.communication.mail.util;

import java.util.StringTokenizer;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

public class MailUtils
{
    /**
     * @param a
     * @return
     */
    public static String joinAddress(Address[] a)
    {
        String s = "";
        if (a != null)
        {
            boolean tf = true;
            for (int i = 0; i < a.length; i++)
            {
                s += ((tf) ? " " : ", ") + getDisplayAddress(a[i]);
                tf = false;
            }
        }
        else
        {
            s = "";
        }

        return s.trim();
    }

    // returns a string with each line with a leading ">"
    /**
     * @param content
     * @return
     */
    public static String quoteContent(String content)
    {
        StringBuffer text = new StringBuffer();
        StringTokenizer tok = new StringTokenizer(content, "\n");
        while (tok.hasMoreTokens())
        {
            text.append("> ").append(tok.nextToken()).append("\n");
        }
        return text.toString();
    }

    // utility method; returns a string suitable for msg header display
    /**
     * @param a
     * @return
     */
    private static String getDisplayAddress(Address a)
    {
        String pers = null;
        String addr = null;
        if (a instanceof InternetAddress
                && ((pers = ((InternetAddress) a).getPersonal()) != null))
        {
            addr = pers + "  " + "<" + ((InternetAddress) a).getAddress() + ">";
        }
        else
        {
            addr = a.toString();
        }
        return addr;
    }

    /**
     * Converts text to html.
     */
    public static String txt2html(String text)
    {
        if (text != null)
        {
            String[] targets =
            { "\n\n", "\n" };
            String[] replaces =
            { "<P>", "<BR>" };
            for (int i = 0; i < targets.length; i++)
            {
                while (text.indexOf(targets[i]) >= 0)
                {
                    int pos = text.indexOf(targets[i]);
                    if (pos == 0)
                    {
                        text = replaces[i] + text.substring(pos + 1);
                    }
                    else
                    {
                        text = text.substring(0, pos) + replaces[i]
                                + text.substring(pos + 1);
                    }
                }
            }
        }
        return text;
    }
}
