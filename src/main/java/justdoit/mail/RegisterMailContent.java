/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.mail;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import justdoit.user.User;
import justdoit.user.UserBean;

/**
 *
 * @author tim.schneider
 */
@Stateless
public class RegisterMailContent extends MailContent {

    public RegisterMailContent(User user) {
        super(user, "Registrierung");
    }

    public RegisterMailContent() {

    }
}
