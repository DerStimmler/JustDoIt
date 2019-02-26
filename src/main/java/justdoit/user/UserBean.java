package justdoit.user;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import justdoit.exceptions.OldPasswordIncorrectException;
import justdoit.exceptions.UserAlreadyExistsException;
import justdoit.hash.HashGenerator;

@Stateless
public class UserBean {
    //TODO: UserBean von EntityBean erben lassen und entsprechend anpassen (update und delete können hier dann gelöscht werden)

    @PersistenceContext
    EntityManager em;

    @Resource
    EJBContext ctx;

    @Inject
    HashGenerator hashGenerator;

    public User getCurrentUser() {
        return this.em.find(User.class, this.ctx.getCallerPrincipal().getName());
    }

    public User findByUsername(String username) {
        return em.find(User.class, username);
    }

    public void signup(String username, String password, String email) throws UserAlreadyExistsException {
        if (em.find(User.class, username) != null) {
            throw new UserAlreadyExistsException("Der Benutzername $Name ist bereits vergeben.".replace("$Name", username));
        }
        //Hash the password
        password = this.hashGenerator.getHashText(password);
        User user = new User(username, password, email);
        //TODO: User zur Gruppe "justdoit-user-inactive" hinzufügen und erst wenn Account aktiviert wurde Gruppe zu "justdoit-user" ändern
        user.addToGroup("justdoit-user");
        em.persist(user);
    }

    @RolesAllowed("justdoit-user")
    public void changePassword(User username, String passwordAkt, String oldPassword, String newPassword) throws OldPasswordIncorrectException {
        oldPassword = this.hashGenerator.getHashText(oldPassword);
        if (username == null || !passwordAkt.equals(oldPassword)) {
            throw new OldPasswordIncorrectException("Altes Passwort ist nicht korrekt.");
        }
        //Hash the password
        newPassword = this.hashGenerator.getHashText(newPassword);
        username.setPassword(newPassword);
        em.merge(username);
    }

    @RolesAllowed("justdoit-user")
    public void changeMail(User username, String passwordAkt, String oldPassword, String email) {
        oldPassword = this.hashGenerator.getHashText(oldPassword);
        if (username == null || !passwordAkt.equals(oldPassword)) {
            throw new OldPasswordIncorrectException("Altes Passwort ist nicht korrekt.");
        }
        username.setEmail(email);
        em.merge(username);
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
