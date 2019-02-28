/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.comment.jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import justdoit.common.jpa.User;
import justdoit.todo.jpa.ToDo;

/**
 *
 * @author Lichter, Ansgar
 */
@Entity
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @NotNull(message = "Der Kommentar muss einem Beutzer zugeordnet werden!")
    private User user;
    
    @ManyToOne
    private ToDo todo;
    
    @Lob //text of a comment can be greater than 255 chars
    @NotNull(message = "Bitte geben Sie einen Kommentar ein!")
    private String commentText;
    
    private Timestamp commentTimestamp;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Comment() {
        this.user = null;
        this.todo = null;
        this.commentText = "";
        this.commentTimestamp = null;
    }
    
    public Comment(User user, ToDo todo, String commentText) {
        this.user = user;
        this.todo = todo;
        this.commentText = commentText;
        this.commentTimestamp = new Timestamp(System.currentTimeMillis());
    }
    //</editor-fold>  
    
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public ToDo getTodo() {
        return todo;
    }
    
    public void setTodo(ToDo todo) {
        this.todo = todo;
    }
    
    public String getCommentText() {
        return commentText;
    }
    
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    
    public Timestamp getCommentTimestamp() {
        return commentTimestamp;
    }
    
    public void setCommentTimestamp(Timestamp commentTimestamp) {
        this.commentTimestamp = commentTimestamp;
    }
//</editor-fold>
       
    //<editor-fold defaultstate="collapsed" desc="Equals, hashCode">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
//</editor-fold>    
}
