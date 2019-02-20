/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.justdoit.account;


import dhbwka.wwi.vertsys.javaee.justdoit.hash.HashGenerator;
import dhbwka.wwi.vertsys.javaee.justdoit.token.TokenBean;
import dhbwka.wwi.vertsys.javaee.justdoit.token.TokenType;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.auth.login.LoginException;

@Stateless
public class AccountBean {
    @PersistenceContext
    EntityManager em;
    
    @Inject
    HashGenerator hashGenerator;
    
    @Inject
    TokenBean tokenBean;
    
    public void registerAccount(final String username, final String email, final String password) {
        String securePassword = this.hashGenerator.getHashText(password);
        Account account = new Account(username, securePassword, email);
        
        //TODO: Account nicht per Default auf aktiv setzen, sondern erst nach Bestätigungsemail
        account.setActive(true);
        
        this.em.persist(account);
    }
    
    public Account getByUsername(final String username) {
        return this.em.createNamedQuery(Account.FIND_BY_USERNAME, Account.class).setParameter("username", username).getSingleResult();
    }
    
    public Account getByEmail(final String email) {
        return this.em.createNamedQuery(Account.FIND_BY_EMAIL, Account.class).setParameter("email", email).getSingleResult();
    }
    
    public Account getByLoginToken(String loginToken, TokenType tokenType) {
        return this.em.createNamedQuery(Account.FIND_BY_TOKEN, Account.class)
                      .setParameter("tokenHash", this.hashGenerator.getHashText(loginToken))
                      .setParameter("tokenType", tokenType)
                      .getSingleResult();
    }
    
    public Account getByUsernameAndPassword(String username, String password) throws LoginException {
        Account managedAccount = this.getByUsername(username);
        
        if(managedAccount == null) {
            throw new LoginException();
        }
        
        String hashPassword = this.hashGenerator.getHashText(password);
        
        if(!hashPassword.equals(managedAccount.getPassword())); {
            throw new LoginException();
        }
    }
}
