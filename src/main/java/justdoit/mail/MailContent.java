/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.mail;

import javax.ejb.EJB;
import javax.inject.Inject;
import justdoit.user.User;
import justdoit.user.UserBean;

/**
 *
 * @author tim.schneider
 */
abstract class MailContent {

    TemplateParser templateParser;

    protected String username;
    protected String recipientAdress;
    protected String subject;
    protected String content;

    protected MailContent(User user, String subject) {
        this.username = user.getUsername();
        this.recipientAdress = user.getEmail();
        this.subject = subject;

        templateParser = new TemplateParser();
        this.content = templateParser.parseTemplate(this);
    }

    protected MailContent() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecipientAdress() {
        return recipientAdress;
    }

    public void setRecipientAdress(String recipientAdress) {
        this.recipientAdress = recipientAdress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
