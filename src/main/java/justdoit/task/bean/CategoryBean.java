/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.task.bean;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import justdoit.common.EntityBean;
import justdoit.task.entitiy.Category;

/**
 *
 * @author Lichter, Ansgar
 */
@Stateless
@RolesAllowed("justdoit-user")
public class CategoryBean extends EntityBean<Category, Long>{
   @PersistenceContext
   EntityManager em;
   
   public CategoryBean() {
       super(Category.class);
   }
}
