package justdoit.mail;

import javax.ejb.Stateless;
import justdoit.user.User;

@Stateless
public class ResetPasswordMailContent extends MailContent {

    private String newPassword;

//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public ResetPasswordMailContent(User user, String newPassword) {
        super(user, "ResetPassword Mail");
        this.newPassword = newPassword;
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
