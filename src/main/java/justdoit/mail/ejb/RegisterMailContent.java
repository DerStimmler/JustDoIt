package justdoit.mail.ejb;

import javax.ejb.Stateless;
import justdoit.common.jpa.User;

@Stateless
public class RegisterMailContent extends MailContent {

    private String activationUrl;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public RegisterMailContent(User user, String activationUrl) {
        super(user, "Willkommen bei JustDoIt! | " + user.getUsername() + " | JustDoIt");
        this.activationUrl = activationUrl;
        this.setContent();
    }

    public RegisterMailContent() {

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public String getActivationUrl() {
        return activationUrl;
    }

    public void setActivationUrl(String activationUrl) {
        this.activationUrl = activationUrl;
    }
    //</editor-fold>
}
