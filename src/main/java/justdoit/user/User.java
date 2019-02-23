/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.user;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lichter, Ansgar
 */
@Entity
@Table(name = "JUSTDOIT_USER")
public class User implements Serializable {
    //TODO: EMail hinzufügen
    //TODO: Status Aktiv / nicht Aktiv hinzufügen und bei Authentication abfragen
    private static final long serialVersionUID = 1L;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    @Column(name = "ID")
    private Long id;
    
    @Id
    @Column(name = "USERNAME", length = 64)
    @Size(min = 5, max = 64, message = "Ihr gewünschter Benutzername darf nur zwischen 5 und 64 Zeichen lang sein")
    @NotNull(message = "Bitte geben Sie einen Benutzernamen ein!")
    private String username;

    @Column(name = "PASSWORD_HASH")
    @NotNull(message = "Bitte geben Sie ein Passwort ein!")
    private String password;
    
    @Column(name = "EMAIL" )
    @NotNull(message = "Bitte geben Sie eine E-Mail-Adresse ein!")
    private String email;
    
    @ElementCollection
    @CollectionTable(
            name = "USER_GROUP",
            joinColumns = @JoinColumn(name = "USERNAME")
    )
    @Column(name = "GROUPNAME")
    List<String> groups = new ArrayList<>();
    
    
    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id =  Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + (10000 + new Random().nextInt(90000))); //aktuelles Datum + Zeit + 5 stellige Random Zahl
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public List<String> getGroups() {
        List<String> groupsCopy = new ArrayList<>();

        this.groups.forEach((groupname) -> {
            groupsCopy.add(groupname);
        });

        return groupsCopy;
    }

    public void addToGroup(String groupname) {
        if (!this.groups.contains(groupname)) {
            this.groups.add(groupname);
        }
    }

    public void removeFromGroup(String groupname) {
        this.groups.remove(groupname);
    }

}
