package justdoit.todo.ejb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import justdoit.common.ejb.EntityBean;
import justdoit.common.jpa.User;
import justdoit.todo.jpa.Category;
import justdoit.todo.jpa.ToDo;
import justdoit.todo.jpa.ToDoPriority;
import justdoit.todo.jpa.ToDoStatus;

@Stateless
public class ToDoBean extends EntityBean<ToDo, Long> {

    @PersistenceContext
    EntityManager em;

    public ToDoBean() {
        super(ToDo.class);
    }

    public List<ToDo> findByUsername(String username) {
        return em.createQuery("SELECT t FROM ToDo t  JOIN t.user u WHERE u.username = :username ORDER BY t.dueDate, t.dueTime")
                .setParameter("username", username)
                .getResultList();
    }

    public List<ToDo> getDueTasks() {
        String tomorrow = new SimpleDateFormat("yyyy-MM-dd").format(new Date(new Date().getTime() + (1000 * 60 * 60 * 24)));
        return em.createQuery("SELECT t FROM ToDo t WHERE t.dueDate = :dueDate").setParameter("dueDate", tomorrow).getResultList();
    }

    //Query with dynamic criterias vgl. https://docs.oracle.com/javaee/7/tutorial/persistence-criteria003.htm
    public List<ToDo> searchToDo(String likeDescription, Category category, ToDoStatus status, ToDoPriority priority, String username) {
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        CriteriaQuery<ToDo> criteriaQuery = criteriaBuilder.createQuery(ToDo.class);

        Root<ToDo> toDo = criteriaQuery.from(ToDo.class);
        criteriaQuery.select(toDo);

        Predicate predicate = criteriaBuilder.conjunction();

        if (username != null && !username.trim().isEmpty()) {
            Metamodel m = em.getMetamodel();
            EntityType<ToDo> ToDo_ = m.entity(ToDo.class);
            Join<ToDo, User> u = toDo.join(ToDo_.getList("user", User.class));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(u.get("username"), username));
            criteriaQuery.where(predicate);
        }

        if (likeDescription != null && !likeDescription.trim().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(toDo.get("description"), "%" + likeDescription + "%"));
            criteriaQuery.where(predicate);
        }

        if (category != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(toDo.get("category").get("categoryName"), category.getCategoryName()));
            criteriaQuery.where(predicate);
        }

        if (status != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(toDo.get("status"), status));
            criteriaQuery.where(predicate);
        }

        if (priority != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(toDo.get("priority"), priority));
            criteriaQuery.where(predicate);
        }

        criteriaQuery.orderBy(criteriaBuilder.asc(toDo.get("dueDate")), criteriaBuilder.asc(toDo.get("dueTime")));

        return this.em.createQuery(criteriaQuery).getResultList();
    }
}
