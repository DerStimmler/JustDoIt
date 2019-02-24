/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.mail;

import java.util.Date;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author tim.schneider
 */
@Stateless
public class MailBean {

    private final String from = "no-reply.justdoit@web.de";
    private final String host = "smtp.web.de";
    private final String port = "587";
    private final String username = "no-reply.justdoit@web.de";
    private final String password = "JustDoIt_Pw123";
    
    public MailBean() {
        
    }

    public void sendMail(String to, String subject, String text) throws AddressException, MessagingException {
        MailAuthenticator auth = new MailAuthenticator(username, password);

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        // properties.setProperty("mail.smtp.socketFactory.class",
        // "javax.net.ssl.SSLSocketFactory");
        // properties.setProperty("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(properties, auth);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);
            msg.setContent(text, "text/html; charset=utf-8");
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}

class MailAuthenticator extends Authenticator {

        private final String user;
        private final String password;

        public MailAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.user, this.password);
        }

    }