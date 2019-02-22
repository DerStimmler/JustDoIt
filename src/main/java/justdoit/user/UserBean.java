/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.user;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import justdoit.exceptions.InvalidCredentialsException;
import justdoit.exceptions.UserAlreadyExistsException;
import justdoit.hash.HashGenerator;

/**
 *
 * @author Lichter, Ansgar
 */

@Stateless
public class UserBean {
   @PersistenceContext
   EntityManager em;
   
   @Resource
    EJBContext ctx;
   
   @Inject
   HashGenerator hashGenerator;
   
   
   public User getCurrentUser() {
        return this.em.find(User.class, this.ctx.getCallerPrincipal().getName());
    }
   
   public void signup(String username, String password) throws UserAlreadyExistsException {
        if (em.find(User.class, username) != null) {
            throw new UserAlreadyExistsException("Der Benutzername $Name ist bereits vergeben.".replace("$Name", username));
        }
        //Hash the password
        password = this.hashGenerator.getHashText(password);
        User user = new User(username, password);
        user.addToGroup("justdoit-user");
        em.persist(user);
    }
   
   @RolesAllowed("justdoit-user")
    public void changePassword(User user, String oldPassword, String newPassword) throws InvalidCredentialsException {
        if (user == null || !user.checkPassword(oldPassword)) {
            throw new InvalidCredentialsException("Benutzername oder Passwort sind falsch.");
        }
        //Hash the password
        newPassword = this.hashGenerator.getHashText(newPassword);
        user.setPassword(newPassword);
    }
    
    @RolesAllowed("justdoit-user")
    public User update(User user) {
        return em.merge(user);
    }
    
    @RolesAllowed("justdoit-user")
    public void delete(User user) {
        this.em.remove(user);
    }
}
