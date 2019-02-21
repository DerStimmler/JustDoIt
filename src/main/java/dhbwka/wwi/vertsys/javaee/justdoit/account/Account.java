/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.justdoit.account;

import dhbwka.wwi.vertsys.javaee.justdoit.token.Token;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Licht
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Account.FIND_BY_USERNAME, query = "select a from Account a where a.username = :username"),
    @NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email"),
    @NamedQuery(name = Account.FIND_BY_TOKEN, query = "select a from Account a inner join a.tokens t where t.tokenHash = :tokenHash and t.tokenType = :tokenType and t.expiration > CURRENT_TIMESTAMP")
})
public class Account implements Serializable {

    public static final String FIND_BY_USERNAME = "Account.findByUsername";
    public static final String FIND_BY_EMAIL = "Account.findByEmail";
    public static final String FIND_BY_TOKEN = "Account.findByToken";

    @Id
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Size(min = 1, max = 100)
    private String email;

    @Column(name = "active")
    @NotNull
    private boolean active;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens = new ArrayList<>();

    public Account() {
    }

    public Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id =  Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + new Random().nextInt(99999)); //aktuelles Datum + Zeit + 5 stellige Random Zahl
    }

    @PrePersist
    public void generateInformation() {
        this.active = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void addToken(Token token) {
        this.tokens.add(token);
        token.setAccount(this);
    }

    public void removeToken(Token token) {
        this.tokens.remove(token);
        token.setAccount(this);
    }

    @Override
    public String toString() {
        return "Account: " + this.username;
    }
    
}
