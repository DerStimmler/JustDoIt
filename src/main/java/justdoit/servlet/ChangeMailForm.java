package justdoit.servlet;

import java.util.ArrayList;
import java.util.List;

//ChangeForm für Mailänderung
public class ChangeMailForm {

    // Fehlermeldungen
    public List<String> errors = new ArrayList<>();
    private final String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    // Eingabefelder
    private String password0 = "";
    private String email = "";

    //ChangeForm zur Mail Zurücksetzung
//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public ChangeMailForm(String password0, String email) {
        this.password0 = password0;
        this.email = email;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Getter&Setter">
    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setPassword0(String password0) {
        this.password0 = password0;
    }

    public String getPassword0() {
        return password0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
//</editor-fold>

    /**
     * Eingegebene Werte prüfen
     */
    //Check für Mailänderung
    public void checkMailValues() {
        if (password0 == null || password0.trim().isEmpty()) {
            password0 = "";
            this.errors.add("Gib erst dein aktuelles Passwort ein");
        }
        if (email == null || email.trim().isEmpty()) {
            email = "";
            this.errors.add("Gib erst eine E-Mail-Adresse ein.");
        }
        if (!email.matches(regex)) {
            this.errors.add("Bitte gib eine gültige E-Mail-Adresse ein");
        }
    }
}
