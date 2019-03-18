package justdoit.common.ejb;

import justdoit.common.jpa.User;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import justdoit.common.exception.UserAlreadyExistsException;
import justdoit.hash.HashGenerator;

@Stateless
public class UserBean extends EntityBean<User, String> {

    @Resource
    EJBContext ctx;

    @Inject
    HashGenerator hashGenerator;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public UserBean() {
        super(User.class);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Weitere Methoden">
    public User getCurrentUser() {
        return this.em.find(User.class, this.ctx.getCallerPrincipal().getName());
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

    public User findByUniqueNumber(long uniqueNumber) {
        return (User) em.createQuery("Select u FROM User u WHERE u.uniqueNumber = :uniqueNumber").setParameter("uniqueNumber", uniqueNumber).getSingleResult();
    }
    //</editor-fold>
}
