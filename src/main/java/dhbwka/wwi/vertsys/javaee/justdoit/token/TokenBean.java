/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.justdoit.token;

import dhbwka.wwi.vertsys.javaee.justdoit.hash.HashGenerator;
import dhbwka.wwi.vertsys.javaee.justdoit.account.Account;
import dhbwka.wwi.vertsys.javaee.justdoit.account.AccountBean;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.auth.login.LoginException;

@Stateless
public class TokenBean {
    @PersistenceContext
    EntityManager em;
    
    @Inject
    HashGenerator hashGenerator;
    
    @Inject
    AccountBean accountBean;
    
    public String generate(final String username, final String ipAddress, final String description, final TokenType tokenType) {
        String rawToken = UUID.randomUUID().toString();
        Instant expiration = Instant.now().plus(14, ChronoUnit.DAYS);
        
        try {
            this.save(rawToken, username, ipAddress, description, tokenType, expiration);
        } catch (LoginException ex) {
            //TODO: Exception-Handling
            Logger.getLogger(TokenBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rawToken;
    }

    public void save(String rawToken, String username, String ipAddress, String description, TokenType tokenType, Instant expiration) throws LoginException {
        Account account = this.accountBean.getByUsername(username);
        if(account == null) {
            //TODO: andere Exception verwenden
            throw new LoginException();            
        }
        
        Token token = new Token();
        token.setTokenHash(this.hashGenerator.getHashText(rawToken));
        token.setExpiration(expiration);
        token.setDescription(description);
        token.setTokenType(tokenType);
        token.setIpAdress(ipAddress);
        
        account.addToken(token);
        
        this.em.merge(token);
    }
    
    public void remove(String token) {
        this.em.createNamedQuery(Token.REMOVE_TOKEN, Token.class)
               .setParameter("tokenHash", token).executeUpdate();
    }
    
    public void removeExpired() {
        this.em.createNamedQuery(Token.REMOVE_EXPIRED_TOKEN, Token.class).executeUpdate();
    }
}
