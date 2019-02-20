/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.justdoit.token;

import dhbwka.wwi.vertsys.javaee.justdoit.account.Account;
import java.io.Serializable;
import java.time.Instant;
import static java.time.temporal.ChronoUnit.MONTHS;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;


@Entity
public class Token implements Serializable {
    
    public static final String REMOVE_TOKEN = "Token.removeToken";
    public static final String REMOVE_EXPIRED_TOKEN = "Token.removeExpiredToken";
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "token_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "token_id_seq", sequenceName = "token_id_seq", allocationSize = 1)
    private Long id;
    
    private String tokenHash;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    
    @Size(min = 1, max = 45)
    private String ipAdress;
    private String description;
    private Instant created;
    private Instant expiration;
    
    @ManyToOne
    private Account account = null;
    
    public Token() {
        
    }
    
    @PrePersist
    public void generateInformation() {
        this.created = Instant.now();
        if(this.expiration == null) {
            this.expiration = this.created.plus(1, MONTHS);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTokenHash() {
        return this.tokenHash;
    }
    
    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }
    
    public TokenType getTokenType() {
        return this.tokenType;
    }
    
    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
    
    public String getIpAdress() {
        return this.ipAdress;
    }
    
    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }
    
    public String getDescription(String description) {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Instant getCreated() {
        return created;
    }
    
    public void setCreated(Instant created) {
        this.created = created;
    }
    
    public Instant getExpiration() {
        return this.expiration;
    }
    
    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }
    
    public Account getAccount() {
        return this.account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }    

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Token)) {
            return false;
        }
        Token other = (Token) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Token{ id = " + id + " }";
    }
    
}
