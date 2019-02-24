/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.task.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import justdoit.task.entitiy.Category;

/**
 *
 * @author Lichter, Ansgar
 */
public class CategoryBean {
   @PersistenceContext
   EntityManager em;
   
   public CategoryBean() {
       
   }
   
   public Category findById(long id) {
        return em.find(Category.class, id);
    }

    public List<Category> findAll() {
        String select = "SELECT c FROM Category c";
        return em.createQuery(select).getResultList();
    }

    public Category saveNew(Category category) {
        em.persist(category);
        return em.merge(category);
    }

    public Category update(Category category) {
        return em.merge(category);
    }
    
    public void delete(Category category) {
        em.remove(em.merge(category));
    }
}
