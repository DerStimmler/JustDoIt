package justdoit.mail.ejb;

import justdoit.mail.ejb.MailContent;
import javax.ejb.Stateless;
import justdoit.common.jpa.User;

@Stateless
public class ResetPasswordMailContent extends MailContent {

    private String newPassword;

//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public ResetPasswordMailContent(User user, String newPassword) {
        super(user, "Passwortwiederherstellung | " + user.getUsername() + " | JustDoIt");
        this.newPassword = newPassword;
        this.setContent();
    }

    public ResetPasswordMailContent() {

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Getter&Setter">
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
//</editor-fold>

}
