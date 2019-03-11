package justdoit.common.ejb;

import justdoit.common.jpa.User;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import justdoit.common.exception.UserAlreadyExistsException;
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
            throw new UserAlreadyExistsException(username);
        }
        //Hash the password
        password = this.hashGenerator.getHashText(password);
        User user = new User(username, password, email);
        user.addToGroup("justdoit-user-inactive");
        em.persist(user);
    }

    public User update(User user) {
        return em.merge(user);
    }

    @RolesAllowed("justdoit-user")
    public void delete(User user) {
        this.em.remove(user);
    }

    public List<User> findAll() {
        return this.em.createQuery("SELECT u FROM User u").getResultList();
    }

    //TODO: Sollte diese Methode nicht in der TODO Bean sein?
    public List<User> getUsers(long id) {
        return em.createQuery("SELECT u FROM User u JOIN u.todos t WHERE t.id= :id").setParameter("id", id).getResultList();
    }

    public User findById(long id) {
        return (User) em.createQuery("Select u FROM User u WHERE u.id = :id").setParameter("id", id).getSingleResult();
    }
}
