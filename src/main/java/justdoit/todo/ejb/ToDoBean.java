package justdoit.todo.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import justdoit.common.ejb.EntityBean;
import justdoit.todo.jpa.ToDo;

@Stateless
public class ToDoBean extends EntityBean<ToDo, Long> {

    @PersistenceContext
    EntityManager em;

    public ToDoBean() {
        super(ToDo.class);
    }

    public List<ToDo> findByUsername(String username) {
        try {
            return em.createQuery("SELECT t FROM ToDo t  JOIN t.user u WHERE u.username = :username ORDER BY t.dueDate, t.dueTime")
                    .setParameter("username", username)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<ToDo> getDueTasks() {
        try {
            Date tomorrow = new java.sql.Date(new java.sql.Date(System.currentTimeMillis()).getTime() + 24 * 60 * 60 * 1000);
            return em.createQuery("SELECT t FROM ToDo t WHERE t.dueDate = :dueDate").setParameter("dueDate", tomorrow).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
