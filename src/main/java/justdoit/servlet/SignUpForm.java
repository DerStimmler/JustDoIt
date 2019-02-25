/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lichter, Ansgar
 */
public class SignUpForm {
    // Fehlermeldungen
    public List<String> errors = new ArrayList<>();
    private final String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    // Eingabefelder
    private String username = "";
    private String password1 = "";
    private String password2 = "";
    private String email = "";
    
//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public SignUpForm(String username, String password1, String password2, String email) {
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
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
    
    public String getUsername() {
        return this.username;
    }
    
    public void setPassword1(String password1) {
        this.password1 = password1;
    }
    
    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    public String getPassword1() {
        return password1;
    }
    
    public String getPassword2() {
        return password2;
    }
    
    public void setUsername(String name) {
        this.username = name;
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
    public void checkValues() {
        if (username == null || username.trim().isEmpty()) {
            username = "";
            this.errors.add("Gib erst einen Namen ein.");
        }
        if (password1 == null || password1.trim().isEmpty()) {
            password1 = "";
            this.errors.add("Gib erst ein Passwort ein");
        }
        if(password1.length() < 5 || password1.length() > 64) {
            this.errors.add("Dein Passwort muss zwischen 5 und 64 Zeichen lang sein");
        }
        if (password2 == null || password2.trim().isEmpty()) {
            password2 = "";
            this.errors.add("Bitte wiederhole dein Passwort!");
        }
        if(!password1.equals(password2)) {
            this.errors.add("Die Passwörter stimmen nicht überein");
        }
        if(email == null || email.trim().isEmpty()) {
            email = "";
            this.errors.add("Gib erst eine E-Mail-Adresse ein.");
        }
        if(!email.matches(regex)) {
            this.errors.add("Bitte gib eine gültige E-Mail-Adresse ein");
        }
    }    
}
