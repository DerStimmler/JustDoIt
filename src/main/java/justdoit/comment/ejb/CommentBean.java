/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.comment.ejb;

import javax.ejb.Stateless;
import justdoit.comment.jpa.Comment;
import justdoit.common.ejb.EntityBean;

/**
 *
 * @author Lichter, Ansgar
 */

@Stateless
public class CommentBean extends EntityBean<Comment, Long> {
    
    public CommentBean() {
        super(Comment.class);
    }
}
