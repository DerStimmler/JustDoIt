package justdoit.todo.ejb;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import justdoit.common.ejb.EntityBean;
import justdoit.todo.jpa.Category;
import justdoit.todo.jpa.CategoryId;
import justdoit.common.jpa.User;

@Stateless
@RolesAllowed("justdoit-user")
public class CategoryBean extends EntityBean<Category, CategoryId> {

    @PersistenceContext
    EntityManager em;

    public CategoryBean() {
        super(Category.class);
    }

    public List<Category> findByUser(User user) {
        try {
            return this.em.createQuery("SELECT c FROM Category c where c.username = :username").setParameter("username", user.getUsername()).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
