package justdoit.task.bean;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import justdoit.common.EntityBean;
import justdoit.task.entitiy.Category;
import justdoit.task.entitiy.CategoryId;
import justdoit.user.User;

@Stateless
@RolesAllowed("justdoit-user")
public class CategoryBean extends EntityBean<Category, CategoryId> {

    @PersistenceContext
    EntityManager em;

    public CategoryBean() {
        super(Category.class);
    }
    
    public List<Category> findByUser(User user) {
        return this.em.createQuery("SELECT c FROM Category c where c.username = :username").setParameter("username", user.getUsername()).getResultList();
    }
}
