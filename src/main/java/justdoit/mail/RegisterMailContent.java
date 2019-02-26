package justdoit.mail;

import javax.ejb.Stateless;
import justdoit.user.User;

@Stateless
public class RegisterMailContent extends MailContent {

//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public RegisterMailContent(User user) {
        super(user, "Registrierung");
    }

    public RegisterMailContent() {

    }
//</editor-fold>
}
