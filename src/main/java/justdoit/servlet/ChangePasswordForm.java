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
import javax.faces.context.FacesContext;
/**
 *
 * @author Goeller
 */

//ChangeForm für Passwortänderung
public class ChangePasswordForm {
    // Fehlermeldungen
    public List<String> errors = new ArrayList<>();
    private final String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    // Eingabefelder
    private String password0 = "";
    private String password1 = "";
    private String password2 = "";
 
    //ChangeForm zur PW Zurücksetzung
    public ChangePasswordForm(String password0, String password1, String password2) {
        this.password0= password0;
        this.password1 = password1;
        this.password2 = password2;
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }   
    
    public void setPassword0(String password0) {
        this.password0 = password0;
    }    
    
    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getPassword0() {
        return password0;
    }    
    
    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }
    /**
     * Eingegebene Werte prüfen
     */
    //Check für PW Änderung
    public void checkValues() {
        //TODO: Länge des Passworts prüfen
        //TODO: Email-Prüfung hinzufügen
         if (password0 == null || password0.trim().isEmpty()) {
            password0 = "";
            this.errors.add("Gib erst dein aktuelles Passwort ein");
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
