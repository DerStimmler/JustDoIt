package justdoit.task.bean;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import justdoit.common.EntityBean;
import justdoit.task.entitiy.Category;

@Stateless
@RolesAllowed("justdoit-user")
public class CategoryBean extends EntityBean<Category, Long> {

    @PersistenceContext
    EntityManager em;

    public CategoryBean() {
        super(Category.class);
    }
}
