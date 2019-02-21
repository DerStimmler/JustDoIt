/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.justdoit.register;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tim.schneider
 */
public class RegisterForm {
    
    // Fehlermeldungen
    public List<String> errors = new ArrayList<>();
    
    // Eingabefelder
    private String name = "";
    private String password = "";
    private String email = "";
    
    public void checkValues(){
    if (name == null || name.trim().isEmpty()) {
            name = "";
            this.errors.add("Gib erst einen Namen ein.");
        }
        if (password == null || password.trim().isEmpty()) {
            password = "";
            this.errors.add("Gib erst einen Passwort ein.");
        }
        if (email == null || email.trim().isEmpty()) {
            email = "";
            this.errors.add("Gib erst eine E-Mail-Adresse ein.");
        }
    }
    
    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
