/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.servlet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lichter, Ansgar
 */
public class SignUpForm {
    // Fehlermeldungen
    public List<String> errors = new ArrayList<>();
    
    // Eingabefelder
    private String username = "";
    private String password1 = "";
    private String password2 = "";
    
    public SignUpForm(String username, String password1, String password2) {
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
    }
    
    
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

    /**
     * Eingegebene Werte prüfen
     */
    public void checkValues() {
        //TODO: Länge des Passworts prüfen
        //TODO: Email-Prüfung hinzufügen
        if (username == null || username.trim().isEmpty()) {
            username = "";
            this.errors.add("Gib erst einen Namen ein.");
        }
        if (password1 == null || password1.trim().isEmpty()) {
            password1 = "";
            this.errors.add("Gib erst ein Passwort ein");
        }
        if (password2 == null || password2.trim().isEmpty()) {
            password2 = "";
            this.errors.add("Bitte wiederhole dein Passwort!");
        }
        if(!password1.equals(password2)) {
            this.errors.add("Die Passwörter stimmen nicht überein");
        }
    }    
}
