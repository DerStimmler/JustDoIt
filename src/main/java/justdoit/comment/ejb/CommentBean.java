/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.comment.ejb;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import justdoit.comment.jpa.Comment;
import justdoit.common.ejb.EntityBean;

@Stateless
@RolesAllowed("justdoit-user")
public class CommentBean extends EntityBean<Comment, Long> {

    @PersistenceContext
    EntityManager em;

    public CommentBean() {
        super(Comment.class);
    }

    public List<Comment> findByToDoId(long id) {
        return this.em.createQuery("SELECT c FROM Comment c JOIN c.todo t where t.id= :id").setParameter("id", id).getResultList();
    }
}
