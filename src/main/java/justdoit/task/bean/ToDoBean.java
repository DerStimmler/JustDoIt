package justdoit.task.bean;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import justdoit.common.EntityBean;
import justdoit.task.entitiy.Category;
import justdoit.task.entitiy.ToDo;
import justdoit.task.entitiy.ToDoPriority;
import justdoit.task.entitiy.ToDoStatus;

@Stateless
@RolesAllowed("justdoit-user")
public class ToDoBean extends EntityBean<ToDo, Long> {

    @PersistenceContext
    EntityManager em;

    public ToDoBean() {
        super(ToDo.class);
    }

    public List<ToDo> findByUsername(String username) {
        return em.createQuery("SELECT t FROM ToDo t WHERE t.user.username = :username ORDER BY t.dueDate, t.dueTime")
                .setParameter("username", username)
                .getResultList();
    }

    //Query with dynamic criterias vgl. https://docs.oracle.com/javaee/7/tutorial/persistence-criteria003.htm
    public List<ToDo> searchToDo(String likeDescription, Category category, ToDoStatus status, ToDoPriority priority, String username) {
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        CriteriaQuery<ToDo> criteriaQuery = criteriaBuilder.createQuery(ToDo.class);

        Root<ToDo> toDo = criteriaQuery.from(ToDo.class);
        criteriaQuery.select(toDo);

        //Predicate to connect the different and queries
        Predicate predicate = criteriaBuilder.conjunction();

        /*
        * If the search value is present the value will be added to the search value.
        * If there are already other query options they will be connected with an
        * and
         */
        if (likeDescription != null || !likeDescription.trim().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(toDo.get("description"), "%" + likeDescription + "%"));
            criteriaQuery.where(predicate);
        }

        if (username != null || !username.trim().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(toDo.get("user.username"), username));
            criteriaQuery.where(predicate);
        }

        if (category != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(toDo.get("category"), category));
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

        /*
        * Order By DueDate and DueTime that the next task which the user have
        * to take care of is shown as first task
         */
        criteriaQuery.orderBy(criteriaBuilder.asc(toDo.get("dueDate")), criteriaBuilder.asc(toDo.get("dueTime")));

        //Execute the query and return the result list
        return this.em.createQuery(criteriaQuery).getResultList();
    }
}
